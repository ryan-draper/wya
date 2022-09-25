from tkinter import W
from google.cloud import vision
import sys
import cv2
import io
import os
import spacy
from collections import Counter
from autocorrect import Speller

def has_number(s):
    for l in s:
        if l.isdigit():
            return True
    return False

def find_issues(path):
    # Authentication
    os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "/Users/pasansirithanachai/repositories/hackrice12/wya/backend/wya/src/main/resources/script/leafy-unity-253618-3af413006efe.json"

    client = vision.ImageAnnotatorClient()
    spell = Speller(lang='en')

    # Load images to be processed by Google Cloud API and OpenCV
    with io.open(path, 'rb') as image_file:
        content = image_file.read()
    google_image = vision.Image(content=content)
    cv_image = cv2.imread(path)

    # Create NLP object for running Spacy NLP models
    nlp = spacy.load('en_core_web_lg')

    # Call Google Cloud APIs to obtain landmark and text information
    landmark_response = client.landmark_detection(image=google_image)
    text_response = client.text_detection(image=google_image)

    landmarks = set([landmark.description for landmark in landmark_response.landmark_annotations])
    if landmarks:
        landmark_msg = f'{", ".join(landmarks)}'
    else:
        landmark_msg =  ''

    # Add bounding box to landmark
    boxed_landmarks = []
    for landmark_annotation in landmark_response.landmark_annotations:
        # Don't box the same landmark more than once
        if landmark_annotation.description in boxed_landmarks:
            continue
        vertices = landmark_annotation.bounding_poly.vertices
        cv2.rectangle(cv_image, (vertices[0].x, vertices[0].y), (vertices[2].x, vertices[2].y), (0, 255, 0), 2)
        boxed_landmarks.append(landmark_annotation.description)

    texts = text_response.text_annotations
    # Extract texts in image
    image_labels = []
    new_texts = []
    for text in texts:
        description = text.description
        description_list = description.split('\n')
        image_labels.extend(description_list)
        for _ in range(len(description_list)):
            new_texts.append(text)

        if '\n' in description:
            new_texts.append(text)
            image_labels.append(description.replace('\n', ' '))

    # Remove special characters in each text string (since they are probably misread)
    image_labels = [''.join(l for l in txt if l.isalnum() or l in [' ', '.', ',']) for txt in image_labels]
    image_labels = [spell(image_label) for image_label in image_labels]

    # sort so labels are from shortest to longest
    labels_texts = list(zip(image_labels, new_texts))
    labels_texts.sort(key=lambda x : len(x[0].split()))
    image_labels = [x[0] for x in labels_texts]
    new_texts = [x[1] for x in labels_texts]

    text_msg = ''
    for label, text in zip(image_labels, new_texts):
        label_words = label.lower().split()
        label_words = [''.join(l for l in txt if l.isalnum() or l == ' ') for txt in label_words]
        print(label_words)

        if label.isnumeric() or len(label.split()) < 3:
            continue
        doc = nlp(label)

        entities = [ent.label_ for ent in doc.ents]
        entity_hist = Counter(entities)
        
        # Count number of "relevant" indicat
        loc_ent_counts = entity_hist.get('GPE', 0) + entity_hist.get('FAC', 0)

        # Check if a variety of obvious address indicators are in the string
        loc_ind_bool = False
        for loc_ind in ['street', 'st', 'dr', 'drive']:
            # Address has some identifier + a number
            if loc_ind in label_words and has_number(label):
                loc_ind_bool = True

        if loc_ind_bool or loc_ent_counts >= 2 or (loc_ent_counts == 1 and entity_hist.get('ORG', 0) >= 1):
            vertices = text.bounding_poly.vertices

            cv2.rectangle(cv_image, (vertices[0].x, vertices[0].y), (vertices[2].x, vertices[2].y), (0, 0, 255), 2)
        
            text_msg = 'The text boxed in red may include location-sensitive info.'
            break
    cv2.imwrite("/Users/pasansirithanachai/repositories/hackrice12/wya/backend/wya/result/detected.png", cv_image)
    return (landmark_msg, text_msg)

def main():
    args = sys.argv[1:]

    if len(args) == 1:
        landmark_msg, text_msg = find_issues(args[0])
        if landmark_msg or text_msg:
            print(True)
        else:
            print(False)
        print(landmark_msg)
        print(text_msg)



if __name__ == "__main__":
    main()
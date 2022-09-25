from google.cloud import vision
import cv2
import io
import os
import spacy
from collections import Counter
from autocorrect import Speller

def find_issues(path):
    client = vision.ImageAnnotatorClient()
    spell = Speller(lang='en')

    # Authentication
    os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "C:/Users/micha/Downloads/leafy-unity-253618-3af413006efe.json"

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

    landmarks = [landmark.description for landmark in landmark_response.landmark_annotations]
    if landmarks:
        landmark_msg = f'Your image my identify the following landmarks: {", ".join(landmarks)}.'
    else:
        landmark_msg =  ''

    texts = text_response.text_annotations
    # Extract texts in image
    image_labels = [text.description.replace('\n', ' ') for text in texts]
    # Remove special characters in each text string (since they are probably misread)
    image_labels = [''.join(l for l in txt if l.isalnum() or l in [' ', '.', ',']) for txt in image_labels]
    image_labels = [spell(image_label) for image_label in image_labels]
    doc = nlp(image_labels[0])

    text_msg = ''
    for label, text in zip(image_labels, texts):
        if label.isnumeric() or len(label.split()) < 3:
            continue
        doc = nlp(label)

        entities = [ent.label_ for ent in doc.ents]
        entity_hist = Counter(entities)
        # Count number of "relevant" indicat
        loc_ent_counts = entity_hist.get('GPE', 0) + entity_hist.get('FAC', 0)

        if loc_ent_counts >= 2 or (loc_ent_counts == 1 and entity_hist.get('ORG', 0) >= 1):
            vertices = text.bounding_poly.vertices

            cv2.rectangle(cv_image,(vertices[0].x, vertices[0].y),(vertices[2].x, vertices[2].y), (0, 255, 0), 2)
            cv2.imwrite("my.png", cv_image)
        
        text_msg = 'The boxed text may include location-sensitive info such as addresses.'
        break

    return landmark_msg, text_msg
   
print(find_issues("C:\\Users\\micha\\Documents\\RICE\\7 - F22\\wya\\popsmoke.png"))
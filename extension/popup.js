document.getElementById("myButton").addEventListener("click", submitImage);
var result;

function submitImage() {
    const url = "http://localhost:8080/api/v1/upload";
    const url2 = "http://localhost:8080/api/v1/getImage";

    const fileInput = document.getElementById("myFile");
    if (fileInput.files.length != 0) {
        var formData = new FormData();
        formData.append("file", fileInput.files[0]);
        (async() => {
            try {
                const response = await fetch(url, {
                    method: 'POST',
                    body: formData,
                });
                const json = await response.json();
                result = json;
                if (json["flagged"]) {
                    document.getElementById("content").innerHTML = "<p></p><h2><i class=\"bi bi-exclamation-diamond-fill\"></i> Do Not Post</h2><p>wya detected location sensitive information</p><p id=\"location\"></p><div><img id=\"image\" width=\"400px\" height=\"auto\" src=\"\"/></div><p></p>";
                    document.getElementById("location").innerText = json["landMark"];
    
                    
                } else {
                    document.getElementById("content").innerHTML = "<p></p><h2><i class=\"bi bi-check2-circle\"></i> Safe to Post</h2><p>No location sensitive information detected</p><div><img id=\"image\" width=\"400px\" height=\"auto\" src=\"\"/></div><p></p>"
                }
                const responseImage = await fetch(url2, {
                    method: 'GET'
                });
                const blob = await responseImage.blob();
                var imageURL = URL.createObjectURL(blob);
                // console.log(imageURL);
                document.getElementById("image").src = imageURL;
            } catch (e) {
                console.error(e);
                alert('error lol');
            }
        })();
    }
}

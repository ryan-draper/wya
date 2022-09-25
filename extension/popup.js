// function httpGet(theUrl) {
//     // var xmlHttp = new XMLHttpRequest();
//     // xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
//     // xmlHttp.send( null );
//     // document.getElementById("upload").innerHTML = xmlHttp.responseText;
//     // document.write(xmlHttp.responseText);
// }

// window.onload = function() {
//     // httpGet('http://localhost:8080/api/v1/uploadimage');
//     //console.log('1');
// };


// function doFetch(url) {
//     fetch(url).then(r => r.text()).then(result => {
//         // Result now contains the response text, do what you want...
//         console.log(r);
//     })
// };

  
// document.addEventListener('DOMContentLoaded', function() {
//     console.log()
//     httpGetAsync("http://localhost:8080/api/v1", callback);
// });
document.getElementById("myButton").addEventListener("click", submitImage);

function submitImage() {
    const url = "http://localhost:8080/api/v1/upload";
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
                console.log(json)
            } catch (e) {
                console.error(e);
                alert('error lol');
            }
        })();
    }
}

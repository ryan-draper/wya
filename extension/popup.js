// // Initialize button with user's preferred color
// let changeColor = document.getElementById("changeColor");

// chrome.storage.sync.get("color", ({ color }) => {
//   changeColor.style.backgroundColor = color;
// });

// // When the button is clicked, inject setPageBackgroundColor into current page
// changeColor.addEventListener("click", async () => {
//     let [tab] = await chrome.tabs.query({ active: true, currentWindow: true });
  
//     chrome.scripting.executeScript({
//       target: { tabId: tab.id },
//       func: setPageBackgroundColor,
//     });
//   });
  
//   // The body of this function will be executed as a content script inside the
//   // current page
//   function setPageBackgroundColor() {
//     chrome.storage.sync.get("color", ({ color }) => {
//       document.body.style.backgroundColor = color;
//     });
//   }

// function httpGetAsync(theUrl, callback) {
//     console.log('yo');
//     var xmlHttp = new XMLHttpRequest();
//     xmlHttp.onreadystatechange = function() { 
//         if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
//             callback(xmlHttp.responseText);
//     }
//     xmlHttp.open("GET", theUrl, true); // true for asynchronous 
//     xmlHttp.send(null);
// }

function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
    xmlHttp.send( null );
    //console.log('2');
    //console.log(xmlHttp.responseText);
    document.write(xmlHttp.responseText);
    //return xmlHttp.responseText;
}

window.onload = function() {
    httpGet('http://localhost:8080/api/v1/uploadimage');
    //console.log('1');
};


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

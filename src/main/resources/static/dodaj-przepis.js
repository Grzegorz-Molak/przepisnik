import {showNotification, search} from "./common.js";

const recipeForm = document.getElementById('recipe-form');
showNotification("Kill me")
setTimeout(function() {
    const message = 'Przykładowa wiadomość po 10 sekundach';
    showNotification(message);
}, 10000);

//Adding image
const imageButton = document.getElementById('image-button');
const fileInput = document.getElementById("fileInput");
imageButton.addEventListener("click", function (){
    chooseFile()
})
function chooseFile() {
    fileInput.click();

    fileInput.addEventListener("change", function() {
        let file = fileInput.files[0];
        let fileName = file.name;
        let acceptedExtensions = ["jpg", "jpeg", "png"]; // Lista akceptowanych rozszerzeń

        let fileExtension = fileName.split(".").pop().toLowerCase();
        if (acceptedExtensions.includes(fileExtension)) {
            console.log("Wybrano obraz o nazwie: " + fileName);
            window.alert("Wybrano obraz o nazwie: " + fileName)
            // Tutaj możesz wykonać dodatkowe działania na nazwie pliku
        }
        else{
            window.alert("Wybrany plik nie jest obrazem")
        }
    });
}


//Adding steps and ingredients
let close = document.getElementsByClassName("close");
for (let i = 0; i < close.length; i++) {
    close[i].onclick = function() {
        let li = this.parentElement;
        li.parentNode.removeChild(li);
    }
}

function newElement(elementID) {
    var li = document.createElement("li");
    if(elementID === "ing-list"){
        var inputValue = document.getElementById("ing-name").value;
        var t = document.createTextNode(document.getElementById("ing-name").value + " " + document.getElementById("amount").value + " " +document.getElementById("unit").value);
    }else{
        var inputValue = document.getElementById("step").value;
        var t = document.createTextNode(inputValue)
    }
    li.appendChild(t);
    var span = document.createElement("SPAN");
    var txt = document.createTextNode("\u00D7");
    span.className = "close";
    span.onclick = function() {
        let li = this.parentElement;
        li.parentNode.removeChild(li);
    }
    span.appendChild(txt);
    li.appendChild(span);

    if (inputValue === '') {
        alert("Nie możesz dodać pustego składnika");
    } else {
        document.getElementById(elementID).appendChild(li);
    }
    document.getElementById("ing-name").value = "";
    document.getElementById("amount").value = "";
    document.getElementById("step").value = "";
    document.getElementById("unit").value = "";
}

//Radio buttons

const privateRadio = document.getElementById('private');
const publicRadio = document.getElementById('public');

let status;
privateRadio.addEventListener('click', function() {
    status = false;
});
publicRadio.addEventListener('click', function() {
    status = true;
});

//Processing submitting recipe
recipeForm.addEventListener('submit', function(event) {
    event.preventDefault();
    const token = localStorage.getItem('jwtToken');

    //Get tags
    const tags = document.querySelectorAll('input[type="checkbox"]');
    const checkedValues = [];
    tags.forEach(tag => {
        if (tag.checked) {
            checkedValues.push(tag.value);
        }
    });
    console.log(checkedValues);

    //get ingredients
    const ingList = document.getElementById('ing-list');
    const ingredients = ingList.getElementsByTagName('li');
    const ingText = Array.from(ingredients).map(item => {
        return item.childNodes[0].textContent.trim();
    });

    console.log(ingText);

    //get steps
    const stepsList = document.getElementById('step-list');
    const steps = stepsList.getElementsByTagName('li');
    const stepsText = Array.from(steps).map(item => {
        return item.childNodes[0].textContent.trim();
    });

    console.log(stepsText);
    let imageFile = fileInput.files[0];

    if(imageFile === null){
        imageFile = new Image();
        imageFile.src = '/img/obiad.png'
    }

    const formData = new FormData();
    formData.append('name', document.getElementById('ra-name').value);
    formData.append('author', localStorage.getItem('username'));
    formData.append('status', status);
    formData.append('tags', JSON.stringify(checkedValues));
    formData.append('ingredients', JSON.stringify(ingText));
    formData.append('steps', JSON.stringify(stepsText));
    formData.append('timeMinutes', document.getElementById('minutes').value);
    formData.append('image', imageFile);

    for (var key of formData.entries()) {
        console.log(key[0] + ', ' + key[1]);
    }

    fetch('/recipe/new', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
        },
        body: formData
    })
        .then(response => {
            console.log(response);
            if (response.ok) {
                alert('Przepis został dodany');
                recipeForm.reset();
                ingList.innerHTML = "";
                stepsList.innerHTML = "";
                fileInput.value = null;
            } else {
                throw new Error('Request failed');
            }
        })
        .catch(error => {
            console.error(error);
            alert('Coś poszło nie tak');
        });
});

const searchForm= document.getElementById('searchForm')

searchForm.addEventListener("submit", e =>  {
    e.preventDefault();
    search("short",true);
});

const ingButton = document.getElementById('ing-button');
const stepButton = document.getElementById('step-button');

ingButton.addEventListener('click', function() {
    newElement('ing-list');
});


stepButton.addEventListener('click', function() {
    newElement('step-list');
});
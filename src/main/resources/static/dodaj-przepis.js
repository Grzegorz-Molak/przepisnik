const recipeForm = document.getElementById('recipe-form');
//Adding image
function chooseFile() {
    document.getElementById("fileInput").click();
}

//Adding steps and ingredients
let close = document.getElementsByClassName("close");
for (i = 0; i < close.length; i++) {
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
    const ingText = Array.from(ingredients).map(item => item.textContent);

    console.log(ingText);

    //get steps
    const stepsList = document.getElementById('step-list');
    const steps = stepsList.getElementsByTagName('li');
    const stepsText = Array.from(steps).map(item => item.textContent);

    console.log(stepsText);

    const requestBody = {
        name: document.getElementById('r-name').value,
        author: localStorage.getItem('username'),
        status: status,
        tags: checkedValues,
        ingredients: ingText,
        steps: stepsText,
        timeMinutes: 30
    }

    const jsonBody = JSON.stringify(requestBody);
    console.log(jsonBody)

    console.log(requestBody);

    fetch('/recipe/new', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(requestBody)
    })
        .then(response => {
            console.log(response);
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Request failed');
            }
        })
        .then(data => {
            alert('Przepis został dodany');
            recipeForm.reset();
            console.log(data);
        })
        .catch(error => {
            console.error(error);
            alert('Coś poszło nie tak');
        });
});

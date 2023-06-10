import {createTags, Recipe, search} from "./common.js";

const recipeId = localStorage.getItem('recipeId');
const username = localStorage.getItem('username');
fetch(`/recipe/${recipeId}`)
    .then(response => response.json())
    .then(recipe => {
        console.log(recipe);
        if(recipe.author === username){
            let shareButton = document.getElementById("share-button");
            shareButton.style.display = "block";
        }
        document.getElementById("rec-name").innerText = recipe.name
        document.getElementById("rec-author").innerText = `Autor: ${recipe.author}`
        document.getElementById("time").innerText = `Czas przygotowania: ${recipe.timeMinutes}min`
        let divTags = document.getElementById("rec-tags")
        createTags(recipe.tags,divTags )
        createOrderedList(recipe.ingredients, "ingredients-list")
        createOrderedList(recipe.steps, "steps-list")
    });

function createOrderedList(list, id){
    let listOrdered = document.getElementById(id)
    for (let item of list){
        let li = document.createElement("li")
        li.innerText = item
        listOrdered.appendChild(li)


    }

}

fetch(`/folder/${username}`)
    .then(response => response.json())
    .then(names => {
        addFolders(names)
    })

function addFolders(names) {
    let select = document.getElementById("sel-folder");
    names.shift();

    names.forEach((name) => {
        let option = document.createElement("option");
        option.text = name;
        option.value = name;
        if (name === "moje ulubione przepisy") {
            option.selected = true;
        }
        select.appendChild(option);
    });
}

const searchForm= document.getElementById('searchForm')

searchForm.addEventListener("submit", e =>  {
    e.preventDefault();
    search("short",true);
});

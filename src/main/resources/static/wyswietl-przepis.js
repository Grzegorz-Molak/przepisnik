import {createTags, Recipe, search} from "./common.js";

const recipeId = localStorage.getItem('recipeId');

fetch(`/recipe/${recipeId}`)
    .then(response => response.json())
    .then(recipe => {
        console.log(recipe);
        if(recipe.author === localStorage.getItem('username')){
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
        let folders = ["moje przepisy", "przepisy babci Zosi"]
        addFolders(folders)
    });

function createOrderedList(list, id){
    let listOrdered = document.getElementById(id)
    for (let item of list){
        let li = document.createElement("li")
        li.innerText = item
        listOrdered.appendChild(li)


    }

}
function addFolders(names) {
    let select = document.getElementById("sel-folder");

    names.forEach((name) => {
        let option = document.createElement("option");
        option.text = name;
        option.value = name;
        select.appendChild(option);
    });
}

const searchForm= document.getElementById('searchForm')

searchForm.addEventListener("submit", e =>  {
    e.preventDefault();
    search("short",true);
});

import {createTags, Recipe, search} from "./common.js";

let ingredients = ["jajka 2 sztuki", "mąka 1 szklanka", "proszek do pieczenia 1 łyżeczka", "olej 2 łyżki"]
let steps = ["Ubić pianę z białek", "Dodać cukier", "Wymieszać z żółtkami i serkiem waniliowym", "Dodać proszek do pieczenia",
    "Smażyć na oleju"]
let recipe = new Recipe(1234, "placki ziemniaczane", "babcia Zosia",  ["śniadanie", "obiad", "kolacja"], steps, ingredients, 30)
let recipe2 = new Recipe(1234, "placki z serkiem", "Agnieszka",  ["śniadanie", "łagodne"], steps, ingredients, 30)
document.getElementById("rec-name").innerText = recipe.name
document.getElementById("rec-author").innerText = `Autor: ${recipe.author}`
document.getElementById("time").innerText = `Czas przygotowania: ${recipe.time}min`
let divTags = document.getElementById("rec-tags")
createTags(recipe.tags,divTags )
createOrderedList(recipe.ingredients, "ingredients-list")
createOrderedList(recipe.steps, "steps-list")
let folders = ["moje przepisy", "przepisy babci Zosi"]
addFolders(folders)

//tutaj rzeczy sprawdzajace czyj to przepis
let isUserRecipe = true

 if(isUserRecipe){
     let shareButton = document.getElementById("share-button");
     shareButton.style.display = "block";

 }
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

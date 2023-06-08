import {createTags, Recipe} from "./common.js";

let ingredients = ["jajka 2 sztuki", "mąka 1 szklanka", "proszek do pieczenia 1 łyżeczka", "olej 2 łyżki"]
let steps = ["Ubić pianę z białek", "Dodać cukier", "Wymieszać z żółtkami i serkiem waniliowym", "Dodać proszek do pieczenia",
    "Smażyć na oleju"]
let recipe = new Recipe(1234, "placki ziemniaczane", "babcia Zosia", "sth", ["śniadanie", "obiad", "kolacja"], steps, ingredients)
let recipe2 = new Recipe(1234, "placki z serkiem", "Agnieszka", "sth", ["śniadanie", "łagodne"], steps, ingredients)
document.getElementById("rec-name").innerText = recipe.name
document.getElementById("rec-author").innerText = `Autor: ${recipe.author}`
let divTags = document.getElementById("rec-tags")
createTags(recipe.tags,divTags )
createOrderedList(recipe.ingredients, "ingredients-list")
createOrderedList(recipe.steps, "steps-list")

function createOrderedList(list, id){
    let listOrdered = document.getElementById(id)
    for (let item of list){
        let li = document.createElement("li")
        li.innerText = item
        listOrdered.appendChild(li)

    }

}
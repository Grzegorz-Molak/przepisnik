import {createTags, Recipe} from "./common.js";

let recipe = new Recipe(1234, "placki ziemniaczane", "babcia Zosia", "sth", ["śniadanie", "obiad", "kolacja"])
document.getElementById("rec-name").innerText = recipe.name
document.getElementById("rec-author").innerText = `Autor: ${recipe.author}`
let divTags = document.getElementById("rec-tags")
createTags(recipe.tags,divTags )
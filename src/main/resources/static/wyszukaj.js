import {addRecipeAd, Recipe} from "./common.js";

document.getElementById("submitbutton").addEventListener("click",function () {
    let recipe = new Recipe(1234, "placki ziemniaczane", "babcia Zosia", "sth", ["śniadanie", "obiad", "kolacja"])
    let recipe2 = new Recipe(1234, "placki z serkiem", "Agnieszka", "sth", ["śniadanie", "łagodne"])
    addRecipeAd(recipe)
    addRecipeAd(recipe2)
})


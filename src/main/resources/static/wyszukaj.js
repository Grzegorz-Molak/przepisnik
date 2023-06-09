import {addRecipeAd, Recipe} from "./common.js";

document.getElementById("submitbutton").addEventListener("click",function () {
    let ingredients = ["jajka 2 sztuki", "mąka 1 szklanka", "proszek do pieczenia 1 łyżeczka", "olej 2 łyżki"]
    let steps = ["Ubić pianę z białek", "Dodać cukier", "Wymieszać z żółtkami i serkiem waniliowym", "Dodać proszek do pieczenia",
        "Smażyć na oleju"]
    let recipe = new Recipe(1234, "placki ziemniaczane", "babcia Zosia",  ["śniadanie", "obiad", "kolacja"], steps, ingredients, 30)
    let recipe2 = new Recipe(1234, "placki z serkiem", "Agnieszka",  ["śniadanie", "łagodne"], steps, ingredients, 30)
    const mainPage = document.getElementById("mainpage");
    addRecipeAd(recipe, mainPage)
    addRecipeAd(recipe2, mainPage)
})


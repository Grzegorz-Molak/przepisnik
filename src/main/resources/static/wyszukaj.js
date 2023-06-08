import {addRecipeAd, Recipe} from "./common.js";

document.getElementById("submitbutton").addEventListener("click",function () {
    let ingredients = ["jajka 2 sztuki", "mąka 1 szklanka", "proszek do pieczenia 1 łyżeczka", "olej 2 łyżki"]
    let steps = ["Ubić pianę z białek", "Dodać cukier", "Wymieszać z żółtkami i serkiem waniliowym", "Dodać proszek do pieczenia",
        "Smażyć na oleju"]
    let recipe = new Recipe(1234, "placki ziemniaczane", "babcia Zosia", "sth", ["śniadanie", "obiad", "kolacja"], steps, ingredients)
    let recipe2 = new Recipe(1234, "placki z serkiem", "Agnieszka", "sth", ["śniadanie", "łagodne"], steps, ingredients)
})


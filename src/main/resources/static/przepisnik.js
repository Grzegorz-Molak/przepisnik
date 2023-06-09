import {addRecipeAd, Recipe} from "./common.js";

let ingredients = ["jajka 2 sztuki", "mąka 1 szklanka", "proszek do pieczenia 1 łyżeczka", "olej 2 łyżki"]
let steps = ["Ubić pianę z białek", "Dodać cukier", "Wymieszać z żółtkami i serkiem waniliowym", "Dodać proszek do pieczenia",
    "Smażyć na oleju"]
let recipe = new Recipe(1234, "placki ziemniaczane", "babcia Zosia",  ["śniadanie", "obiad", "kolacja"], steps, ingredients, 30)
let recipe2 = new Recipe(1234, "placki z serkiem", "Agnieszka",  ["śniadanie", "łagodne"], steps, ingredients, 30)
let folderList = ["Moje przepisy", "Przepisy babci Zosi"]
let currentFolder = "Nazwa folderu";
let folderNameElement = document.getElementById("folder-name");
folderNameElement.textContent = currentFolder;
createRecipeAdWithDelete(recipe2)
createRecipeAdWithDelete(recipe)
addFolderButtons(folderList)

function createRecipeAdWithDelete(recipe){
    let folderContentDiv = document.getElementById("folder-content")
    let newDiv = document.createElement("div");
    newDiv.className = "recip";
    // newDiv.id = "recip"; // Zmień "recipId" na odpowiednie ID
    let deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.className = "actionbutton w-button";
    let deleteImage = document.createElement("img");
    deleteImage.src = "img/delete.png";
    deleteImage.height = "25";
    deleteImage.alt = "";
    deleteImage.className = "photo";
    deleteImage.title = "Usuń przepis";
    deleteButton.appendChild(deleteImage);
    addRecipeAd(recipe, newDiv)
    newDiv.appendChild(deleteButton)
    folderContentDiv.appendChild(newDiv)

}

function addFolderButtons(folderNames) {
    let tabsDiv = document.getElementById("tabs");

    folderNames.forEach(folderName => {
        let folderButton = document.createElement("button");
        folderButton.className = "folder-tab";
        folderButton.innerText = folderName;

        tabsDiv.appendChild(folderButton);
    });
    let folderButton = document.createElement("button");
    folderButton.className = "folder-tab";
    folderButton.innerText = "+";
    tabsDiv.appendChild(folderButton);
}


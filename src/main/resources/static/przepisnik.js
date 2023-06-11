import {addRecipeAd, createTags, Recipe, search} from "./common.js";

const searchForm= document.getElementById('searchForm')
const username = localStorage.getItem('username');
searchForm.addEventListener("submit", e =>  {
    e.preventDefault();
    search("short",true);
});

const folderNameElement = document.getElementById("folder-name");

function createRecipeAdWithDelete(recipe){
    let folderContentDiv = document.getElementById("folder-content-recipes")
    let newDiv = document.createElement("div");
    newDiv.className = "recip";
    // newDiv.id = "recip"; // Zmień "recipId" na odpowiednie ID
    let deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.className = "actionbutton w-button";
    let deleteImage = document.createElement("img");
    deleteImage.src = "img/delete.png";
    deleteImage.height = 25;
    deleteImage.alt = "";
    deleteImage.className = "photo";
    deleteImage.title = "Usuń przepis";
    deleteButton.appendChild(deleteImage);
    let folderElementNameText = folderNameElement.textContent;
    deleteButton.addEventListener('click', function (){
        fetch(`/folder/${username}/${folderElementNameText}/${recipe.id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                console.log(response)
                if (response.ok) {
                    alert('Usunięto z folderu');
                    folderContentDiv.innerHTML = '';
                    getRecipesFromFolder(folderElementNameText);
                } else {
                    message = response.getAllResponseHeaders()
                    throw new Error(message);
                }
            })
            .catch(error => {
                console.error(error.message);
                alert('Coś poszło nie tak');
            });
    })
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
        folderButton.addEventListener("click", function (){
            folderNameElement.textContent = folderName;
            let folderContentDiv = document.getElementById("folder-content-recipes")
            folderContentDiv.innerHTML = '';
            getRecipesFromFolder(folderName);
        })

        tabsDiv.appendChild(folderButton);
    });
    let folderButton = document.createElement("button");
    folderButton.className = "folder-tab";
    folderButton.innerText = "+";
    tabsDiv.appendChild(folderButton);
}

const author = localStorage.getItem('username');
function getRecipesFromFolder(folderName){
    fetch(`/folder/${author}/${folderName}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            data.forEach(recipe => {
                const id = recipe.id;
                const name = recipe.name;
                const author = recipe.author;
                const tags = recipe.tags;
                const image = recipe.image;

                let recipeDisplay = new Recipe(id,name,author,tags,[],[],0, image);
                createRecipeAdWithDelete(recipeDisplay,document.getElementById("searchResult"));
            });
        })
}

fetch(`/folder/${author}`)
    .then(response => response.json())
    .then(names => {
        addFolderButtons(names)
        folderNameElement.textContent = names[0];
        getRecipesFromFolder(names[0]);
    })
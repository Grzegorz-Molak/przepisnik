import {addRecipeAd, closeFromId, createTags, openForm, Recipe, search} from "./common.js";

const searchForm= document.getElementById('searchForm')
const username = localStorage.getItem('username');
searchForm.addEventListener("submit", e =>  {
    e.preventDefault();
    search("short",true);
});

const folderNameElement = document.getElementById("folder-name");
const folderElementNameText = folderNameElement.innerText;
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
    if(folderElementNameText === 'moje autorskie przepisy'){
        localStorage.setItem('recipeId', recipe.id);
        openForm('confirm');
    }else{
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
                        throw new Error();
                    }
                })
                .catch(error => {
                    console.error(error.message);
                    alert('Coś poszło nie tak');
                });
        })
    }

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
            if(folderName === "moje autorskie przepisy" || folderName === "moje ulubione przepisy"){
                deleteFolderButton.style.display = "none";
            }else{
                deleteFolderButton.style.display = "flex";
            }
            let folderContentDiv = document.getElementById("folder-content-recipes")
            folderContentDiv.innerHTML = '';
            getRecipesFromFolder(folderName);
        })

        tabsDiv.appendChild(folderButton);
    });
    let folderButton = document.createElement("button");
    folderButton.className = "folder-tab";
    folderButton.innerText = "+";
    folderButton.addEventListener("click", function (){
        openForm('new-name');
    })
    tabsDiv.appendChild(folderButton);
}

const newNameText = document.getElementById('new-folder-name');
const newNameClose = document.getElementById('edit-close');
const newNameForm = document.getElementById('form');
newNameClose.addEventListener("click", function (){
    closeFromId('new-name');
})
newNameForm.addEventListener('submit', function (){
    const folderName = newNameText.value;
    fetch(`/folder/new/${username}/${folderName}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                fetch(`/folder/${username}`)
                    .then(response => response.json())
                    .then(names => {
                        addFolderButtons(names)
                    })
            } else {
                message = response.getAllResponseHeaders()
                throw new Error(message);
            }
        })
        .catch(error => {
            console.error(error.message);
            alert('Nie udało się utworzyć');
        });
})

const deleteDialog = document.getElementById('confirm');
const deleteClose = document.getElementById('delete-close');
const deleteYes = document.getElementById('yes-button');
const deleteNo = document.getElementById('no-button');
const deleteFolderButton = document.getElementById('deleteFolder');

deleteFolderButton.addEventListener("click", function (){
    openForm('confirm');
    localStorage.setItem('whatdelete','folder');
})
deleteClose.addEventListener('click', function (){
    closeFromId('confirm');
    localStorage.removeItem('whatdelete')
})
deleteNo.addEventListener('click', function (){
    closeFromId('confirm');
    localStorage.removeItem('whatdelete')
})

deleteYes.addEventListener('click', function (){
    const whatDelete = localStorage.getItem('whatdelete');
    let folderName = document.getElementById('folder-name').textContent
    if(whatDelete === 'folder'){
        fetch(`/folder/${username}/${folderName}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                console.log(response)
                if (response.ok) {
                    alert('usunięto folder');

                    getFolders();
                } else {
                    throw new Error();
                }
            })
            .catch(error => {
                console.error(error.message);
                alert('Coś poszło nie tak');
            });
    }

    if(whatDelete === 'recipe'){
        fetch(`/${localStorage.getItem('recipeId')}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                console.log(response)
                if (response.ok) {
                    alert('usunięto przepis');
                    getRecipesFromFolder(folderNameElement.textContent);
                } else {
                    message = response.getAllResponseHeaders()
                    throw new Error(message);
                }
            })
            .catch(error => {
                console.error(error.message);
                alert('Coś poszło nie tak');
            });
    }

    localStorage.removeItem('whatdelete')
    closeFromId('confirm');
})

function getRecipesFromFolder(folderName){
    fetch(`/folder/${username}/${folderName}`)
        .then(response => {
            console.log(response)
            if (response.ok) {
                return response.json();
            }else{
                throw new Error();
            } })
        .then(data => {
            console.log(data);
            if(data!==undefined){
                document.getElementById('folder-content-recipes').innerHTML = '';
                data.forEach(recipe => {
                    const id = recipe.id;
                    const name = recipe.name;
                    const author = recipe.author;
                    const tags = recipe.tags;
                    const image = recipe.image;

                    let recipeDisplay = new Recipe(id,name,author,tags,[],[],0, image);
                    createRecipeAdWithDelete(recipeDisplay,document.getElementById("searchResult"));
                });
            }
        })
        .catch(error => {
            console.error(error.message);
        });
}
getFolders();
function getFolders(){
    fetch(`/folder/${username}`)
        .then(response => response.json())
        .then(names => {
            addFolderButtons(names)
            folderNameElement.textContent = names[0];
            getRecipesFromFolder(names[0]);
        })
}
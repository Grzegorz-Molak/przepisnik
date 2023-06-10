export class Recipe{
    constructor(id, name, author, tags, steps, ingredients, time) {
        this.id = id
        this.name = name
        this.author = author
        this.tags = tags
        this.steps = steps
        this.ingredients = ingredients
        this.time = time
    }
}
export function addRecipeAd(recipe, div) {
    let divRecipeAd = document.createElement("div");
    let imgRecipe = document.createElement("img");
    let divInfo = document.createElement("div");
    let h3Name = document.createElement("h3");
    let h5Author = document.createElement("h5");
    let divTags = document.createElement("div");

    div.appendChild(divRecipeAd);
    divRecipeAd.className = "recipead";
    imgRecipe.className = "recad-img";
    divInfo.className = "recad-info";
    h3Name.className = "recad-name";
    h5Author.className = "recad-author";
    divTags.className = "recad-tags";
    divRecipeAd.addEventListener("click", function () {
        //     tutaj chcemy przejść do szczegółow
        window.alert("przechodzimy")
    })
    //imgRecipe.src = recipe.image;
    imgRecipe.src = "img/example.jpg";
    h3Name.textContent = recipe.name;
    h5Author.textContent = `Autor: ${recipe.author}`;
    divRecipeAd.appendChild(imgRecipe);
    divRecipeAd.appendChild(divInfo);
    divInfo.appendChild(h3Name)
    divInfo.appendChild(h5Author)
    divInfo.appendChild(divTags);
    let id = document.createElement("span");
    id.textContent = recipe.id;
    id.style.display = "none";
    divInfo.appendChild(id)
    createTags(recipe.tags, divTags)

}


export function createTags(tags, divTags){
    for (let tag of tags) {
        let divTag = document.createElement("div");
        divTag.className = "tag"
        let imgTag = document.createElement("img");
        imgTag.src = `img/${tag}.png`;
        imgTag.alt = ""
        imgTag.height = "14"
        divTag.appendChild(imgTag)
        divTag.appendChild(document.createTextNode(tag))
        divTags.appendChild(divTag)
    }
}

export function showNotification(message) {
    const alertElement = document.querySelector('.alert');
    const alertTextElement = document.querySelector('.alertText');
    const closeButton = document.querySelector('.alertClose');

    alertTextElement.textContent = message;
    alertElement.style.display = 'block';

    closeButton.addEventListener('click', function() {
        alertElement.style.display = 'none';
    });

}










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

export function search(searchType,status){
    const token = localStorage.getItem('jwtToken');
    let requestBody;
    if(searchType === "short"){
        requestBody = {
            pageNumber: 0,
            pageSize: 10,
            author: "",
            keyword: document.getElementById('search').value,
            tags: []
        }
        document.getElementById("searchResult").innerHTML = "";
    }else{
        //Get tags
        const tags = document.querySelectorAll('input[type="checkbox"]');
        const checkedValues = [];
        tags.forEach(tag => {
            if (tag.checked) {
                checkedValues.push(tag.value);
            }
        });
        console.log(checkedValues);

        let author;

        if(!status){
             author = localStorage.getItem('username');
        }else{
            author = "";
        }
        requestBody = {
            pageNumber: 0,
            pageSize: 10,
            author: author,
            keyword: document.getElementById('r-name').value,
            tags: checkedValues
        }
        document.getElementById("searchResult").innerHTML = "";
    }
    console.log(requestBody);
    fetch('/recipe/search', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(requestBody)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Request failed');
            }
        })
        .then(data => {
            console.log(data);
            data.forEach(recipe => {
                const id = recipe.id;
                const name = recipe.name;
                const author = recipe.author;
                const tags = recipe.tags;

                console.log(`Recipe ID: ${id}`);
                console.log(`Recipe Name: ${name}`);
                console.log(`Recipe Author: ${author}`);
                console.log(`Recipe Tags: ${tags}`);

                let recipeDisplay = new Recipe(id,name,author,tags,[],[],0);
                addRecipeAd(recipeDisplay);
            });
        })
        .catch(error => {
            console.error(error);
            alert('Coś poszło nie tak');
        });
}
export function addRecipeAd(recipe) {

    const mainPage = document.getElementById("searchResult");
    let divRecipeAd = document.createElement("div");
    let imgRecipe = document.createElement("img");
    let divInfo = document.createElement("div");
    let h3Name = document.createElement("h3");
    let h5Author = document.createElement("h5");
    let divTags = document.createElement("div");

    mainPage.appendChild(divRecipeAd);
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
    // for (let tag of recipe.tags){
    //     let divTag = document.createElement("div");
    //     divTag.className = "tag"
    //     let imgTag = document.createElement("img");
    //     imgTag.src = `img/${tag}.png`;
    //     imgTag.alt=""
    //     imgTag.height="14"
    //     divTag.appendChild(imgTag)
    //     divTag.appendChild(document.createTextNode(tag))
    //     divTags.appendChild(divTag)
    //
    // }
}


export function createTags(tags, divTags){
    for (let tag of tags) {
        let divTag = document.createElement("div");
        divTag.className = "tag"
        let imgTag = document.createElement("img");
        imgTag.src = `img/${tag}.png`;
        imgTag.alt = ""
        imgTag.height = 14
        divTag.appendChild(imgTag)
        divTag.appendChild(document.createTextNode(tag))
        divTags.appendChild(divTag)
    }
}








// <div className="recipead">
//     <img className="recad-img" src="img/example.jpg">
//         <div className="recad-info">
//             <h3 className="recad-name">Tutaj nazwa przepisu</h3>
//             <h5 className="recad-author"> Autor: BlaBla </h5>
//             <div className="recad-tags">
//                 <div className="tag">
//                     <img src="img/wegetariańskie.png" alt="" height="14">
//                         Tag 1
//                 </div>
//                 <div className="tag">
//                     <img src="img/wegetariańskie.png" alt="" height="14">
//                         Tag 2
//                 </div>
//                 <div className="tag">
//                     <img src="img/wegetariańskie.png" alt="" height="14">
//                         Tag 3
//                 </div>
//                 <div className="tag">
//                     <img src="img/wegetariańskie.png" alt="" height="14">
//                         Tag 4
//                 </div>
//             </div>
//         </div>
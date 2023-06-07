
export function addRecipeAd(recipe){

    const mainPage = document.getElementById('mainpage');
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
    divInfo.appendChild(divTags);
    divInfo.appendChild(h3Name)
    divInfo.appendChild(h5Author)
    for (let tag of recipe.tags){
        let divTag = document.createElement("div");
        divTag.className = "tag"
        let imgTag = document.createElement("img");
        imgTag.src = `img/${tag}.png`;
        imgTag.alt=""
        imgTag.height="14"
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
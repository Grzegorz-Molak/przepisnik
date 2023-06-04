function chooseFile() {
    document.getElementById("fileInput").click();
}

let close = document.getElementsByClassName("close");
for (i = 0; i < close.length; i++) {
    close[i].onclick = function() {
        let li = this.parentElement;
        li.parentNode.removeChild(li);
    }
}

function newElement(elementID) {
    var li = document.createElement("li");
    if(elementID === "ing-list"){
        var inputValue = document.getElementById("ing-name").value;
        var t = document.createTextNode(document.getElementById("ing-name").value + " " + document.getElementById("amount").value + " " +document.getElementById("unit").value);
    }else{
        var inputValue = document.getElementById("step").value;
        var t = document.createTextNode(inputValue)
    }
    li.appendChild(t);
    var span = document.createElement("SPAN");
    var txt = document.createTextNode("\u00D7");
    span.className = "close";
    span.onclick = function() {
        let li = this.parentElement;
        li.parentNode.removeChild(li);
    }
    span.appendChild(txt);
    li.appendChild(span);

    if (inputValue === '') {
        alert("Nie możesz dodać pustego składnika");
    } else {
        document.getElementById(elementID).appendChild(li);
    }
    document.getElementById("ing-name").value = "";
    document.getElementById("amount").value = "";
    document.getElementById("step").value = "";
    document.getElementById("unit").value = "";


}
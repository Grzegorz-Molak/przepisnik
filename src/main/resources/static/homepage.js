import {search} from "./common.js";

const searchForm = document.getElementById('searchFrom')

searchForm.addEventListener("submit", e =>  {
    e.preventDefault();
    search("short",true);
});

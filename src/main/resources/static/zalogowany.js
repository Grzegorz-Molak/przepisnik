import {logout} from "./common.js";

const logoutButton = document.getElementById('wyloguj');

logoutButton.addEventListener('click', function() {
    logout();
})


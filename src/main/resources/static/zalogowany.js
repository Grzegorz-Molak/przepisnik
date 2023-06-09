import {logout} from "./common";

const logoutButton = document.getElementById('wyloguj');

logoutButton.addEventListener('click', function() {
    logout();
})


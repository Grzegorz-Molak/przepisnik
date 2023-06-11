import {closeForm, login, openForm, register,search} from "./common.js";

const registrationForm = document.getElementById('form');
const loginForm = document.getElementById('login-form');

//Processing login form
loginForm.addEventListener('submit', function(event) {
    event.preventDefault();
    login(loginForm);
});

//Opening registration form
const registerOpen = document.getElementById('register-open');
const registerClose = document.getElementById('register-close');
registerOpen.addEventListener('click', function() {
    openForm()
})

registerClose.addEventListener('click', function() {
    closeForm()
})

//Processing registration form
registrationForm.addEventListener('submit', e => {
    e.preventDefault();
    register(e, registrationForm);
});


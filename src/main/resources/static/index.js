import {addRecipeAd, Recipe} from "./common.js";

let ingredients = ["jajka 2 sztuki", "mąka 1 szklanka", "proszek do pieczenia 1 łyżeczka", "olej 2 łyżki"]
let steps = ["Ubić pianę z białek", "Dodać cukier", "Wymieszać z żółtkami i serkiem waniliowym", "Dodać proszek do pieczenia",
    "Smażyć na oleju"]
let recipe = new Recipe(1234, "placki ziemniaczane", "babcia Zosia",  ["śniadanie", "obiad", "kolacja"], steps, ingredients, 30)
let recipe2 = new Recipe(1234, "placki z serkiem", "Agnieszka",  ["śniadanie", "łagodne"], steps, ingredients, 30)
const mainPage = document.getElementById("mainpage");
addRecipeAd(recipe, mainPage)
addRecipeAd(recipe2, mainPage)



const registrationForm = document.getElementById('form');
const username = document.getElementById('name');
const email = document.getElementById('email');
const password = document.getElementById('psw');
const password2 = document.getElementById('psw2');
const loginForm = document.getElementById('login-form');

//Processing login form
loginForm.addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(loginForm);

    fetch('/auth/authenticate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(Object.fromEntries(formData))
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                message = response.getAllResponseHeaders()
                throw new Error(message);
            }
        })
        .then(data => {
            console.log(data);
            const jwtToken = data.token;

            localStorage.setItem('jwtToken', jwtToken);
            localStorage.setItem('username', formData.get('username').toString());

            window.location.href = 'dodaj-przepis.html';
        })
        .catch(error => {
            console.error(error.message);
            alert('Niepoprawny login lub hasło');
        });
});

//Opening registration form
function openForm() {
    document.getElementById("rejestracja").style.display = "block";
}
function closeForm() {
    document.getElementById("form").reset();
    setDefault(username);
    setDefault(email);
    setDefault(password);
    setDefault(password2);
    document.getElementById("rejestracja").style.display = "none";
}

//Processing registration form
registrationForm.addEventListener('submit', e => {
    e.preventDefault();
    if(validate(e)){
        const formData = new FormData(registrationForm);

        const requestBody = {
            username: formData.get('name'),
            email: formData.get('email'),
            password: formData.get('psw')
        };
        fetch('/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (response.ok) {
                    console.log(response)
                    alert('Gratulacje! Zarejestrowałeś się');
                    closeForm();
                } else {
                    throw new Error('Registration failed');
                }
            })
            .catch(error => {
                console.error(error.message);
                alert('Istnieje już użytkownik o takiej nazwie');
                document.getElementById("form").reset();
                setDefault(username);
                setDefault(email);
                setDefault(password);
                setDefault(password2);
            });
    }
});

//Validating registration input
const validate = (e) => {
    const usernameValue = username.value.trim();
    const emailValue = email.value.trim();
    const passwordValue = password.value.trim();
    const password2Value = password2.value.trim();

    if(usernameValue === '') {
        setError(username, 'Podaj nazwę użytkownika');
        return false;
    } else {
        setSuccess(username);
    }

    if(emailValue === '') {
        setError(email, 'Podaj adres email');
        return false;

    } else if (!isValidEmail(emailValue)) {
        setError(email, 'Email nieprawidłowy');
        return false;

    } else {
        setSuccess(email);
    }

    if(passwordValue === '') {
        setError(password, 'Podaj hasło');
        return false;

    } else if (passwordValue.length < 6 ) {
        setError(password, 'Hasło musi mieć przynjamniej 6 znaków.')
        return false;
    } else {
        setSuccess(password);
    }

    if(password2Value === '') {
        setError(password2, 'Potwierdź hasło');
        return false;
    } else if (password2Value !== passwordValue) {
        setError(password2, "Hasła są różne");
        return false;
    } else {
        setSuccess(password2);
    }
    return true;
};

const setError = (element, message) => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.err');

    errorDisplay.innerText = message;
    inputControl.classList.add('error');
    inputControl.classList.remove('success')
}

const setSuccess = element => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.err');

    errorDisplay.innerText = '';
    inputControl.classList.add('success');
    inputControl.classList.remove('error');
};

const setDefault = element => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.err');

    errorDisplay.innerText = '';
    inputControl.classList.remove('success');
    inputControl.classList.remove('error');
};

const isValidEmail = email => {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}




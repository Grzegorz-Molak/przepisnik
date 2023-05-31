const form = document.getElementById('form');
const username = document.getElementById('name');
const email = document.getElementById('email');
const password = document.getElementById('psw');
const password2 = document.getElementById('psw2');

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
form.addEventListener('submit', e => {
    validate(e);
});

const validate = (e) => {
    const usernameValue = username.value.trim();
    const emailValue = email.value.trim();
    const passwordValue = password.value.trim();
    const password2Value = password2.value.trim();

    if(usernameValue === '') {
        setError(username, 'Podaj nazwę użytkownika');
        e.preventDefault();
    } else {
        setSuccess(username);
    }

    if(emailValue === '') {
        setError(email, 'Podaj adres email');
        e.preventDefault();

    } else if (!isValidEmail(emailValue)) {
        setError(email, 'Email nieprawidłowy');
        e.preventDefault();

    } else {
        setSuccess(email);
    }

    if(passwordValue === '') {
        setError(password, 'Podaj hasło');
        e.preventDefault();

    } else if (passwordValue.length < 6 ) {
        setError(password, 'Hasło musi mieć przynjamniej 6 znaków.')
        e.preventDefault();

    } else {
        setSuccess(password);
    }

    if(password2Value === '') {
        setError(password2, 'Potwierdź hasło');
        e.preventDefault();

    } else if (password2Value !== passwordValue) {
        setError(password2, "Hasła są różne");
        e.preventDefault();

    } else {
        setSuccess(password2);
    }

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




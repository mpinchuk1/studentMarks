const url = 'http://localhost:8081';
let $loginButton;

function init() {
    cacheDOM();
    bindEvents();
}

function bindEvents() {
    $loginButton.on('click', login.bind(this));

}

function cacheDOM() {
    $loginButton = $('#loginButton');

}

function login() {
    let login = $('#log').val();
    let pass = $('#pas').val();
    console.log(login + " " + pass)
    if(validate(login, pass)){
        $.ajax({
            url: url + "/login",
            method: "post",
            data: {"login":login, "password": pass},
            error: function (message) {
                console.log(message)
                alert("Wrong password/login")
            },
            success: function () {
                currentUser = login;
                document.location.href = url + "/subjects";

            }
        });

    }
}


function validate(login, pass) {

    if (login.trim() === "" || pass === "") {
        alert("Username and password are required");
        return false;
    }
    return true;
}

init()
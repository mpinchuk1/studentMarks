let $chatHistory;
let $showUserDataButton;
let $saveUserDataButton;
let $createNewRoomButton;
let $loginButton;
let currentUser;


function init() {
    cacheDOM();
    bindEvents();
}

function bindEvents() {
    $loginButton.on('click', loginUser.bind(this));
    $showUserDataButton.on('click', showUserData.bind(this));
    $saveUserDataButton.on('click', saveUserData.bind(this));
}

function cacheDOM() {
    $chatHistory = $('.chat-history');
    $loginButton = $('#loginButton');
    $showUserDataButton = $('#showUserData');
    $saveUserDataButton = $('#saveUserData');
    $createNewRoomButton = $('#addNewRoom');
}


function showUserData() {
    showData();
}

function saveUserData() {
    saveData();
}

function loginUser() {
    login();
}

init();


function adminButtonShow() {
    if(currentUser.login === 'admin'){
        $('#addRoomButtonModal').show()
    }else {
        $('#addRoomButtonModal').hide()
    }
}
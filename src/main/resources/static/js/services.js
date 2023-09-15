function sendData(lang) {
    if (lang === 'sn' || lang === 'en') {
        var today = new Date();
        const gifTimeout = setTimeout(function(){
            displayFormAlert("Too long to respond", "The server is taking too long to respond. Please check your connection and try again.")
        }, 60000);
        displayLoadingGif();
        const name = document.getElementsByName("name");
        const email = document.getElementsByName("email");
        const serviceType = document.getElementsByName("service");
        const details = document.getElementsByName("details");
        const budget = document.getElementsByName("budget");
        let jsonToSend = { "name": "", "email": "", "service": "", "budget": "", "details": "", "date": ""};
        jsonToSend.date = today.toLocaleString('en-ZW');
        let shouldSend = true;

        if (name.length === 1) {
            let usersName = name.item(0).value;
            if (verifyIntegrity(usersName, "name") && usersName.length > 0) {
                jsonToSend.name = name.item(0).value;
            } else {
                shouldSend = false;
                if (usersName.length === 0) {
                    displayFormAlert("Name", "Name is required");
                } else {
                    displayFormAlert("Name", "Only letters are allowed when entering Name");
                }
            }
        }
        if (email.length === 1 && shouldSend) {
            let emailItem = email.item(0).value;
            if (verifyIntegrity(emailItem, "email")) {
                jsonToSend.email = email.item(0).value;
            } else {
                shouldSend = false;
                if (emailItem.length === 0) {
                    displayFormAlert("Email", "Email is required");
                }
                else {
                    displayFormAlert("Email", "Please check that your input follows email format");
                }
            }
        }
        if (serviceType.length === 1 && shouldSend) {
            if (verifyIntegrity(serviceType.item(0).value, "service")) {
                jsonToSend.service = serviceType.item(0).value;
            } else {
                shouldSend = false;
                displayFormAlert("Project", "Invalid Project");
            }
        }
        if (details.length === 1 && shouldSend) {
            if (verifyIntegrity(details.item(0).value, "details")) {
                jsonToSend.details = details.item(0).value;
            } else {
                shouldSend = false;
                displayFormAlert("Project", "Only punctuation, letters and digits are allowed");
            }
        }
        if (budget.length === 1 && shouldSend) {
            if (verifyIntegrity(budget.item(0).value, "budget")) {
                jsonToSend.budget = budget.item(0).value;
            } else {
                shouldSend = false;
                displayFormAlert("Project", "Invalid Budget Selected")
            }
        }

        if (shouldSend) {
            let data = [], clientInfo;

            for (clientInfo in jsonToSend) {
                data.push(encodeURIComponent(clientInfo) + '=' + encodeURIComponent(jsonToSend[clientInfo]));
            }
            jsonToSend = data.join('&').replace(/%20/g, '+');
            const xmlRequest = new XMLHttpRequest();
            xmlRequest.responseType = "text";
            if (lang === 'sn') {
                xmlRequest.open("POST", "/sn/service");
            } else {
                xmlRequest.open("POST", "/service");
            }

            xmlRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlRequest.send(jsonToSend);

            xmlRequest.addEventListener("load", function (event) {
                if (xmlRequest.readyState === xmlRequest.DONE) {
                    if (xmlRequest.status === 200) {
                        displayFormAlert("Success", event.target.responseText);
                    }
                    if (xmlRequest.status === 400) {
                        displayFormAlert("Failure", event.target.responseText);
                    }
                    if (xmlRequest.status === 429) {
                        displayFormAlert("Too many requests", event.target.responseText);
                    }
                    if (xmlRequest.status === 500){
                        displayFormAlert("Server error", "Server could not proccess your request");
                    }
                }
                clearTimeout(gifTimeout);
            });
            xmlRequest.addEventListener("error", function (event) {
                clearTimeout(gifTimeout);
                displayFormAlert("Failure", event.target.responseText);
            });
        }
    }
}


function verifyIntegrity(stringToVerify, inputElem) {
    if (inputElem === "name" || inputElem === "service") {
        let regex = /^[a-zA-Z\s]*$/;
        return regex.test(stringToVerify);
    } else if (inputElem === "email") {
        var regex = /^(?:[a-z0-9!#$%&amp;'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&amp;'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])$/
        return regex.test(stringToVerify);
    } else if (inputElem === "budget") {
        let regex = /^[0-9-$+\s]*$/;
        return regex.test(stringToVerify);
    } else if (inputElem === "details") {
        let regex = /^[a-zA-Z\s0-9!,.?:;]*$/;
        return regex.test(stringToVerify);
    }
}

function displayFormAlert(headingText, bodyText) {
    var formAlert = document.getElementById("formAlert");
    var formHeading = document.getElementById("alertTitle");
    var formLoadingGifCont = document.getElementById("formLoadingGifCont");
    var formBody = document.getElementById("alertBody");
    var closeButton = document.getElementById("closeButtonId");
    formAlert.style.display = "block";
    formLoadingGifCont.style.display = "none";
    closeButton.style.display = "block";
    formHeading.innerHTML = headingText;
    formBody.innerHTML = bodyText;
}

function displayLoadingGif() {
    var formAlert = document.getElementById("formAlert");
    var formHeading = document.getElementById("alertTitle");
    var formBody = document.getElementById("alertBody");
    var formLoadingGifCont = document.getElementById("formLoadingGifCont");
    if (formHeading.innerHTML !== "" || formBody.innerHTML !== "") {
        formHeading.innerHTML = "";
        formBody.innerHTML = "";
    }
    formAlert.style.display = "block";
    formLoadingGifCont.style.display = "flex";
}

function closeSpan() {
    var formAlert = document.getElementById("formAlert");
    var formHeading = document.getElementById("alertTitle");
    var formBody = document.getElementById("alertBody");
    var closeButton = document.getElementById("closeButtonId");
    closeButton.style.display = "none";
    formHeading.innerHTML = "";
    formBody.innerHTML = "";
    formAlert.style.display = "none";
}
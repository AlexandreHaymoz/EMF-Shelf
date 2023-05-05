$(document).ready(function() {
    $.getScript("../service/service.js")
    getLivres(displayLivres, displayInternalError);
});

function displayLivres(data) {
    data.array.forEach(livre => {
        alert(livre);
    });
}

function displayInternalError() {
    alert("Erreur interne du serveur.")
}

$(document).ready(function () {
    pkLivre = new URL(window.location.href).searchParams.get("pkLivre")
    getLivre(displayLivre, displayInternalError, pkLivre);
})

function displayLivre(data) {
    $("#titre").text(data.titre)
    $("#auteur").text(data.auteur)
    $("#description").text(data.description)
    $("#disponible").text(data.disponible)
}

function displayInternalError() {
    alert("Erreur interne du serveur.")
}
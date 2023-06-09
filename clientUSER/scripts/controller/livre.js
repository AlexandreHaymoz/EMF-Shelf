$(document).ready(function () {
    pkLivre = new URL(window.location.href).searchParams.get("pkLivre")
    getLivre(displayLivre, displayInternalError, pkLivre);
    $("#reserver").on("click", function() {
        reserverLivre(pkLivre, displayReserverLivre, displayMauvaiseRequete, displayNonConnecte, displayInternalError)
    })
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

function displayReserverLivre() {
    alert("Le livre a été réservé")
}

function displayMauvaiseRequete() {
    alert("Mauvaise requête")
}

function displayNonConnecte() {
    alert("Vous n'êtes pas connecté")
}

function displayInternalError() {
    alert("Erreur interne")
}
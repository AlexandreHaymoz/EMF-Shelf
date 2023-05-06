$(document).ready(function () {
    pkLivre = new URL(window.location.href).searchParams.get("pkLivre")
    getLivre(displayLivre, displayInternalError, pkLivre);
})

function displayLivre(data) {
    $("#titre").text(data.titre)
    $("#auteur").text(data.auteur)
    $("#description").text(data.description)
    $("#disponible").text(data.disponible)
    $("#effacer").on("click", function () {
        effacerLivre(pkLivre, displayEffacerLivre, displayMauvaiseRequete, displayNonConnecte, displayNonAdmin, displayInternalError)
    })
    $("#effacer").after('<a href="/pages/modifierLivre.html?PK_Livre='+new URL(window.location.href).searchParams.get("pkLivre")+'&Titre='+data.titre+'&auteur='+data.auteur+'"><input id="modifier" type="submit" value="Modifier"/></a>')
}

function displayInternalError() {
    alert("Erreur interne du serveur.")
}

function displayEffacerLivre() {
    alert("Le livre a été éffacé")
    window.location.href = "/pages/gestionIndex.html"
}

function displayMauvaiseRequete() {
    alert("Mauvaise requête")
}

function displayNonConnecte() {
    alert("Vous n'êtes pas connecté")
}

function displayNonAdmin() {
    alert("Vous n'êtes pas un admin")
}

function displayInternalError() {
    alert("Erreur interne")
}
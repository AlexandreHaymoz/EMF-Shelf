
$(document).ready(function () {
    getReservations(displayReservations, displayInternalError);
});

function displayReservations(data) {
    if (data) {
        data.forEach(livre => {
            $("#livres").append("<li><a id='" + livre.PK_Livre + "'href='/pages/livre.html?pkLivre=" + livre.PK_Livre + "'>" + livre.titre + "</a></li>")
        });
    } else {
        $("body").append("<p>Aucune réservation</p>")
    }
}

function displayNonConnecte() {
    alert("Vous n'êtes pas connecté")
    window.location.href = "/"
}

function displayInternalError() {
    alert("Erreur interne")
}
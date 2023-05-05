
$(document).ready(function() {
    getLivres(displayLivres, displayInternalError);
    $("#deconnecter").on("click", function() {
        seDeconneter(displayDeconnecter, displayNonConnecte, displayInternalError)
    })
});

function displayLivres(data) {
    data.forEach(livre => {
        $("#livres").append("<li><a id='"+livre.PK_Livre+"'href='/pages/livre.html?pkLivre="+livre.PK_Livre+"'>"+livre.titre+"</a></li>")
    });
}

function displayDeconnecter() {
    alert("Déconnection réussi")
}

function displayNonConnecte() {
    alert("Vous n'êtes pas connecté")
}

function displayInternalError() {
    alert("Erreur interne du serveur.")
}

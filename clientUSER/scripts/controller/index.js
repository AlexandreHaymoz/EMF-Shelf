
$(document).ready(function() {
    getLivres(displayLivres, displayInternalError);
});

function displayLivres(data) {
    data.forEach(livre => {
        $("#livres").append("<li><a id='"+livre.PK_Livre+"'href='/pages/livre.html?pkLivre="+livre.PK_Livre+"'>"+livre.titre+"</a></li>")
    });
}


function displayInternalError() {
    alert("Erreur interne du serveur.")
}

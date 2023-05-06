$(document).ready(function () {
    pkLivre = new URL(window.location.href).searchParams.get("PK_Livre")
    $("#titre").val(new URL(window.location.href).searchParams.get("Titre"))
    $("#auteur").val(new URL(window.location.href).searchParams.get("auteur"))
    $("#modifierLivre").on("click", function () {
        let titre = $("#titre").val();
        let auteur = $("#auteur").val();
        let description = $("#description").val();
        if (titre == "" || auteur == "" || description == "") {
            alert("Veuillez remplir tous les champs")
        } else {
            modifierLivre(pkLivre, titre, auteur, description, displayModifierLivre, displayMauvaiseRequete, displayNonConnecte, displayNonAdmin, displayInternalError)
        }
    })
})



function displayModifierLivre() {
    alert("Un livre a été modifié!")
    window.location.href = "/pages/gestionIndex.html"
}

function displayMauvaiseRequete() { 
    alert("La requête a été mal formulée")
}

function displayNonAdmin() {
    alert("Vous n'êtes pas un admin")
}

function displayNonConnecte() {
    alert("Vous n'êtes pas connecté")
}

function displayInternalError() {
    alert("Problème interne")
}
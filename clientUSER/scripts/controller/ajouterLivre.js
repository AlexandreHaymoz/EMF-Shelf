$(document).ready(function () {
    $("#addLivre").on("click", function() {
        let titre = $("#titre").val();
        let auteur = $("#auteur").val();
        let description = $("#description").val();
        if (titre == "" || auteur == "" || description == "") {
            alert("Veuillez remplir tous les champs")
        } else {
            ajouterLivre(titre, auteur, description, displayAjouterLivre, displayMauvaiseRequete, displayNonAdmin, displayNonConnecte, displayInternalError)
        }
    })
})

function displayAjouterLivre() {
    alert("Un livre a été ajouté!")
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
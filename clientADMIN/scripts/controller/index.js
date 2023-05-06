$(document).ready(function () {
    $("#connecter").on("click", function() {
        let nom = $("#nom").val();
        let motDePasse = $("#motDePasse").val();
        if (nom == "" || motDePasse == "") {
            alert("Veuillez remplir tous les champs")
        } else {
            connecter(nom, motDePasse, displayConnecter, displayMauvaiseRequete, displayMauvaiseID, displayDejaConnecte, displayInternalError)
        }
    })
})


function displayConnecter(data) {
    if (data.administrateur) {
        alert("Connexion réussie")
        sessionStorage.setItem("isConnected", true);
        window.location.href = "/pages/gestionIndex.html"
    } else {
        alert("Connexion refusée, vous n'êtes pas admin")
        seDeconneter()
    }

}

function displayMauvaiseID() {
    alert("Mauvais identifiant")
}

function displayMauvaiseRequete() {
    alert("Mauvaise requête")
}

function displayDejaConnecte() {
    alert("Deja connecté")
}

function displayInternalError() {
    alert("Erreur interne")
}
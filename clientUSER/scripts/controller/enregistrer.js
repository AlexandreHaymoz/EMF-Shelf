$(document).ready(function () {
    $("#enregistrer").on("click", function() {
        let nom = $("#nom").val();
        let motDePasse = $("#motDePasse").val();
        if (nom == "" || motDePasse == "") {
            alert("Veuillez remplir tous les champs")
        } else {
            enregistrer(nom, motDePasse, displayEnregistrer, displayMauvaiseRequete, displayDejaConnecte , displayConflit, displayInternalError)
        }
    })
})

function displayEnregistrer(data) {
    alert("Enregistrement réussi")
    window.location.href = "/"
}
function displayMauvaiseRequete() {
    alert("Vérifier que le nom est composé uniquement de 3 à 18 lettres ASCII\nVérifier que le mot de passe est 8 à 18 lettres dont un symbole (sauf ,;&|), un numéro, une majuscule et une minuscule")
}

function displayDejaConnecte() {
    alert("Deja connecté")
}

function displayConflit() {
    alert("Le nom existe déjà")
}

function displayInternalError() {
    alert("Erreur interne")
}
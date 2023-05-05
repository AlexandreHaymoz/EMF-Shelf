
const BASE_URL_UTILISATEUR = "http://localhost:8080/gatewayAPI_war/utilisateurs"
const BASE_URL_LIVRE = "http://localhost:8080/gatewayAPI_war/livres"

function getLivres(displayLivres, displayInternalError) {
    $.ajax({
        type: "GET",
        dateType: "json",
        url: BASE_URL_LIVRE + "?action=getLivres",
        xhrFields: {
            withCredentials: true
            },
            async: false,
            crossDomain: true,
        statusCode: {
            200: displayLivres,
            500: displayInternalError,
        }
    })
}

function getLivre(displayLivre, displayInternalError, pkLivre) {
    $.ajax({
        type: "GET",
        dateType: "json",
        url: BASE_URL_LIVRE + "?action=getLivre&pk="+pkLivre,
        xhrFields: {
            withCredentials: true
            },
            async: false,
            crossDomain: true,
        statusCode: {
            200: displayLivre,
            500: displayInternalError,
        }
    })
}

function connecter(nom, motDePasse, displayConnecter, displayMauvaiseRequete, displayMauvaiseID, displayDejaConnecte, displayInternalError) {
    $.ajax({
        type: "POST",
        dateType: "json",
        url: BASE_URL_UTILISATEUR,
        data: "action=connecter&nom=" + nom + "&motDePasse=" + motDePasse,
        xhrFields: {
            withCredentials: true
            },
            async: false,
            crossDomain: true,
        statusCode: {
            200: displayConnecter,
            400: displayMauvaiseRequete,
            401: displayMauvaiseID,
            403: displayDejaConnecte,
            500: displayInternalError,
        } 
    })
}

function enregistrer(nom, motDePasse, displayEnregistrer, displayMauvaiseRequete, displayDejaConnecte , displayConflit, displayInternalError) {
    $.ajax({
        type: "POST",
        dateType: "json",
        url: BASE_URL_UTILISATEUR,
        data: "action=enregistrer&nom=" + nom + "&motDePasse=" + motDePasse,
        xhrFields: {
            withCredentials: true
            },
            async: false,
            crossDomain: true,
        statusCode: {
            200: displayEnregistrer,
            400: displayMauvaiseRequete,
            403: displayDejaConnecte,
            409: displayConflit,
            500: displayInternalError
        }
    })
}

function seDeconneter(displayDeconnecter, displayNonConnecte, displayInternalError) {
    $.ajax({
        type: "POST",
        dateType: "json",
        url: BASE_URL_UTILISATEUR,
        data: "action=deconnecter",
        xhrFields: {
            withCredentials: true
            },
            async: false,
            crossDomain: true,
            statusCode: {
                200: displayDeconnecter,
                401: displayNonConnecte,
                500: displayInternalError
            }
    })
}
function ajouterLivre(titre, auteur, description, displayAjouterLivre, displayMauvaiseRequete, displayNonAdmin, displayNonConnecte, displayInternalError) {
    $.ajax({
        type: "POST",
        dateTpye: "json",
        url: BASE_URL_LIVRE,
        data: "titre="+titre+"&auteur="+auteur+"&description="+description+"&image=-",
        xhrFields: {
            withCredentials: true
            },
            async: false,
            crossDomain: true,
            statusCode: {
                200: displayAjouterLivre,
                400: displayMauvaiseRequete,
                403: displayNonAdmin,
                401: displayNonConnecte,
                500: displayInternalError
            }
    })
}

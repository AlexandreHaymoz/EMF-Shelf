const BASE_URL_LIVRE = "http://wstemfa39-21:8888/gatewayAPI/livres"
const BASE_URL_UTILISATEUR = "http://wstemfa39-21:8888/gatewayAPI/utilisateurs"

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
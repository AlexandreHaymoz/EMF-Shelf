
const BASE_URL_UTILISATEUR = "http://localhost:8080/gatewayAPI_war/utilisateurs"
const BASE_URL_LIVRE = "http://localhost:8080/gatewayAPI_war/livres"
const BASE_URL_RESERVATION = "http://localhost:8080/gatewayAPI_war/reservations"

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

function seDeconneter() {
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
    })
}

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

function effacerLivre(pkLivre, displayReserverLivre, displayMauvaiseRequete, displayNonAdmin, displayNonConnecte, displayInternalError) {
    $.ajax({
        type: "DELETE",
        dateType: "json",
        url: BASE_URL_LIVRE,
        data: "PK_livre="+pkLivre,
        xhrFields: {
            withCredentials: true
            },
            async: false,
            crossDomain: true,
            statusCode: {
                200: displayReserverLivre,
                400: displayMauvaiseRequete,
                401: displayNonConnecte,
                403: displayNonAdmin,
                500: displayInternalError
            }
    })
}

function modifierLivre(pkLivre, titre, auteur, description, displayModifierLivre, displayMauvaiseRequete,displayNonConnecte, displayNonAdmin, displayInternalError) {
    $.ajax({
        type: "PUT",
        dateType: "json",
        url: BASE_URL_LIVRE,
        data: "PK_livre="+pkLivre+"&titre="+titre+"&auteur="+auteur+"&description="+description+"&image=-",
        xhrFields: {
            withCredentials: true
            },
            async: false,
            crossDomain: true,
            statusCode: {
                200: displayModifierLivre,
                400: displayMauvaiseRequete,
                401: displayNonConnecte,
                403: displayNonAdmin,
                500: displayInternalError
            }
    })
}
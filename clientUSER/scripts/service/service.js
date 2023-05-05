var BASE_URL_LIVRE = "http://wstemfa39-21:8888/gatewayAPI/livres"
var BASE_URL_UTILISATEUR = "http://wstemfa39-21:8888/gatewayAPI/utilisateurs"

function getLivres(displayLivres, displayInternalError) {
    $.ajax({
        type: "GET",
        dateType: "json",
        url: BASE_URL_LIVRE,
        statusCode: {
            200: displayLivres,
            500: displayInternalError,
        }
    })
}
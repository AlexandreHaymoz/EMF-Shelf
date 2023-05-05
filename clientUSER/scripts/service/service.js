let BASE_URL_LIVRE = ""
let BASE_URL_UTILISATEUR = ""

function getLivres(displayLivres, displayInternalError) {
    $.ajax({
        type: "GET",
        dateType: "json",
        url: BASE_URL_LIVRE + "",
        statusCode: {
            200: displayLivres,
            500: displayInternalError,
        }
    })
}
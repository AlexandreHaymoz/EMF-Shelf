package emf.haymoz.gatewayapi.service;

import com.google.gson.Gson;
import emf.haymoz.gatewayapi.model.HttpData;
import emf.haymoz.gatewayapi.model.Utilisateur;

import static emf.haymoz.gatewayapi.service.CommonService.*;


public class UtilisateurService {
    private static final String URL = "https://haymozn.emf-informatique.ch/java_compteREST/bibliotheque/utilisateurs";

    /**
     * Objet pour convertir les objets en JSON et vice versa
     */
    private static final Gson gson = new Gson();

    /**
     * Enregistre un nouvel utilisateur dans la base de données.
     * @param utilisateur Utilisateur à enregistrer
     * @return Les données HTTP retournées par l'API REST.
     */
    public HttpData enregistrer(Utilisateur utilisateur) {
        String data = gson.toJson(utilisateur);
        return httpRequest(URL + "/enregistrer", data, "POST");
    }

    /**
     * Connecte un utilisateur enregistré dans la base de données.
     * @param utilisateur Utilisateur à connecter
     * @return Les données HTTP retournées par l'API REST.
     */
    public HttpData connecter(Utilisateur utilisateur) {
        String data = gson.toJson(utilisateur);
        return httpRequest(URL + "/connecter", data, "POST");
    }

    /**
     * Récupère tous les utilisateurs enregistrés dans la base de données.
     * @return Les données HTTP retournées par l'API REST.
     */
    public HttpData getUtilisateurs() {
        return httpRequest(URL, null, "GET");
    }
    /**
     *
     * Récupère un utilisateur enregistré dans la base de données.
     * @param pkUtilisateur Clé primaire de l'utilisateur à récupérer
     * @return Les données HTTP retournées par l'API REST.
     */
    public HttpData getUtilisateur(String pkUtilisateur) {
        return httpRequest(URL + "/" + pkUtilisateur, null, "GET");
    }


}

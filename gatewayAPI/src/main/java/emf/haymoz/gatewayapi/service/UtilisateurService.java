package emf.haymoz.gatewayapi.service;

import com.google.gson.Gson;
import emf.haymoz.gatewayapi.model.HttpData;
import emf.haymoz.gatewayapi.model.Utilisateur;

import static emf.haymoz.gatewayapi.service.CommonService.httpGet;
import static emf.haymoz.gatewayapi.service.CommonService.httpPost;


public class UtilisateurService {
    private static final String URL = "https://haymozn.emf-informatique.ch/java_compteREST/bibliotheque/utilisateurs";
    private static final Gson gson = new Gson();

    public int enregistrer(Utilisateur utilisateur) {
        String data = gson.toJson(utilisateur);
        return httpPost(URL + "/enregistrer", data);
    }

    public int login(Utilisateur utilisateur) {
        String data = gson.toJson(utilisateur);
        return httpPost(URL + "/login", data);
    }

    public HttpData getUtilisateurs() {
        return httpGet(URL);
    }

    public HttpData getUtilisateur(String pkUtilisateur) {
        return httpGet(URL + "/" + pkUtilisateur);
    }


}

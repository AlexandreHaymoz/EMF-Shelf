package emf.haymoz.gatewayapi.service;

import com.google.gson.Gson;
import emf.haymoz.gatewayapi.model.HttpData;
import emf.haymoz.gatewayapi.model.Utilisateur;

import static emf.haymoz.gatewayapi.service.CommonService.*;


public class UtilisateurService {
    private static final String URL = "https://haymozn.emf-informatique.ch/java_compteREST/bibliotheque/utilisateurs";
    private static final Gson gson = new Gson();

    public HttpData enregistrer(Utilisateur utilisateur) {
        String data = gson.toJson(utilisateur);
        return httpRequest(URL + "/enregistrer", data, "POST");
    }

    public HttpData connecter(Utilisateur utilisateur) {
        String data = gson.toJson(utilisateur);
        System.out.println(data);
        return httpRequest(URL + "/connecter", data, "POST");
    }

    public HttpData getUtilisateurs() {
        return httpRequest(URL, null, "GET");
    }

    public HttpData getUtilisateur(String pkUtilisateur) {
        return httpRequest(URL + "/" + pkUtilisateur, null, "GET");
    }


}

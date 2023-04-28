package emf.haymoz.gatewayapi.service;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import emf.haymoz.gatewayapi.model.HttpData;
import emf.haymoz.gatewayapi.model.Livre;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static emf.haymoz.gatewayapi.service.CommonService.*;

public class LivreService {
    // URL du Rest
    private static final String URL = "https://clapassonn.emf-informatique.ch/javaLivreREST/bibliotheque/livres";

    // Pour serialiser des objets Java en JSON
    private static final Gson gson = new Gson();


    public HttpData getLivres() {
        return httpGet(URL);
    }

    public HttpData getLivre(String pk) {
        return httpGet(URL + "/" + pk);
    }

    public int addLivre(Livre livre){
        String data = gson.toJson(livre);
        return httpPostPutDelete(URL , data, "POST");
    }

    public int modifyLivre(Livre livre){
        String data = gson.toJson(livre);
        return httpPostPutDelete(URL , data, "PUT");
    }

    /**
     * Envoie une requête GET vers une adresse cible et retourne les données.
     *
     * @param urlAppend Un string qui spécifie l'adresse cible
     * @return A VOIR, SUREMENT UN STRING AVEC LES DONNEES
     */

}

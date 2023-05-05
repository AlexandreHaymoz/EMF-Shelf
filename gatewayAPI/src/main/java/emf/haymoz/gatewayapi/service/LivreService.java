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
        return httpRequest(URL, null, "GET");
    }

    public HttpData getLivre(String pk) {
        return httpRequest(URL + "/" + pk, null, "GET");
    }

    public HttpData addLivre(Livre livre){
        String data = gson.toJson(livre);
        return httpRequest(URL , data, "POST");
    }

    public HttpData modifyLivre(Livre livre){
        String data = gson.toJson(livre);
        return httpRequest(URL , data, "PUT");
    }

    public HttpData deleteLivre(Livre livre){
        String data = gson.toJson(livre);
        return httpRequest(URL , data, "DELETE");
    }


}

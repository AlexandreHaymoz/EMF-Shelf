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

    /**
     * Objet pour convertir les objets en JSON et vice versa
     */
    private static final Gson gson = new Gson();

    /**
     * Récupère tous les livres
     *
     * @return un objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */
    public HttpData getLivres() {
        return httpRequest(URL, null, "GET");
    }

    /**
     * Récupère les informations d'un livre à partir de sa clé primaire.
     *
     * @param pk la clé primaire du livre à récupérer.
     * @return un objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */
    public HttpData getLivre(String pk) {
        return httpRequest(URL + "/" + pk, null, "GET");
    }

    /**
     * Récupère tous les livres associés à un utilisateur à partir de sa clé primaire.
     *
     * @param pk la clé primaire de l'utilisateur.
     * @returnun objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */
    public HttpData getLivresUser(String pk) {
        return httpRequest(URL + "/users/" + pk, null, "GET");
    }

    /**
     * Ajoute un livre à la bibliothèque.
     *
     * @param livre l'objet Livre à ajouter.
     * @return objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */
    public HttpData addLivre(Livre livre) {
        String data = gson.toJson(livre);
        return httpRequest(URL, data, "POST");
    }

    /**
     * Modifie les informations d'un livre à partir de son objet Livre.
     *
     * @param livre l'objet Livre à modifier.
     * @return objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */
    public HttpData modifyLivre(Livre livre) {
        String data = gson.toJson(livre);
        return httpRequest(URL, data, "PUT");
    }

    /**
     * Supprime un livre de la bibliothèque à partir de son objet Livre.
     *
     * @param livre l'objet Livre à supprimer.
     * @return objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */
    public HttpData deleteLivre(Livre livre) {
        String data = gson.toJson(livre);
        return httpRequest(URL, data, "DELETE");
    }


}
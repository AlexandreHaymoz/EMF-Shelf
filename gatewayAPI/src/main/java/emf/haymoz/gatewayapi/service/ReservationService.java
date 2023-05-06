package emf.haymoz.gatewayapi.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import emf.haymoz.gatewayapi.model.HttpData;
import emf.haymoz.gatewayapi.model.Livre;
import emf.haymoz.gatewayapi.model.Reservation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static emf.haymoz.gatewayapi.service.CommonService.httpRequest;

public class ReservationService {
    // URL du Rest
    private static final String URL = "http://clapassonn.emf-informatique.ch/javaLivreREST/bibliotheque/reservations";

    // Pour serialiser des objets Java en JSON
    private static final Gson gson = new GsonBuilder().setDateFormat("MM-dd-yyyy").create();

    /**
     * Récupére toutes les réservations
     *
     * @return  un objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */

    public HttpData getReservations() {
        return httpRequest(URL, null, "GET");
    }

    /**
     * Récupère les informations d'une réservation à partir de sa clé primaire.
     *
     * @param pk la clé primaire de la réservation à récupérer.
     * @return un objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */
    public HttpData getReservation(String pk) {
        return httpRequest(URL + "/" + pk, null, "GET");
    }
    /**
     * Ajoute une réservation
     *
     * @param reservation l'objet Reservation à ajouter.
     * @return objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */
    public HttpData addReservation(Reservation reservation){
        String data = gson.toJson(reservation);
        return httpRequest(URL , data, "POST");
    }
    /**
     * Modifie les informations d'une réservation à partir de son objet Reservation.
     *
     * @param reservation l'objet Reservation à modifier.
     * @return objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */
    public HttpData modifyReservation(Reservation reservation){
        String data = gson.toJson(reservation);
        return httpRequest(URL , data, "PUT");
    }
    /**
     * Supprime une réservation de la bibliothèque à partir de son objet Reservation.
     *
     * @param reservation l'objet Reservation à supprimer.
     * @return objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */
    public HttpData deleteReservation(Reservation reservation){
        String data = gson.toJson(reservation);
        return httpRequest(URL , data, "DELETE");
    }

}

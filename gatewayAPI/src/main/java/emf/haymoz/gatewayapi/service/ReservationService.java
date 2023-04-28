package emf.haymoz.gatewayapi.service;

import com.google.gson.Gson;
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
    private static final Gson gson = new Gson();


    public HttpData getReservations() {
        return httpRequest(URL, null, "GET");
    }

    public HttpData getReservation(String pk) {
        return httpRequest(URL + "/" + pk, null, "GET");
    }

    public HttpData addReservation(Reservation reservation){
        String data = gson.toJson(reservation);
        return httpRequest(URL , data, "POST");
    }

    public HttpData modifyReservation(Reservation reservation){
        String data = gson.toJson(reservation);
        return httpRequest(URL , data, "PUT");
    }

    public HttpData deleteReservation(Reservation reservation){
        String data = gson.toJson(reservation);
        return httpRequest(URL , data, "DELETE");
    }

}

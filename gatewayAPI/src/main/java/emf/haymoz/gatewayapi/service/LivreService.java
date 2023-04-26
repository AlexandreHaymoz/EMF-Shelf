package emf.haymoz.gatewayapi.service;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LivreService {
    // URL du Rest
    private static final String URL = "";

    // Pour serialiser des objets Java en JSON
    private static final Gson gson = new Gson();

    /**
     * Envoie une requête GET vers une adresse cible et retourne les données.
     *
     * @param urlAppend Un string qui spécifie l'adresse cible
     * @return A VOIR, SUREMENT UN STRING AVEC LES DONNEES
     */
    private void httpGet(String urlAppend) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(URL + urlAppend).openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Envoie une requête POST vers une adresse cible et retourne un code HTTP.
     *
     * @param urlAppend Un string qui spécifie l'adresse cible
     * @param data      Le payload de la requête POST
     * @return un code HTTP selon le résultat de la requête POST
     */
    private int httpPost(String urlAppend, String data) {
        int httpCode = 500;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(URL + urlAppend).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            os.close();
            httpCode = conn.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpCode;
    }
}

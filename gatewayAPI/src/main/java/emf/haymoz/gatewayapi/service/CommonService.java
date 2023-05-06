package emf.haymoz.gatewayapi.service;

import emf.haymoz.gatewayapi.model.HttpData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class CommonService {

    /**
     * Permet d'envoyer une requête HTTP en fonction de la méthode et de l'URL fournis.
     *
     * @param url l'URL à utiliser pour la requête
     * @param data les données à envoyer dans la requête
     * @param typeMethod la méthode HTTP à utiliser (POST, PUT, DELETE, etc.)
     * @return un objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */

    protected static HttpData httpRequest(String url, String data, String typeMethod) {
        int httpCode = HttpURLConnection.HTTP_INTERNAL_ERROR;
        try {
            // Initialisation
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod(typeMethod);
            conn.setRequestProperty("Content-Type", "application/json");
            // Envoie des données POST - PUT - DELETE - DEBUT
            if (typeMethod.equals("POST") || typeMethod.equals("PUT") || typeMethod.equals("DELETE")) {
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();
                httpCode = conn.getResponseCode();
            }
            // Envoie des données POST - FIN

            // Reception des données GET - DEBUT
            return getData(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HttpData(httpCode, null);
    }

    /**
     * Permet de récupérer les données renvoyées par une requête HTTP.
     *
     * @param conn l'objet HttpURLConnection qui représente la connexion à utiliser pour la récupération des données
     * @return un objet HttpData contenant le code de réponse HTTP et les données renvoyées par le serveur (s'il y en a)
     */
    private static HttpData getData(HttpURLConnection conn) {
        try {
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return new HttpData(HttpURLConnection.HTTP_OK, response.toString());
            }
            return new HttpData(responseCode, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HttpData(HttpURLConnection.HTTP_INTERNAL_ERROR, null);
    }
}

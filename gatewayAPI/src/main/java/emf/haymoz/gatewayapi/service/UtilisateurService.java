package emf.haymoz.gatewayapi.service;

import com.google.gson.Gson;
import emf.haymoz.gatewayapi.model.Utilisateur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class UtilisateurService {

    private static final String URL = "https://www.google.com/";
    private static final Gson gson = new Gson();

    public int enregistrer(Utilisateur utilisateur) {
        String data = gson.toJson(utilisateur);
        return httpPost("enregistrer", data);
    }

    public int login(Utilisateur utilisateur) {
        String data = gson.toJson(utilisateur);
        return httpPost("login", data);
    }
    public void getUtilisateurs() {
        httpGet("");
    }

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

package emf.haymoz.gatewayapi.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UtilisateurService {

    private static final String URL = "http://example.com";

    public String enregistrer(String nom, String motDePasse) {
        String data = "nom=" + URLEncoder.encode(nom, StandardCharsets.UTF_8) + "&motDePasse=" + URLEncoder.encode(motDePasse, StandardCharsets.UTF_8);
        return httpRequest("enregistrer", data);
    }

    public String login(String nom, String motDePasse) {
        String data = "nom=" + URLEncoder.encode(nom, StandardCharsets.UTF_8) + "&motDePasse=" + URLEncoder.encode(motDePasse, StandardCharsets.UTF_8);
        return httpRequest("login", data);
    }

    private String httpRequest(String urlAppend, String data) {
        String response = null;
        try {
            URL url = new URL(URL + urlAppend);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            os.close();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            response = content.toString();
            System.out.println(content.toString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return response;
    }
}

package emf.haymoz.gatewayapi.service;

import emf.haymoz.gatewayapi.model.HttpData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommonService {

    protected static HttpData httpGet(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response);
                return new HttpData(responseCode, response.toString());
            }
            return new HttpData(responseCode, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HttpData(500, null);
    }

    protected static int httpPostPutDelete(String url, String data, String typeMethod) {
        int httpCode = 500;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod(typeMethod);
            conn.setRequestProperty("Content-Type", "application/json");
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

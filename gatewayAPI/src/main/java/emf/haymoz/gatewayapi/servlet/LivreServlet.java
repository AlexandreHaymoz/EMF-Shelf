package emf.haymoz.gatewayapi.servlet;

import emf.haymoz.gatewayapi.model.HttpData;
import emf.haymoz.gatewayapi.model.Livre;
import emf.haymoz.gatewayapi.model.Utilisateur;
import emf.haymoz.gatewayapi.service.LivreService;
import emf.haymoz.gatewayapi.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LivreServlet", description = "Servlet qui gère les livres", value = {"/livres"})
public class LivreServlet extends HttpServlet {

    LivreService service;

    @Override
    public void init() {
        service = new LivreService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpData httpData = new HttpData(500, "");
        if (action != null) {
            switch (action) {
                case "getLivres" -> {
                    httpData = service.getLivres();
                }
                case "getLivre" -> {
                    String pk = req.getParameter("pk");
                    if (pk != null) {
                        httpData = service.getLivre(pk);
                    } else {
                        handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Mauvaise requête, paramètre PK vide");
                    }
                }
                default -> handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Mauvaise requête, action inconnue");
            }
        } else {
            handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Mauvaise requête, action inconnue");
        }
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        // retourne le json chez l'utilisateur
        out.print(httpData.data());
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lecture du payload d'une requête POST
        Map<String, String> body = new HashMap<>();
        String requestBody = req.getReader().readLine();
        if (requestBody != null) {
            requestBody = java.net.URLDecoder.decode(requestBody, StandardCharsets.UTF_8);
            Arrays.stream(requestBody.split("&")).map(line -> line.split("=")).filter(pair -> pair.length == 2).forEach(pair -> body.put(pair[0], pair[1]));
        }
        if (checkIsAdmin(req, resp)) {
            if (body.get("titre") != null && body.get("auteur") != null && body.get("description") != null && body.get("image") != null) {
                Livre livre = new Livre();
                livre.setTitre(body.get("titre"));
                livre.setAuteur(body.get("auteur"));
                livre.setDescription(body.get("description"));
                livre.setImage(body.get("image"));
                resp.setStatus(service.addLivre(livre).httpCode());
            } else {
                handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Mauvaise requête, paramètre vide");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> body = new HashMap<>();
        String requestBody = req.getReader().readLine();
        if (requestBody != null) {
            requestBody = java.net.URLDecoder.decode(requestBody, StandardCharsets.UTF_8);
            Arrays.stream(requestBody.split("&")).map(line -> line.split("=")).filter(pair -> pair.length == 2).forEach(pair -> body.put(pair[0], pair[1]));
        }
        if (checkIsAdmin(req, resp)) {
            if (body.get("PK_livre") != null && body.get("titre") != null && body.get("auteur") != null && body.get("description") != null && body.get("image") != null && body.get("disponible") != null) {
                Livre livre = new Livre();
                livre.setPK_Livre(Integer.parseInt(body.get("PK_livre")));
                livre.setTitre(body.get("titre"));
                livre.setAuteur(body.get("auteur"));
                livre.setDescription(body.get("description"));
                livre.setImage(body.get("image"));
                livre.setDisponible(Integer.parseInt(body.get("disponible")));
                resp.setStatus(service.modifyLivre(livre).httpCode());
            } else {
                handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Mauvaise requête, paramètre vide");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> body = new HashMap<>();
        String requestBody = req.getReader().readLine();
        if (requestBody != null) {
            requestBody = java.net.URLDecoder.decode(requestBody, StandardCharsets.UTF_8);
            Arrays.stream(requestBody.split("&")).map(line -> line.split("=")).filter(pair -> pair.length == 2).forEach(pair -> body.put(pair[0], pair[1]));
        }
        if (checkIsAdmin(req, resp)) {
            if (body.get("PK_livre") != null) {
                Livre livre = new Livre();
                livre.setPK_Livre(Integer.parseInt(body.get("PK_livre")));
                resp.setStatus(service.deleteLivre(livre).httpCode());
            } else {
                handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Mauvaise requête, paramètre vide");
            }
        }
    }

    private void handleMauvaiseRequete(HttpServletResponse resp, int httpCode, String message) throws IOException {
        resp.setStatus(httpCode);
        resp.getWriter().write(message);
    }

    private boolean checkIsAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isOk = false;
        if (req.getSession().getAttribute("utilisateur") != null) {
            if (((Utilisateur) req.getSession().getAttribute("utilisateur")).isAdministrateur()) {
                isOk = true;
            } else {
                handleMauvaiseRequete(resp, HttpURLConnection.HTTP_FORBIDDEN, "Accès refusée, vous n'êtes pas admin");
            }
        } else {
            handleMauvaiseRequete(resp, HttpURLConnection.HTTP_UNAUTHORIZED, "Accès non autorisée, vous n'êtes pas connecté");
        }
        return isOk;
    }
}

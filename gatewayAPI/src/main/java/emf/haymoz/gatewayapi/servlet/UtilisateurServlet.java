package emf.haymoz.gatewayapi.servlet;

import emf.haymoz.gatewayapi.model.Utilisateur;
import emf.haymoz.gatewayapi.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet(
        name = "UtilisateurServlet",
        description = "Servlet qui gère les utilisateurs",
        urlPatterns = {"/utilisateurs"}
)
public class UtilisateurServlet extends HttpServlet {
    UtilisateurService service;

    @Override
    public void init() throws ServletException {
        service = new UtilisateurService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action") == null) {
            service.getUtilisateurs();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> body = new HashMap<>();
        String requestBody = req.getReader().readLine();
        if (requestBody != null) {
            Arrays.stream(requestBody.split("&"))
                    .map(line -> line.split("="))
                    .filter(pair -> pair.length == 2)
                    .forEach(pair -> body.put(pair[0], pair[1]));
            String action = body.get("action");
            if (action != null) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNom(body.get("nom"));
                utilisateur.setMotDePasse(body.get("motDePasse"));
                switch (action) {
                    case "enregistrer" -> handleEnregistrer(resp, service.enregistrer(utilisateur));
                    case "connecter" -> handleConnecter(req, resp, service.login(utilisateur));
                    default -> handleMauvaiseRequete(resp, "Action inconnue");
                }
            } else {
                handleMauvaiseRequete(resp, "Mauvaise requête");
            }

        }
    }

    private void handleMauvaiseRequete(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().write(message);
    }

    private void handleConnecter(HttpServletRequest req, HttpServletResponse resp, int httpCode) {
        if (httpCode == 200) {
            HttpSession session = req.getSession();
            // Continue...
        } else {
            resp.setStatus(httpCode);
        }
    }

    private void handleEnregistrer(HttpServletResponse resp, int httpCode) {
        resp.setStatus(httpCode);
    }
}
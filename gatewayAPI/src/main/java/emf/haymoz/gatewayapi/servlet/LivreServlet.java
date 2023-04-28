package emf.haymoz.gatewayapi.servlet;

import emf.haymoz.gatewayapi.model.HttpData;
import emf.haymoz.gatewayapi.model.Livre;
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
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet(
        name = "LivreServlet",
        description = "Servlet qui gère les livres",
        value = {"/livres"}
)
public class LivreServlet extends HttpServlet {

    LivreService service;

    @Override
    public void init() throws ServletException {
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
                    }
                }

            }
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
        String requestBody = java.net.URLDecoder.decode(req.getReader().readLine(), StandardCharsets.UTF_8.name());
        if (requestBody != null) {
            Arrays.stream(requestBody.split("&"))
                    .map(line -> line.split("="))
                    .filter(pair -> pair.length == 2)
                    .forEach(pair -> body.put(pair[0], pair[1]));
        }
        String action = body.get("action");

        if (action != null) {
            switch (action) {
                case "addLivre" -> {
                    if (body.get("titre") != null && body.get("auteur") != null && body.get("description") != null && body.get("image") != null) {
                        Livre livre = new Livre();
                        livre.setTitre(body.get("titre"));
                        livre.setAuteur(body.get("auteur"));
                        livre.setDescription(body.get("description"));
                        livre.setImage(body.get("image"));
                        resp.setStatus(service.addLivre(livre).httpCode());
                    } else {
                        handleMauvaiseRequete(resp, "Mauvaise requête, paramètre livre vide");
                    }
                }
                case "modifyLivre" -> {
                    if (body.get("PK_livre") != null && body.get("titre") != null && body.get("auteur") != null && body.get("description") != null && body.get("image") != null && body.get("disponible") != null) {
                        Livre livre = new Livre();
                        livre.setPK_Livre(Integer.parseInt(body.get("PK_Livre")));
                        livre.setTitre(body.get("titre"));
                        livre.setAuteur(body.get("auteur"));
                        livre.setDescription(body.get("description"));
                        livre.setImage(body.get("image"));
                        livre.setDisponible(Integer.parseInt(body.get("disponible")));
                        resp.setStatus(service.modifyLivre(livre).httpCode());
                    } else {
                        handleMauvaiseRequete(resp, "Mauvaise requête, paramètre livre vide");
                    }
                }
            }
        }
    }


    private void handleMauvaiseRequete(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().write(message);
    }
}

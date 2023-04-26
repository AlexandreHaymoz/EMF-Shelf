package emf.haymoz.gatewayapi.servlet;

import emf.haymoz.gatewayapi.service.LivreService;
import emf.haymoz.gatewayapi.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet(
        name = "LivreServlet",
        description = "Servlet qui gère les réservations et les livres",
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
       /* if (req.getParameter("action") == null) {
            service.getUtilisateurs();
        } */
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lecture du payload d'une requête POST
        Map<String, String> body = new HashMap<>();
        String requestBody = req.getReader().readLine();
        if (requestBody != null) {
            Arrays.stream(requestBody.split("&"))
                    .map(line -> line.split("="))
                    .filter(pair -> pair.length == 2)
                    .forEach(pair -> body.put(pair[0], pair[1]));
        }

    }
}

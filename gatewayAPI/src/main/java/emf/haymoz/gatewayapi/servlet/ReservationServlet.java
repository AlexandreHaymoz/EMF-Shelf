package emf.haymoz.gatewayapi.servlet;

import emf.haymoz.gatewayapi.service.LivreService;
import emf.haymoz.gatewayapi.service.ReservationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet(
        name = "ReservationServlet",
        description = "Servlet qui gère les réservations et les livres",
        value = {"/reservations"}
)
public class ReservationServlet extends HttpServlet {

    ReservationService service;

    @Override
    public void init() throws ServletException {
        service = new ReservationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String data = "";
        if (action != null) {
            switch (action) {
                case "getReservations" -> {
                        data = service.getReservations();
                }
                case "getReservation" -> {
                    String pk = req.getParameter("pk");
                    if (pk != null) {
                        data = service.getReservation(pk);
                    }
                }

            }
        }
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        // retourne le json chez l'utilisateur
        out.print(data);
        out.flush();
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

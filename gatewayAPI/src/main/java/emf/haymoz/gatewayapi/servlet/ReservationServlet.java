package emf.haymoz.gatewayapi.servlet;

import emf.haymoz.gatewayapi.model.HttpData;
import emf.haymoz.gatewayapi.model.Livre;
import emf.haymoz.gatewayapi.model.Reservation;
import emf.haymoz.gatewayapi.service.LivreService;
import emf.haymoz.gatewayapi.service.ReservationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        HttpData httpData = new HttpData(500, "");
        if (action != null) {
            switch (action) {
                case "getReservations" -> {
                        httpData = service.getReservations();
                }
                case "getReservation" -> {
                    String pk = req.getParameter("pk");
                    if (pk != null) {
                        httpData = service.getReservation(pk);
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
        if (body.get("fk_livre") != null && body.get("fk_compte") != null && body.get("retour") != null) {
            Reservation reservation = new Reservation();
            reservation.setFk_livre(Integer.parseInt(body.get("fk_livre")));
            reservation.setFk_compte(Integer.parseInt(body.get("fk_compte")));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            reservation.setRetour(LocalDate.parse(body.get("retour"),formatter));
            resp.setStatus(service.addReservation(reservation).httpCode());
        } else {
            handleMauvaiseRequete(resp, "Mauvaise requête, paramètre livre vide");
        }

    }

    private void handleMauvaiseRequete(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().write(message);
    }
}

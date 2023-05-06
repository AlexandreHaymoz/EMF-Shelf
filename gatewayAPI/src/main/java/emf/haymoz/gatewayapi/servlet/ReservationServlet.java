package emf.haymoz.gatewayapi.servlet;

import emf.haymoz.gatewayapi.model.HttpData;
import emf.haymoz.gatewayapi.model.Livre;
import emf.haymoz.gatewayapi.model.Reservation;
import emf.haymoz.gatewayapi.model.Utilisateur;
import emf.haymoz.gatewayapi.service.LivreService;
import emf.haymoz.gatewayapi.service.ReservationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@WebServlet(name = "ReservationServlet", description = "Servlet qui gère les réservations et les livres", value = {"/reservations"})
public class ReservationServlet extends HttpServlet {

    private ReservationService service;

    /**
     * Initialisation de la servlet.
     */
    @Override
    public void init() throws ServletException {
        service = new ReservationService();
    }

    /**
     * Traitement des requêtes GET.
     *
     * @param req  la requête reçue
     * @param resp la réponse à envoyer
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        String action = req.getParameter("action");
        HttpData httpData = new HttpData(500, "");
        if (action != null) {
            switch (action) {
                case "getReservations" -> {
                    if (checkIsAdmin(req, resp)) {
                        httpData = service.getReservations();
                    }
                }
                case "getReservation" -> {
                    String pk = req.getParameter("pk");
                    if (checkIsAdmin(req, resp)) {
                        if (pk != null) {
                            httpData = service.getReservation(pk);
                        } else {
                            handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Mauvaise requête, paramètre vide");
                        }
                    }
                }
                default -> handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Action inconnue");
            }
        } else {
            handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Action inconnue");
        }
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        // retourne le json chez l'utilisateur
        out.print(httpData.data());
        out.flush();

    }

    /**
     * Traitement des requêtes POST.
     *
     * @param req  la requête reçue
     * @param resp la réponse à envoyer
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        Map<String, String> body = new HashMap<>();
        String requestBody = java.net.URLDecoder.decode(req.getReader().readLine(), StandardCharsets.UTF_8.name());
        if (requestBody != null) {
            Arrays.stream(requestBody.split("&")).map(line -> line.split("=")).filter(pair -> pair.length == 2).forEach(pair -> body.put(pair[0], pair[1]));
        }
        if (req.getSession().getAttribute("utilisateur") != null) {
            if (body.get("fk_livre") != null) {
                Reservation reservation = new Reservation();
                reservation.setFk_livre(Integer.parseInt(body.get("fk_livre")));
                reservation.setFk_compte(((Utilisateur) req.getSession().getAttribute("utilisateur")).getPkUtilisateur());
                reservation.setRetour(Date.valueOf(LocalDate.now().plusDays(30)));
                HttpData x = service.addReservation(reservation);
                resp.setStatus(x.httpCode());
                sendData(resp, x);
            } else {
                handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Mauvaise requête, paramètre vide");
            }
        } else {
            handleMauvaiseRequete(resp, HttpURLConnection.HTTP_UNAUTHORIZED, "Accès non autorisée, vous n'êtes pas connecté");
        }

    }

    /**
     * Traitement des requêtes PUT.
     *
     * @param req  la requête reçue
     * @param resp la réponse à envoyer
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        Map<String, String> body = new HashMap<>();
        String requestBody = java.net.URLDecoder.decode(req.getReader().readLine(), StandardCharsets.UTF_8.name());
        if (requestBody != null) {
            Arrays.stream(requestBody.split("&")).map(line -> line.split("=")).filter(pair -> pair.length == 2).forEach(pair -> body.put(pair[0], pair[1]));
        }
        if (checkIsAdmin(req, resp)) {
            if (body.get("pk_reservation") != null && body.get("fk_livre") != null && body.get("fk_compte") != null && body.get("retour") != null) {
                Reservation reservation = new Reservation();
                reservation.setPk_reservation(Integer.parseInt(body.get("pk_reservation")));
                reservation.setFk_livre(Integer.parseInt(body.get("fk_livre")));
                reservation.setFk_compte(Integer.parseInt(body.get("fk_compte")));
                reservation.setRetour(Date.valueOf(body.get("retour")));
                resp.setStatus(service.modifyReservation(reservation).httpCode());
            } else {
                handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Mauvaise requête, paramètre vide");
            }
        }

    }

    /**
     * Traitement des requêtes DELETE.
     *
     * @param req  la requête reçue
     * @param resp la réponse à envoyer
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        Map<String, String> body = new HashMap<>();
        String requestBody = java.net.URLDecoder.decode(req.getReader().readLine(), StandardCharsets.UTF_8.name());
        if (requestBody != null) {
            Arrays.stream(requestBody.split("&")).map(line -> line.split("=")).filter(pair -> pair.length == 2).forEach(pair -> body.put(pair[0], pair[1]));
        }
        if (checkIsAdmin(req, resp)) {
            if (body.get("pk_reservation") != null) {
                Reservation reservation = new Reservation();
                reservation.setPk_reservation(Integer.parseInt(body.get("pk_reservation")));
                resp.setStatus(service.deleteReservation(reservation).httpCode());
            } else {
                handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Mauvaise requête, paramètre vide");
            }
        }

    }

    /**
     * Gère les requêtes HTTP mal formées en retournant une réponse avec le code d'erreur HTTP approprié et un message d'erreur.
     *
     * @param resp     la réponse HTTP à envoyer
     * @param httpCode le code d'erreur HTTP
     * @param message  le message d'erreur à inclure dans la réponse
     */
    private void handleMauvaiseRequete(HttpServletResponse resp, int httpCode, String message) throws IOException {
        resp.setStatus(httpCode);
        resp.getWriter().write(message);
    }

    /**
     * Permet de vérifier si l'utilisateur connecté est un administrateur.
     *
     * @param req  la requête reçue
     * @param resp la réponse à envoyer
     */
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
    /**
     * Envoie les données HTTP au client sous forme JSON.
     *
     * @param resp     la réponse envoyée.
     * @param httpData Les données HTTP à envoyer au client.
     */
    private void sendData(HttpServletResponse resp, HttpData httpData) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("Application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(httpData.data());
        out.flush();
    }
}

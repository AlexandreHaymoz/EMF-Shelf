package emf.haymoz.gatewayapi.servlet;

import com.google.gson.Gson;
import emf.haymoz.gatewayapi.model.HttpData;
import emf.haymoz.gatewayapi.model.Utilisateur;
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
import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@WebServlet(name = "UtilisateurServlet", description = "Servlet qui gère les utilisateurs", urlPatterns = {"/utilisateurs"})
public class UtilisateurServlet extends HttpServlet {
    private UtilisateurService service;
    private static final Gson gson = new Gson();
    private static final String REGEX_NOM = "^[a-zA-Z]{3,18}$";
    private static final String REGEX_MOTDEPASSE = "(?=^.{8,18}$)((?!.*\\s)(?=.*[A-Z])(?=.*[a-z])(?=(.*\\d){1,}))((?!.*[\",;&|'])|(?=(.*\\W){1,}))(?!.*[\",;&|'])^.*$";

    /**
     * Initialisation de la servlet.
     */
    @Override
    public void init() {
        service = new UtilisateurService();
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
        if (action != null) {
            switch (action) {
                case "getUtilisateur" -> {
                    String pkUtilisateur = req.getParameter("pkUtilisateur");
                    if (pkUtilisateur != null) {
                        sendData(resp, service.getUtilisateur(pkUtilisateur));
                    } else {
                        handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Mauvaise requête");
                    }
                }
            }
        } else {
            sendData(resp, service.getUtilisateurs());
        }
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
        String requestBody = req.getReader().readLine();
        if (requestBody != null) {
            requestBody = java.net.URLDecoder.decode(requestBody, StandardCharsets.UTF_8);
            Arrays.stream(requestBody.split("&")).map(line -> line.split("=")).filter(pair -> pair.length == 2).forEach(pair -> body.put(pair[0], pair[1]));
        }
        String action = body.get("action");
        if (action != null) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(body.get("nom"));
            utilisateur.setMotDePasse(body.get("motDePasse"));
            switch (action) {
                case "enregistrer" -> handleEnregistrer(req, resp, utilisateur);
                case "connecter" -> handleConnecter(req, resp, utilisateur);
                case "deconnecter" -> handleDeconnecter(req, resp);
                default -> handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Action inconnue");
            }
        } else {
            handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Mauvaise requête");
        }

    }

    /**
     * Gère les requêtes de deconnecter en invalidant la session si l'utilisateur est connecté.
     *
     * @param req  la requête reçue.
     * @param resp la réponse envoyée.
     */
    private void handleDeconnecter(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!(req.getSession().getAttribute("utilisateur") == null)) {
            req.getSession().setAttribute("utilisateur", null);
        } else {
            handleMauvaiseRequete(resp, HttpURLConnection.HTTP_UNAUTHORIZED, "Accès non autorisée, vous n'êtes pas connecté");
        }
    }

    /**
     * Gère une mauvaise requête en retournant une réponse avec un code d'erreur HTTP et un message d'erreur.
     *
     * @param resp     la requête reçue.
     * @param httpCode la réponse envoyée.
     */
    private void handleMauvaiseRequete(HttpServletResponse resp, int httpCode, String message) throws IOException {
        resp.setStatus(httpCode);
        resp.getWriter().write(message);
    }

    /**
     * Gère la connexion d'un utilisateur en renvoyant un JSON de l'utilisateur vers le client de la requête.
     *
     * @param req         la requête reçue.
     * @param resp        la réponse envoyée.
     * @param utilisateur l'utilisateur qui souhaite se connecter
     */
    private void handleConnecter(HttpServletRequest req, HttpServletResponse resp, Utilisateur utilisateur) throws IOException {
        if (req.getSession().getAttribute("utilisateur") == null) {
            if (utilisateur.getNom() == null || utilisateur.getMotDePasse() == null) {
                utilisateur.setNom("");
                utilisateur.setMotDePasse("");
            }
            HttpData httpCode = service.connecter(utilisateur);
            if (httpCode.httpCode() == HttpURLConnection.HTTP_OK) {
                HttpSession session = req.getSession();
                utilisateur = gson.fromJson(httpCode.data(), Utilisateur.class);
                session.setAttribute("utilisateur", utilisateur);
                resp.setHeader("Set-Cookie", "JSESSIONID=" + session.getId() + "; HttpOnly; SameSite=none; secure");
            }
            resp.setStatus(httpCode.httpCode());
            sendData(resp, httpCode);
        } else {
            handleMauvaiseRequete(resp, HttpURLConnection.HTTP_FORBIDDEN, "Vous êtes déjà connecté");
        }
    }

    /**
     * Gère la requête d'enregistrement d'un utilisateur en renvoyant un code HTTP.
     *
     * @param req         la requête reçue.
     * @param resp        la réponse envoyée.
     * @param utilisateur l'utilisateur qui souhaite s'enregistrer
     */
    private void handleEnregistrer(HttpServletRequest req, HttpServletResponse resp, Utilisateur utilisateur) throws IOException {
        if (req.getSession().getAttribute("utilisateur") == null) {
            if (utilisateur.getNom() != null && utilisateur.getNom().matches(REGEX_NOM)) {
                if (utilisateur.getMotDePasse() != null && utilisateur.getMotDePasse().matches(REGEX_MOTDEPASSE)) {
                    HttpData httpCode = service.enregistrer(utilisateur);
                    resp.setStatus(httpCode.httpCode());
                } else {
                    handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Le mot de passe doit comporter au moins 8 caractères et au maximum 18 caractères, dont au moins une lettre majuscule, au moins une lettre minuscule et au moins un chiffre. Les caractères spéciaux \",;&|' ne sont pas autorisés.");
                }
            } else {
                handleMauvaiseRequete(resp, HttpURLConnection.HTTP_BAD_REQUEST, "Le nom doit comporter au moins 8 à 18 caractères sans casse de A à Z uniquement");
            }
        } else {
            handleMauvaiseRequete(resp, HttpURLConnection.HTTP_FORBIDDEN, "Vous êtes déjà connecté");
        }
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
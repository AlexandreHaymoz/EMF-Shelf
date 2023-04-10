package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Role : Comme un controller ou un script PHP
 */
@WebServlet(
        name = "UtilisateurServlet",
        description = "Servlet qui gère les utilisateurs",
        urlPatterns = {"/utilisateurs"}
)
public class UtilisateurServlet extends HttpServlet {
    /**
     * Initialisation du Servlet appelée une unique fois AVANT qu'une première requête ne soit reçue. Peut être effacée
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}

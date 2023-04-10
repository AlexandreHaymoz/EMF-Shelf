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
        name = "LivreServlet",
        description = "Servlet qui gère les réservations et les livres",
        urlPatterns = {"/livres"}
)
public class LivreServlet extends HttpServlet {
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

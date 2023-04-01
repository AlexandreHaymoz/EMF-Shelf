package service;

import com.google.gson.Gson;
import database.dao.UtilisateurDao;
import model.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;


@Path("/utilisateurs")
public class UtilisateurService {
    private static final Gson gson = new Gson();
    private static final UtilisateurDao utilisateurDao = new UtilisateurDao();

    /**
     * Récupère tous les utilisateurs
     *
     * @return Une réponse 200 avec un message de succès si tous c'est bien passé, sinon une réponse 500 si la base de
     * données n'arrive pas à procéder la requête.
     */
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getUsers() {
        try {
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(utilisateurDao.getAll()))
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } catch (SQLException e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Tente de récupérer un utilisateur.
     *
     * @param pk La PK de l'utilisateur qu'on cherche à récupérer
     * @return Une réponse 200 avec un message de succès si tous c'est bien passé, sinon une réponse 404 si la PK
     * n'existe pas ou 500 si la base de données n'arrive pas à procéder la requête.
     */
    @Path("/{pk}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getUser(@PathParam("pk") int pk) {
        try {
            Utilisateur utilisateur = utilisateurDao.get(pk, null);
            if (utilisateur == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(utilisateur))
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } catch (SQLException e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Tente d'enregistrer un nouvel utilisateur dans la base de donnée
     *
     * @param json Les informations de l'enregistrement encodées en JSON
     * @return Une réponse 200 avec un message de succès si tous c'est bien passé, sinon une réponse 409 si le nom
     * existe déjà ou 500 si la base de données n'arrive pas à procéder la requête.
     */
    @Path("/enregistrer")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response enregistrer(String json) {
        Utilisateur utilisateur = gson.fromJson(json, Utilisateur.class);
        utilisateur.setMotDePasse(BCrypt.hashpw(utilisateur.getMotDePasse(), BCrypt.gensalt()));
        try {
            utilisateurDao.save(utilisateur);
        } catch (SQLException e) {
            // Le nom d'utilisateur existe déjà
            if (e.getErrorCode() == 1062) {
                throw new WebApplicationException(Response.Status.CONFLICT);
            }
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.status(Response.Status.OK)
                .entity(utilisateur.getNom() + " a été enregistré avec succès")
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    /**
     * Vérifie les entrées d'un utilisateur afin de savoir si les identifiants sont correctes.
     *
     * @param json Les informations de connection encodées en JSON
     * @return Une réponse 200 avec un message de succès si tous c'est bien passé, sinon une réponse 401 si les
     * identifiants sont faux ou 500 si la base de données n'arrive pas à procéder la requête.
     */
    @Path("/connecter")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response connecter(String json) {
        Utilisateur utilisateur = gson.fromJson(json, Utilisateur.class);
        try {
            String motDePasse = utilisateur.getMotDePasse();
            utilisateur = utilisateurDao.get(-1, utilisateur.getNom());
            if (utilisateur == null || !BCrypt.checkpw(motDePasse, utilisateur.getMotDePasse())) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        } catch (SQLException e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.status(Response.Status.OK)
                .entity("Succès de la connexion de " + utilisateur.getNom())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    /**
     * Banni ou débanni un utilisateur en inversant le champ "banni" de l'utilisateur
     *
     * @param json Les informations de banissement encodées en JSON
     * @return Une réponse 200 avec un message de succès si tous c'est bien passé, sinon une réponse 404 si les
     * informations sont introuvables ou 500 si la base de données n'arrive pas à procéder la requête.
     */
    @Path("/bannir")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response bannir(String json) {
        Utilisateur utilisateur = gson.fromJson(json, Utilisateur.class);
        try {
            utilisateur = utilisateurDao.get(utilisateur.getPkUtilisateur(), null);
            if (utilisateur == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            utilisateur.setBanni(!utilisateur.isBanni());
            utilisateurDao.update(utilisateur);
        } catch (SQLException e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.status(Response.Status.OK)
                .type(MediaType.TEXT_PLAIN)
                .entity(utilisateur.getNom() + " a été " + (utilisateur.isBanni() ? "banni" : "débanni") + " avec succès")
                .build();
    }

}

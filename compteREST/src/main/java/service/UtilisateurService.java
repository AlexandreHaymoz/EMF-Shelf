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

    @Path("/{pk}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String getUser(@PathParam("pk") int pk) {
        try {
            Utilisateur utilisateur = utilisateurDao.get(pk, null);
            if (utilisateur == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return gson.toJson(utilisateur);
        } catch (SQLException e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Path("/enregistrer")
    @Produces(MediaType.APPLICATION_JSON)
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
        return Response.status(Response.Status.OK).entity("Le visiteur a créé un compte avec succès!").build();
    }

    @Path("/connecter")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response connecter(String json) {
        Utilisateur utilisateurDTO = gson.fromJson(json, Utilisateur.class);
        try {
            Utilisateur utilisateur = utilisateurDao.get(-1, utilisateurDTO.getNom());
            if (utilisateur == null || !BCrypt.checkpw(utilisateurDTO.getMotDePasse(), utilisateur.getMotDePasse())) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        } catch (SQLException e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.status(Response.Status.OK).entity("Le visiteur s'est connecté avec succès!").build();
    }

    @Path("/bannir")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response bannir(String json) {
        Utilisateur utilisateurDTO = gson.fromJson(json, Utilisateur.class);
        try {
            Utilisateur utilisateur = utilisateurDao.get(utilisateurDTO.getPkUtilisateur(), null);
            if (utilisateur == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            utilisateur.setBanni(!utilisateur.isBanni());
            utilisateurDao.update(utilisateur);
        } catch (SQLException e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.status(Response.Status.OK).entity("L'utilisateur a été banni avec succès!").build();
    }

}

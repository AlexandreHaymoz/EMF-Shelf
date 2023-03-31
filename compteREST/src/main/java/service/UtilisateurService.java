package service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("utilisateur")
public class UtilisateurService {

    @Path("/enregistrement")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public String enregistrer() {

        return "Hello!";
    }
    @Path("/connexion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public String connecter() {
        return "Hello!";
    }
}

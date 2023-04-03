/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package rest;

import com.google.gson.Gson;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import wrk.WrkLivre;

/**
 * REST Web Service
 *
 * @author clapassonn
 */
@Path("livres")
public class Livres {

    @Context
    private UriInfo context;

    WrkLivre wrk = new WrkLivre();

    /**
     * Creates a new instance of DBLivres
     */
    public Livres() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLivres() {
        Gson builder = new Gson();
        String toJson = builder.toJson(wrk.lireLivres());
        return "{\"type\":" + toJson + "}";
    }

    @GET
    @Path("/{PK_livre}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLivre(@PathParam("PK_livre") int PK_livre) {
        Gson builder = new Gson();
        String toJson = builder.toJson(wrk.lireLivre(PK_livre));
        return "{\"type\":" + toJson + "}";
    }

    @POST
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public String addLivre(@FormParam("titre") String titre, @FormParam("auteur") String auteur, @FormParam("description") String description, @FormParam("image") String image) {
        String s;
        if (wrk.ajouterLivre(titre, auteur, description, image)) {
            s = "OK";
        } else {
            s = "KO";
        }
        return s;
    }

    @PUT
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public String modifyLivre(@FormParam("titre") String titre, @FormParam("auteur") String auteur, @FormParam("description") String description,
            @FormParam("image") String image, @FormParam("disponible") int disponible, @FormParam("PK_livre") int PK_livre) {
        String s;
        if (wrk.modifyLivre(titre, auteur, description, image, disponible, PK_livre)) {
            s = "OK";
        } else {
            s = "KO";
        }
        return s;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public String deleteLivre(@FormParam("PK_livre") int PK_livre) {
        String s;
        if (wrk.deleteLivre(PK_livre)) {
            s = "OK";
        } else {
            s = "KO";
        }
        return s;
    }
}

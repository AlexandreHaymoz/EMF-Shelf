/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package rest;

import bean.Livre;
import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
        return toJson;
    }

    @GET
    @Path("/{PK_livre}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLivre(@PathParam("PK_livre") int PK_livre) {
        Gson builder = new Gson();
        String toJson = builder.toJson(wrk.lireLivre(PK_livre));
        return toJson;
    }
    
    @GET
    @Path("/users/{PK_compte}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLivresUser(@PathParam("PK_compte") int PK_compte) {
        Gson builder = new Gson();
        String toJson = builder.toJson(wrk.lireLivresUser(PK_compte));
        return toJson;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addLivre(String json) {
        Gson builder = new Gson();
        Livre livre = builder.fromJson(json, Livre.class);
        String s;
        if (wrk.ajouterLivre(livre.getTitre(), livre.getAuteur(), livre.getDescription(), livre.getImage())) {
            s = "OK";
        } else {
            s = "KO";
        }
        return s;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modifyLivre(String json) {
        Gson builder = new Gson();
        Livre livre = builder.fromJson(json, Livre.class);
        String s;
        if (wrk.modifyLivre(livre.getTitre(), livre.getAuteur(), livre.getDescription(), livre.getImage(), livre.getDisponible(), livre.getPK_Livre())) {
            s = "OK";
        } else {
            s = "KO";
        }
        return s;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteLivre(String json) {
        Gson builder = new Gson();
        Livre livre = builder.fromJson(json, Livre.class);
        String s;
        if (wrk.deleteLivre(livre.getPK_Livre())) {
            s = "OK";
        } else {
            s = "KO";
        }
        return s;
    }
}

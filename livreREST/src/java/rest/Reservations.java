/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package rest;

import com.google.gson.Gson;
import java.sql.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import wrk.WrkReservation;

/**
 * REST Web Service
 *
 * @author clapassonn
 */
@Path("reservations")
public class Reservations {

    @Context
    private UriInfo context;

    WrkReservation wrk = new WrkReservation();
    /**
     * Creates a new instance of Reservations
     */
    public Reservations() {
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getReservations() {
        Gson builder = new Gson();
        String toJson = builder.toJson(wrk.lireReservations());
        return "{\"type\":" + toJson + "}";
    }

    @GET
    @Path("/{PK_reservation}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getReservation(@PathParam("PK_reservation") int PK_reservation) {
        Gson builder = new Gson();
        String toJson = builder.toJson(wrk.lireReservation(PK_reservation));
        return "{\"type\":" + toJson + "}";
    }

    @POST
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public String addReservation(@FormParam("FK_livre") int FK_livre, @FormParam("FK_compte") int FK_compte, @FormParam("retour") Date retour) {
        String s;
        if (wrk.ajouterReservation(FK_livre, FK_compte, retour)) {
            s = "OK";
        } else {
            s = "KO";
        }
        return s;
    }

    @PUT
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public String modifyReservation(@FormParam("FK_livre") int FK_livre, @FormParam("FK_compte") int FK_compte, @FormParam("retour") Date retour, @FormParam("PK_reservation") int PK_reservation) {
        String s;
        if (wrk.modifyReservation(FK_livre, FK_compte, retour, PK_reservation)) {
            s = "OK";
        } else {
            s = "KO";
        }
        return s;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public String deleteReservation(@FormParam("PK_reservation") int PK_reservation) {
        String s;
        if (wrk.deleteReservation(PK_reservation)) {
            s = "OK";
        } else {
            s = "KO";
        }
        return s;
    }
}

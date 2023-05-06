/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package rest;

import bean.Reservation;
import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.DELETE;
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
        return toJson;
    }

    @GET
    @Path("/{PK_reservation}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getReservation(@PathParam("PK_reservation") int PK_reservation) {
        Gson builder = new Gson();
        String toJson = builder.toJson(wrk.lireReservation(PK_reservation));
        return toJson;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addReservation(String json) {
        Gson builder = new Gson();
        Reservation reservation = builder.fromJson(json, Reservation.class);
        String s;
        if (wrk.ajouterReservation(reservation.getFk_livre(), reservation.getFk_compte(), reservation.getRetour())) {
            s = "OK";
        } else {
            s = "KO";
        }
        return s;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modifyReservation(String json) {
        Gson builder = new Gson();
        Reservation reservation = builder.fromJson(json, Reservation.class);
        String s;
        if (wrk.modifyReservation(reservation.getFk_livre(), reservation.getFk_compte(), reservation.getRetour(), reservation.getPk_reservation())) {
            s = "OK";
        } else {
            s = "KO";
        }
        return s;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteReservation(String json) {
        Gson builder = new Gson();
        Reservation reservation = builder.fromJson(json, Reservation.class);
        String s;
        if (wrk.deleteReservation(reservation.getPk_reservation())) {
            s = "OK";
        } else {
            s = "KO";
        }
        return s;
    }
}

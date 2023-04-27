/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import java.sql.Date;

/**
 *
 * @author clapassonn
 */
public class Reservation {

    public Reservation(int pk_reservation, int fk_livre, int fk_compte, Date retour) {
        this.pk_reservation = pk_reservation;
        this.fk_livre = fk_livre;
        this.fk_compte = fk_compte;
        this.retour = retour;
    }

    public int getPk_reservation() {
        return pk_reservation;
    }

    public void setPk_reservation(int pk_reservation) {
        this.pk_reservation = pk_reservation;
    }

    public int getFk_livre() {
        return fk_livre;
    }

    public void setFk_livre(int fk_livre) {
        this.fk_livre = fk_livre;
    }

    public int getFk_compte() {
        return fk_compte;
    }

    public void setFk_compte(int fk_compte) {
        this.fk_compte = fk_compte;
    }

    public Date getRetour() {
        return retour;
    }

    public void setRetour(Date retour) {
        this.retour = retour;
    }
    

    int pk_reservation;
    int fk_livre;
    int fk_compte;
    Date retour;
}

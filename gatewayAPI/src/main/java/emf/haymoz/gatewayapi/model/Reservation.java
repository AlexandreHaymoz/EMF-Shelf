package emf.haymoz.gatewayapi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Reservation implements Serializable {

    public Reservation() {

    }

    public Reservation(int pk_reservation, int fk_livre, int fk_compte, LocalDate retour) {
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

    public LocalDate getRetour() {
        return retour;
    }

    public void setRetour(LocalDate retour) {
        this.retour = retour;
    }

    private int pk_reservation;
    private int fk_livre;
    private int fk_compte;
    private LocalDate retour;

}

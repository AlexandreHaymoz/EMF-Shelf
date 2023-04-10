package model;

import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {

    public Reservation() {

    }

    public Reservation(int fk_livre, int fk_compte, Date retour) {
        this.fk_livre = fk_livre;
        this.fk_compte = fk_compte;
        this.retour = retour;
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

    private int fk_livre;
    private int fk_compte;
    private Date retour;

}

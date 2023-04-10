package model;

import java.io.Serializable;

public class Livre implements Serializable {
    public Livre() {

    }

    public Livre(String titre, String auteur, String description, String image, int disponible) {
        this.titre = titre;
        this.auteur = auteur;
        this.description = description;
        this.image = image;
        this.disponible = disponible;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    private String titre;
    private String auteur;
    private String description;
    private String image;
    private int disponible;
}

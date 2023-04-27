/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author clapassonn
 */
public class Livre {

    public Livre(int PK_Livre, String titre, String auteur, String description, String image, int disponible) {
        this.PK_Livre = PK_Livre;
        this.titre = titre;
        this.auteur = auteur;
        this.description = description;
        this.image = image;
        this.disponible = disponible;
    }



    public int getPK_Livre() {
        return PK_Livre;
    }

    public void setPK_Livre(int PK_Livre) {
        this.PK_Livre = PK_Livre;
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
    
    int PK_Livre;
    String titre;
    String auteur;
    String description;
    String image;
    int disponible;
}

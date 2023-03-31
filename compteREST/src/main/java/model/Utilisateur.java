package model;

public class Utilisateur {

    private int pkUtilisateur;
    private String nom;
    private String motDePasse;
    private boolean administrateur;
    private boolean banni;

    public int getPkUtilisateur() {
        return pkUtilisateur;
    }

    public void setPkUtilisateur(int pkUtilisateur) {
        this.pkUtilisateur = pkUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean isAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(boolean administrateur) {
        this.administrateur = administrateur;
    }

    public boolean isBanni() {
        return banni;
    }

    public void setBanni(boolean banni) {
        this.banni = banni;
    }
}

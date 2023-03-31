package database.dao;

import database.ConnexionDB;
import model.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDao {

    private final Connection con = ConnexionDB.getInstance().getConnection();

    /**
     * Permet de récupérer un utilisateur selon sa PK ou son nom
     * @param pk La pk de l'utilisateur qu'on souhaite récupérer
     * @param nom Le nom de l'utilisateur qu'on souhaite récupérer
     * @return L'utilisateur
     */

    public Utilisateur get(int pk, String nom) throws SQLException {
        Utilisateur utilisateur = null;
        PreparedStatement statement = null;

        if (pk != -1) {
            statement = con.prepareStatement("SELECT * FROM t_utilisateur WHERE pk_utilisateur = ?");
            statement.setInt(1, pk);
        } else if (nom != null) {
            statement = con.prepareStatement("SELECT * FROM t_utilisateur WHERE nom like ?");
            statement.setString(1, nom);
        }
        if (statement != null) {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setPkUtilisateur(result.getInt("pk_utilisateur"));
                utilisateur.setNom(result.getString("nom"));
                utilisateur.setMotDePasse(result.getString("mot_de_passe"));
                utilisateur.setAdministrateur(result.getBoolean("administrateur"));
                utilisateur.setBanni(result.getBoolean("banni"));
            }
            }
        return utilisateur;
    }

    /**
     * Récupère tous les utilisateurs
     *
     * @return La liste de tous les utilisateurs
     */
    /* public List<Utilisateur> getAll() throws SQLException {
        List<Utilisateur> utilisateurs = null;

            ResultSet result = con.prepareStatement("SELECT * FROM t_utilisateur").executeQuery();
            utilisateurs = new ArrayList<>();
            while (result.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setPkUtilisateur(result.getInt("pk_utilisateur"));
                utilisateur.setNom(result.getString("nom"));
                utilisateur.setMotDePasse(result.getString("mot_de_passe"));
                utilisateur.setAdministrateur(result.getBoolean("administrateur"));
                utilisateur.setBanni(result.getBoolean("banni"));
                utilisateurs.add(utilisateur);
            }
        return utilisateurs;
    } */

    /**
     * Sauvegarde un nouvel utilisateur
     *
     * @param utilisateur L'utilisateur qu'on souhaite sauvegarder
     * @throws SQLException
     */
    public void save(Utilisateur utilisateur) throws SQLException {
        PreparedStatement statement = con.prepareStatement("INSERT INTO t_utilisateur (nom, mot_de_passe, administrateur, banni) VALUES (?,?,?,?)");
        statement.setString(1, utilisateur.getNom());
        statement.setString(2, utilisateur.getMotDePasse());
        statement.setBoolean(3, utilisateur.isAdministrateur());
        statement.setBoolean(4, utilisateur.isBanni());
    }

    /**
     * Met à jour un utilisateur
     *
     * @param utilisateur L'utilisateur qu'on souhaite mettre à jour
     * @throws SQLException
     */
    public void update(Utilisateur utilisateur) throws SQLException {
        PreparedStatement statement = con.prepareStatement("UPDATE t_utilisateur SET pk_utilisateur = ?, nom = ?, mot_de_passe = ?, administrateur = ?, banni = ? WHERE pk_utilisateur = ?");
        statement.setInt(1, utilisateur.getPkUtilisateur());
        statement.setString(2, utilisateur.getNom());
        statement.setString(3, utilisateur.getMotDePasse());
        statement.setBoolean(4, utilisateur.isAdministrateur());
        statement.setBoolean(5, utilisateur.isBanni());
        statement.setInt(6, utilisateur.getPkUtilisateur());
    }

    /**
     * Efface un utilisateur
     *
     * @param utilisateur L'utilisateur qu'on souhaite effacer
     * @throws SQLException
     */
    /* public void delete(Utilisateur utilisateur) throws SQLException {
        PreparedStatement statement = con.prepareStatement("DELETE FROM t_utilisateur WHERE ?");
        statement.setInt(1, utilisateur.getPkUtilisateur());
    } */
}

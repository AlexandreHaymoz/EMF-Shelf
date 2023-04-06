package database.dao;

import database.ConnexionDB;
import model.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDao {
    private static final String DELETE = "DELETE FROM t_utilisateur WHERE ?";
    private static final String FIND_ALL = "SELECT * FROM t_utilisateur ORDER BY pk_utilisateur";
    private static final String FIND_BY_PK = "SELECT * FROM t_utilisateur WHERE pk_utilisateur = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM t_utilisateur WHERE nom like ?";
    private static final String INSERT = "INSERT INTO t_utilisateur (nom, mot_de_passe, administrateur, banni) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE t_utilisateur SET pk_utilisateur = ?, nom = ?, mot_de_passe = ?, administrateur = ?, banni = ? WHERE pk_utilisateur = ?";
    private static final int DEFAULT_PK = -1;

    /**
     * Permet de récupérer un utilisateur selon sa PK ou son nom
     *
     * @param pk  La pk de l'utilisateur qu'on souhaite récupérer
     * @param nom Le nom de l'utilisateur qu'on souhaite récupérer
     * @return L'utilisateur
     */

    public Utilisateur get(int pk, String nom) throws SQLException {
        try (Connection con = ConnexionDB.getInstance().getCon();
             PreparedStatement statement = (pk != DEFAULT_PK) ? con.prepareStatement(FIND_BY_PK) : con.prepareStatement(FIND_BY_NAME)) {
            if (pk != DEFAULT_PK) {
                statement.setInt(1, pk);
            } else if (nom != null) {
                statement.setString(1, nom);
            }
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setPkUtilisateur(result.getInt("pk_utilisateur"));
                    utilisateur.setNom(result.getString("nom"));
                    utilisateur.setMotDePasse(result.getString("mot_de_passe"));
                    utilisateur.setAdministrateur(result.getBoolean("administrateur"));
                    utilisateur.setBanni(result.getBoolean("banni"));
                    return utilisateur;
                }
            }
        }
        return null;
    }

    /**
     * Récupère tous les utilisateurs
     *
     * @return La liste de tous les utilisateurs
     */
    public List<Utilisateur> getAll() throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (Connection con = ConnexionDB.getInstance().getCon();
             PreparedStatement statement = con.prepareStatement(FIND_ALL);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setPkUtilisateur(result.getInt("pk_utilisateur"));
                utilisateur.setNom(result.getString("nom"));
                utilisateur.setMotDePasse(result.getString("mot_de_passe"));
                utilisateur.setAdministrateur(result.getBoolean("administrateur"));
                utilisateur.setBanni(result.getBoolean("banni"));
                utilisateurs.add(utilisateur);
            }
        }
        return utilisateurs;
    }

    /**
     * Sauvegarde un nouvel utilisateur
     *
     * @param utilisateur L'utilisateur qu'on souhaite sauvegarder
     * @throws SQLException
     */
    public void save(Utilisateur utilisateur) throws SQLException {
        try (Connection con = ConnexionDB.getInstance().getCon();
             PreparedStatement statement = con.prepareStatement(INSERT)) {
            statement.setString(1, utilisateur.getNom());
            statement.setString(2, utilisateur.getMotDePasse());
            statement.setBoolean(3, utilisateur.isAdministrateur());
            statement.setBoolean(4, utilisateur.isBanni());
            statement.executeUpdate();
        }
    }

    /**
     * Met à jour un utilisateur
     *
     * @param utilisateur L'utilisateur qu'on souhaite mettre à jour
     * @throws SQLException
     */
    public void update(Utilisateur utilisateur) throws SQLException {
        try (Connection con = ConnexionDB.getInstance().getCon();
             PreparedStatement statement = con.prepareStatement(UPDATE)) {
            statement.setInt(1, utilisateur.getPkUtilisateur());
            statement.setString(2, utilisateur.getNom());
            statement.setString(3, utilisateur.getMotDePasse());
            statement.setBoolean(4, utilisateur.isAdministrateur());
            statement.setBoolean(5, utilisateur.isBanni());
            statement.setInt(6, utilisateur.getPkUtilisateur());
            statement.executeUpdate();
        }
    }


    /**
     * Efface un utilisateur
     *
     * @param utilisateur L'utilisateur qu'on souhaite effacer
     * @throws SQLException
     */
    public void delete(Utilisateur utilisateur) throws SQLException {
        try (Connection con = ConnexionDB.getInstance().getCon();
             PreparedStatement statement = con.prepareStatement(DELETE)) {
            statement.setInt(1, utilisateur.getPkUtilisateur());
            statement.execute();
        }
    }
}

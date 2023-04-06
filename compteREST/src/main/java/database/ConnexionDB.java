/*
Auteur : Alexandre Haymoz
Date de creation : 31.03.2023
Date de modification : 31.03.2023
Description : Singleton d'une connexion vers la base de données comptedb
 */

package database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnexionDB {

    private static ConnexionDB instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    /**
     * Cette méthode permet de se connecter à la base de données
     *
     * @throws SQLException
     */
    private ConnexionDB() {
        try {
            getConfig();
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Impossibilité de se connecter à la database. Exception = " + e.getMessage());
        }
    }

    /**
     * Cette méthode permet de retourner l'objet Connnection de DriverManager de l'instance ConnexionDB
     *
     * @return L'objet Connection de DriverManager
     */
    public Connection getConnection() {
        return connection;
    }

    private void getConfig() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("identifiants.properties")) {
            Properties prop = new Properties();
            prop.load(inputStream);
            url = prop.getProperty("database_url");
            username = prop.getProperty("user");
            password = prop.getProperty("password");
        } catch (IOException e) {
            System.out.println("Impossibilité de créer une instance ConnexionDB. Le chemin vers le fichier identifiants.properties est faux. Exception = " + e);
        }

    }

    /**
     * Cette méthode permet de récupérer une nouvelle instance ConnexionDB ou une précedemment créée
     *
     * @return L'instance ConnexionDB
     * @throws SQLException
     */
    public static ConnexionDB getInstance() {
        try {
            if (instance == null || instance.getConnection() == null || instance.getConnection().isClosed()) {
                instance = new ConnexionDB();
            }
        } catch (SQLException e) {
            System.out.println("Impossibilité de créer une nouvelle connexion de l'instance ConnexionDB. Exception = " + e);
        }
        return instance;
    }

}

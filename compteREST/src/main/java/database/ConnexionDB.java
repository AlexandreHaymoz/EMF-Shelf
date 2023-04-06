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
    private static Connection connection;
    private static String url;
    private static String username;
    private static String password;


    private ConnexionDB() {
        try {
            getConfig();
            Class.forName("com.mysql.cj.jdbc.driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ConnexionDB getInstance() {
        if (instance == null) {
            //synchronized block to remove overhead
            synchronized (ConnexionDB.class) {
                if (instance == null) {
                    instance = new ConnexionDB();
                }

            }
        }
        return instance;
    }

    public Connection getCon() {
        return connection;
    }

    private static void getConfig() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("identifiants.properties")) {
            Properties prop = new Properties();
            prop.load(inputStream);
            url = prop.getProperty("database_url");
            username = prop.getProperty("user");
            password = prop.getProperty("password");
        } catch (IOException e) {
            System.out.println("Impossibilité de créer une instance ConnexionDB. Le chemin vers le fichier identifiants.properties est faux. Exception = " + e);
            e.printStackTrace();
        }
    }
}

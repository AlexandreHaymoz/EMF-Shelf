/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wrk;

import bean.Reservation;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author clapassonn
 */
public class WrkReservation {

    private Connection jdbcConnection;

    public WrkReservation() {
        jdbcConnection = null;
    }

    private boolean dbConnect() {
        boolean ok = false;
        try {
            if (jdbcConnection == null) {
                //On spécifie que notre driver est JDBC               
                Class.forName("com.mysql.jdbc.Driver");
                //L'URL se compose de l'adresse de notre base de données, ainsi que
                //la base de données à utiliser.                
                String url = "jdbc:mysql://localhost:3306/clapassonn_EMF_Shelf";
                //On essaie de se connecter à notre URL à partir d'un identifiant. 
                //Ici, le nom d'utilisateur est "root", et il n'y a pas de mot de passe.
                //Si la connexion est réussie, la méthode "getConnection" renverra "true".
                jdbcConnection = DriverManager.getConnection(url, "clapassonn", "bu19POY[aQ}c");
                ok = true;
            }
        } catch (SQLException b) {
            Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, b);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Si la connexion s'est bien passée, on renvoie "true", sinon "false".
        return ok;
    }

    private boolean dbDisconnect() {
        boolean ok = false;

//        On vérifie si une connexion est toujours présente (donc pas nulle)
        if (jdbcConnection
                != null) {
            try {
//                On essaie de fermer la connexion, puis "vide" la variable.
                jdbcConnection.close();
                jdbcConnection = null;
                ok = true;
            } catch (SQLException ex) {
                Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ok;
    }

    public ArrayList<Reservation> lireReservations() {
        //On prépare nos variables.        
        ArrayList<Reservation> resultat = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        //On essaie de se connecter à la base de données. 
        if (dbConnect()) {
            try {
                //On essaie de créer une requête grâce à notre connexion. 
                if ((stmt = jdbcConnection.createStatement()) != null) {
                    //Ce string est la requête SQL qui va récupérer les enregistrements.    
                    String sql = "SELECT * FROM t_reservations";
                    //On exécute la requête et on stocke la réponse dans un "ResulSet"
                    //Si notre "ResulSet" contient quelque chose, c'est qu'on a reçu une réponse !
                    if ((rs = stmt.executeQuery(sql)) != null) {
                        //On effectue une boucle qui va parcourir notre résultat.
                        //La méthode ".next()" avance d'un index renvoie "true"
                        //s'il y a un résultat. Sinon, "false" quand il atteint
                        //un enregistrement null.
                        while (rs.next()) {
                            int livre = rs.getInt("FK_livre");
                            int compte = rs.getInt("FK_compte");
                            Date retour = rs.getDate("retour");
                            Reservation reservation = new Reservation(livre, compte, retour);
                            //On stocke notre String dans notre résultat final.
                            resultat.add(reservation);
                        }
                    }
                    //On ferme le tout pour optimiser les performances.                    
                    rs.close();
                    rs = null;
                    stmt.close();
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
            } //On repasse les variables pour vérifier que tout est bien fermé. //On repasse les variables pour vérifier que tout est bien fermé. //On repasse les variables pour vérifier que tout est bien fermé. //On repasse les variables pour vérifier que tout est bien fermé.
            finally {
                dbDisconnect();
                try {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (stmt != null) {
                        stmt.close();
                        stmt = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultat;
    }
    
    public Reservation lireReservation(int PK_reservation) {
        //On prépare nos variables.        
        Reservation resultat = null;
        Statement stmt = null;
        ResultSet rs = null;
        //On essaie de se connecter à la base de données. 
        if (dbConnect()) {
            try {
                //On essaie de créer une requête grâce à notre connexion. 
                if ((stmt = jdbcConnection.createStatement()) != null) {
                    //Ce string est la requête SQL qui va récupérer l'enregistrement.    
                    String sql = "SELECT * FROM t_reservations where PK_reservation=" + PK_reservation;
                    //On exécute la requête et on stocke la réponse dans un "ResulSet"
                    //Si notre "ResulSet" contient quelque chose, c'est qu'on a reçu une réponse !
                    if ((rs = stmt.executeQuery(sql)) != null) {
                        //On effectue une boucle qui va parcourir notre résultat.
                        //La méthode ".next()" avance d'un index renvoie "true"
                        //s'il y a un résultat. Sinon, "false" quand il atteint
                        //un enregistrement null.
                        while (rs.next()) {
                            int livre = rs.getInt("FK_livre");
                            int compte = rs.getInt("FK_compte");
                            Date retour = rs.getDate("retour");
                            resultat = new Reservation(livre, compte, retour);
                        }
                    }
                    //On ferme le tout pour optimiser les performances.                    
                    rs.close();
                    rs = null;
                    stmt.close();
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
            } //On repasse les variables pour vérifier que tout est bien fermé. //On repasse les variables pour vérifier que tout est bien fermé. //On repasse les variables pour vérifier que tout est bien fermé. //On repasse les variables pour vérifier que tout est bien fermé.
            finally {
                dbDisconnect();
                try {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (stmt != null) {
                        stmt.close();
                        stmt = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultat;
    }

    public boolean ajouterReservation(int FK_livre, int FK_compte, Date retour) {
        //On initialise nos variables. 
        boolean ok = false;
        PreparedStatement prestmt = null;
        ResultSet rs = null;
        //On essaie d'effectuer une connexion à la base de données.
        if (dbConnect()) {
            try {
                //On crée une requête préparée. Notre argument est remplacé par "?".                
                String sql = "INSERT INTO t_reservations (FK_livre, FK_compte, retour) values (?,?,?)";
                //On essaie notre requête préparée si elle est valide et fonctionnelle.
                if ((prestmt = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) != null) {
                    //On insère notre argument dans la requête.                    
                    prestmt.setInt(1, FK_livre);
                    prestmt.setInt(2, FK_compte);
                    prestmt.setDate(3, retour);
                    prestmt.executeUpdate();
                    //Si tout se passe bien, alors notre requête génère des clefs. C'est grâce
                    //à ceci que l'on sait si quelque chose a été inséré !
                    if ((rs = prestmt.getGeneratedKeys()) != null) {
                        if (rs.next()) {
                            ok = true;
                        }
                    }
                }
                //On ferme le tout, pour optimiser l'application.               
                prestmt.close();
                prestmt = null;
                rs.close();
                rs = null;
            } catch (SQLException ex) {
                Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
            } //On vérifie à nouveau si tout est bien fermé. //On vérifie à nouveau si tout est bien fermé. //On vérifie à nouveau si tout est bien fermé. //On vérifie à nouveau si tout est bien fermé.
            finally {
                dbDisconnect();
                try {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (prestmt != null) {
                        prestmt.close();
                        prestmt = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ok;
    }

    public boolean modifyReservation(int FK_livre, int FK_compte, Date retour, int PK_reservation) {
        //On initialise nos variables. 
        boolean ok = false;
        PreparedStatement prestmt = null;
        //On essaie d'effectuer une connexion à la base de données.
        if (dbConnect()) {
            try {
                //On crée une requête préparée. Nos arguments est remplacé par "?".                
                String sql = "UPDATE t_reservations SET FK_livre=?, FK_compte=?, retour=? WHERE PK_reservation=?";
                //On essaie notre requête préparée si elle est valide et fonctionnelle.
                if ((prestmt = jdbcConnection.prepareStatement(sql)) != null) {
                    //On insère notre argument dans la requête.                    
                    prestmt.setInt(1, FK_livre);
                    prestmt.setInt(2, FK_compte);
                    prestmt.setDate(3, retour);
                    prestmt.setInt(4, PK_reservation);
                    //Plutôt que de récupérer les clefs générées, on compte le
                    //nombre de lignes modifiées. La requête ".executeUpdate" 
                    //renvoie le nombre de lignes affectées. On vérifie donc si
                    //c'est supérieur à 0, car dans ce cas, au moins une ligne a été
                    //affectée.
                    int row = prestmt.executeUpdate();
                    ok = row > 0;
                }
                //On ferme le tout, pour optimiser l'application.                
                prestmt.close();
                prestmt = null;
            } catch (SQLException ex) {
                Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
            } //On vérifie à nouveau si tout est bien fermé. //On vérifie à nouveau si tout est bien fermé. //On vérifie à nouveau si tout est bien fermé. //On vérifie à nouveau si tout est bien fermé.
            finally {
                dbDisconnect();
                try {
                    if (prestmt != null) {
                        prestmt.close();
                        prestmt = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ok;
    }

    public boolean deleteReservation(int PK_reservation) {
        //On initialise nos variables. 
        boolean ok = false;
        PreparedStatement prestmt = null;
        //On essaie d'effectuer une connexion à la base de données.
        if (dbConnect()) {
            try {
                //On crée une requête préparée. Notre argument est remplacé par "?".                
                String sql = "DELETE FROM t_reservations WHERE PK_reservation=? ";
                //On essaie notre requête préparée si elle est valide et fonctionnelle.
                if ((prestmt = jdbcConnection.prepareStatement(sql)) != null) {
                    //On insère notre argument dans la requête.                    
                    prestmt.setInt(1, PK_reservation);
                    //Plutôt que de récupérer les clefs générées, on compte le
                    //nombre de lignes modifiées. La requête ".executeUpdate" 
                    //renvoie le nombre de lignes affectées. On vérifie donc si
                    //c'est supérieur à 0, car dans ce cas, au moins une ligne a été
                    //affectée.
                    int row = prestmt.executeUpdate();
                    ok = row > 0;
                }
                //On ferme le tout, pour optimiser l'application.                
                prestmt.close();
                prestmt = null;
            } catch (SQLException ex) {
                Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
            } //On vérifie à nouveau si tout est bien fermé. //On vérifie à nouveau si tout est bien fermé. //On vérifie à nouveau si tout est bien fermé. //On vérifie à nouveau si tout est bien fermé.
            finally {
                dbDisconnect();
                try {
                    if (prestmt != null) {
                        prestmt.close();
                        prestmt = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkReservation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ok;
    }
}

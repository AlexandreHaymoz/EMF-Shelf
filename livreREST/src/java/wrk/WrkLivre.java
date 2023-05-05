/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wrk;

import bean.Livre;
import java.sql.Connection;
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
public class WrkLivre {

    private Connection jdbcConnection;

    public WrkLivre() {
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
            Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, b);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ok;
    }

    public ArrayList<Livre> lireLivres() {
        //On prépare nos variables.        
        ArrayList<Livre> resultat = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        //On essaie de se connecter à la base de données. 
        if (dbConnect()) {
            try {
                //On essaie de créer une requête grâce à notre connexion. 
                if ((stmt = jdbcConnection.createStatement()) != null) {
                    //Ce string est la requête SQL qui va récupérer les enregistrements.    
                    String sql = "SELECT * FROM t_livres";
                    //On exécute la requête et on stocke la réponse dans un "ResulSet"
                    //Si notre "ResulSet" contient quelque chose, c'est qu'on a reçu une réponse !
                    if ((rs = stmt.executeQuery(sql)) != null) {
                        //On effectue une boucle qui va parcourir notre résultat.
                        //La méthode ".next()" avance d'un index renvoie "true"
                        //s'il y a un résultat. Sinon, "false" quand il atteint
                        //un enregistrement null.
                        while (rs.next()) {
                            int PK_livre = rs.getInt("PK_livre");
                            String titre = rs.getString("titre");
                            String auteur = rs.getString("auteur");
                            String description = rs.getString("description");
                            String image = rs.getString("image");
                            int disponible = rs.getInt("disponible");
                            Livre livre = new Livre(PK_livre,titre, auteur, description, image, disponible);
                            //On stocke notre String dans notre résultat final.
                            resultat.add(livre);
                        }
                    }
                    //On ferme le tout pour optimiser les performances.                    
                    rs.close();
                    rs = null;
                    stmt.close();
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
            } //On repasse les variables pour vérifier que tout est bien fermé. //On repasse les variables pour vérifier que tout est bien fermé.
            finally {
                dbDisconnect();
                try {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (stmt != null) {
                        stmt.close();
                        stmt = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultat;
    }
    
    public ArrayList<Livre> lireLivresUser(int PK_user) {
        //On prépare nos variables.        
        ArrayList<Livre> resultat = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        //On essaie de se connecter à la base de données. 
        if (dbConnect()) {
            try {
                //On essaie de créer une requête grâce à notre connexion. 
                if ((stmt = jdbcConnection.createStatement()) != null) {
                    //Ce string est la requête SQL qui va récupérer les enregistrements.    
                    String sql = "SELECT PK_livre, titre, auteur, description, image, disponible FROM t_livres inner join t_reservations on FK_livre = PK_livre where FK_compte=" + PK_user;
                    //On exécute la requête et on stocke la réponse dans un "ResulSet"
                    //Si notre "ResulSet" contient quelque chose, c'est qu'on a reçu une réponse !
                    if ((rs = stmt.executeQuery(sql)) != null) {
                        //On effectue une boucle qui va parcourir notre résultat.
                        //La méthode ".next()" avance d'un index renvoie "true"
                        //s'il y a un résultat. Sinon, "false" quand il atteint
                        //un enregistrement null.
                        while (rs.next()) {
                            int PK_livre = rs.getInt("PK_livre");
                            String titre = rs.getString("titre");
                            String auteur = rs.getString("auteur");
                            String description = rs.getString("description");
                            String image = rs.getString("image");
                            int disponible = rs.getInt("disponible");
                            Livre livre = new Livre(PK_livre,titre, auteur, description, image, disponible);
                            //On stocke notre String dans notre résultat final.
                            resultat.add(livre);
                        }
                    }
                    //On ferme le tout pour optimiser les performances.                    
                    rs.close();
                    rs = null;
                    stmt.close();
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
            } //On repasse les variables pour vérifier que tout est bien fermé. //On repasse les variables pour vérifier que tout est bien fermé.
            finally {
                dbDisconnect();
                try {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (stmt != null) {
                        stmt.close();
                        stmt = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultat;
    }
    
    public Livre lireLivre(int PK_livre) {
        //On prépare nos variables.        
        Livre resultat = null;
        Statement stmt = null;
        ResultSet rs = null;
        //On essaie de se connecter à la base de données. 
        if (dbConnect()) {
            try {
                //On essaie de créer une requête grâce à notre connexion. 
                if ((stmt = jdbcConnection.createStatement()) != null) {
                    //Ce string est la requête SQL qui va récupérer l'enregistrement.    
                    String sql = "SELECT * FROM t_livres where PK_Livre=" + PK_livre;
                    //On exécute la requête et on stocke la réponse dans un "ResulSet"
                    //Si notre "ResulSet" contient quelque chose, c'est qu'on a reçu une réponse !
                    if ((rs = stmt.executeQuery(sql)) != null) {
                        //On effectue une boucle qui va parcourir notre résultat.
                        //La méthode ".next()" avance d'un index renvoie "true"
                        //s'il y a un résultat. Sinon, "false" quand il atteint
                        //un enregistrement null.
                        while (rs.next()) {
                            String titre = rs.getString("titre");
                            String auteur = rs.getString("auteur");
                            String description = rs.getString("description");
                            String image = rs.getString("image");
                            int disponible = rs.getInt("disponible");
                            resultat = new Livre(PK_livre, titre, auteur, description, image, disponible);
                        }
                    }
                    //On ferme le tout pour optimiser les performances.                    
                    rs.close();
                    rs = null;
                    stmt.close();
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
            } //On repasse les variables pour vérifier que tout est bien fermé. //On repasse les variables pour vérifier que tout est bien fermé.
            finally {
                dbDisconnect();
                try {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (stmt != null) {
                        stmt.close();
                        stmt = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultat;
    }

    public boolean ajouterLivre(String titre, String auteur, String description, String image) {
        //On initialise nos variables. 
        boolean ok = false;
        PreparedStatement prestmt = null;
        ResultSet rs = null;
        //On essaie d'effectuer une connexion à la base de données.
        if (dbConnect()) {
            try {
                //On crée une requête préparée. Notre argument est remplacé par "?".                
                String sql = "INSERT INTO t_livres (titre, auteur, description, image) values (?,?,?,?)";
                //On essaie notre requête préparée si elle est valide et fonctionnelle.
                if ((prestmt = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) != null) {
                    //On insère notre argument dans la requête.                    
                    prestmt.setString(1, titre);
                    prestmt.setString(2, auteur);
                    prestmt.setString(3, description);
                    prestmt.setString(4, image);
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
                Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
            } //On vérifie à nouveau si tout est bien fermé. //On vérifie à nouveau si tout est bien fermé.
            finally {
                dbDisconnect();
                try {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (prestmt != null) {
                        prestmt.close();
                        prestmt = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ok;
    }

    public boolean modifyLivre(String titre, String auteur, String description,String image, int disponible, int PK_livre) {
        //On initialise nos variables. 
        boolean ok = false;
        PreparedStatement prestmt = null;
        //On essaie d'effectuer une connexion à la base de données.
        if (dbConnect()) {
            try {
                //On crée une requête préparée. Nos arguments est remplacé par "?".                
                String sql = "UPDATE t_livres SET titre=?, auteur=?, description=?, image=?, disponible=? WHERE PK_livre=?";
                //On essaie notre requête préparée si elle est valide et fonctionnelle.
                if ((prestmt = jdbcConnection.prepareStatement(sql)) != null) {
                    //On insère notre argument dans la requête.                    
                    prestmt.setString(1, titre);
                    prestmt.setString(2, auteur);
                    prestmt.setString(3, description);
                    prestmt.setString(4, image);
                    prestmt.setInt(5, disponible);
                    prestmt.setInt(6, PK_livre);
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
                Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
            } //On vérifie à nouveau si tout est bien fermé. //On vérifie à nouveau si tout est bien fermé.
            finally {
                dbDisconnect();
                try {
                    if (prestmt != null) {
                        prestmt.close();
                        prestmt = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ok;
    }

    public boolean deleteLivre(int PK_livre) {
        //On initialise nos variables. 
        boolean ok = false;
        PreparedStatement prestmt = null;
        //On essaie d'effectuer une connexion à la base de données.
        if (dbConnect()) {
            try {
                //On crée une requête préparée. Notre argument est remplacé par "?".                
                String sql = "DELETE FROM t_livres WHERE PK_livre=? ";
                //On essaie notre requête préparée si elle est valide et fonctionnelle.
                if ((prestmt = jdbcConnection.prepareStatement(sql)) != null) {
                    //On insère notre argument dans la requête.                    
                    prestmt.setInt(1, PK_livre);
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
                Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
            } //On vérifie à nouveau si tout est bien fermé. //On vérifie à nouveau si tout est bien fermé.
            finally {
                dbDisconnect();
                try {
                    if (prestmt != null) {
                        prestmt.close();
                        prestmt = null;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WrkLivre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ok;
    }
}

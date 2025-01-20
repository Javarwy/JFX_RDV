package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String serveur = "localhost:3306";
    private String nomDeLaBase = "jfx";
    private String utilisateur = "root";
    private String motDePasse = "";

    private String getUrl() {
        return "jdbc:mysql://"+this.serveur+"/"+this.nomDeLaBase+"?serverTimezone=UTC";
    }

    public Connection getConnection() {
        try {
            Connection cnx = DriverManager.getConnection(this.getUrl(),this.utilisateur,this.motDePasse);
            System.out.print("Etat de la connexion : ");
            System.out.print(cnx.isClosed()?"fermée":"ouverte \r\n");
            return cnx;
        } catch (SQLException e) {
            System.out.print("Erreur lors de la tentative de connexion à la base de données");
            e.printStackTrace();
            return null;
        }
    }
}

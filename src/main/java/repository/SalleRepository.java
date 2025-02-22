package repository;

import java.sql.*;
import java.util.ArrayList;

import database.Database;
import modeles.Salle;

public class SalleRepository {
    // Modifie les propriétés de la salle dans la base de données pour la mettre "occupée"
    public boolean reserver(Salle salle) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "UPDATE salle SET occupe = 1, professeur_present = ? WHERE id_salle = ?"
        );
        ps.setInt(1, salle.getProfesseur_absent());
        ps.setInt(2, salle.getId_salle());
        ps.executeUpdate();
        PreparedStatement ps2 = db.getConnection().prepareStatement(
          "SELECT occupe, professeur_present FROM salle WHERE id_salle = ?"
        );
        ps2.setInt(1, salle.getId_salle());
        ResultSet rs = ps2.executeQuery();
        if (rs.next()){
            if (rs.getInt("occupe") == 1 && rs.getString("professeur_present") != null){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Modifie les propriétés de la salle dans la base de données pour la mettre "libre"
    public boolean liberer(Salle salle) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
          "UPDATE salle SET occupe = 0, professeur_present = NULL WHERE id_salle = ?"
        );
        ps.setInt(1, salle.getId_salle());
        ps.executeUpdate();
        PreparedStatement ps2 = db.getConnection().prepareStatement(
                "SELECT occupe, professeur_present FROM salle WHERE id_salle = ?"
        );
        ps2.setInt(1, salle.getId_salle());
        ResultSet rs = ps2.executeQuery();
        if (rs.next()){
            if (rs.getInt("occupe") == 0 && rs.getString("professeur_present") == null){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Récupère la liste de toutes les salles libres
    public ArrayList<Salle> getSallesLibres() throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement("SELECT id_salle, nom_salle FROM salle WHERE occupe = 0;");
        ResultSet rs = ps.executeQuery();
        ArrayList<Salle> salles = new ArrayList<>();
        while (rs.next()) {
            salles.add(new Salle(rs.getInt(1), rs.getString(2)));
        }
        return salles;
    }
}

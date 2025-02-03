package repository;

import java.sql.*;
import java.util.ArrayList;

import database.Database;
import modeles.Salle;

public class SalleRepository {
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

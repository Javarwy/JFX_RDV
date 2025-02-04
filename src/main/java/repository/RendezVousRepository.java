package repository;

import java.sql.*;

import database.Database;
import modeles.RendezVous;

public class RendezVousRepository {
    public boolean ajout(RendezVous rendezVous) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "INSERT INTO rendezvous (date_rendezvous, heure_rendezvous, ref_dossier, ref_salle) VALUES (?, ?, ?, ?)"
        );
        ps.setString(1, rendezVous.getDate_rendezvous());
        ps.setString(2, rendezVous.getHeure_rendez());
        ps.setInt(3, rendezVous.getRefDossier().getId_dossier());
        ps.setInt(4, rendezVous.getRefSalle().getId_salle());
        ps.executeUpdate();
        PreparedStatement ps2 = db.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM rendezvous WHERE ref_dossier = ?"
        );
        ps2.setInt(1, rendezVous.getRefDossier().getId_dossier());
        ps2.executeUpdate();
        ResultSet rs = ps2.executeQuery();
        if (rs.next()){
            return true;
        } else {
            return false;
        }
    }
}

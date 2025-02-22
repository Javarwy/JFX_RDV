package repository;

import java.sql.*;
import java.util.ArrayList;

import database.Database;
import modeles.Dossier;
import modeles.Etudiant;

public class DossierRepository {
    // Récupère une liste de tous les dossiers (avec informations de l'étudiant)
    public ArrayList<Dossier> getDossiers() throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement("SELECT d.id_dossier, d.date, d.heure, d.filliere, d.motivation, e.id_etudiant, e.prenomEtudiant, e.nomEtudiant, e.diplome, e.emailEtudiant, e.telephone FROM dossier as d INNER JOIN etudiant as e ON d.ref_etudiant = e.id_etudiant;");
        ResultSet rs = ps.executeQuery();
        ArrayList<Dossier> dossiers = new ArrayList<>();
        while(rs.next()) {
            Etudiant etudiant = new Etudiant(rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
            dossiers.add(new Dossier(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), etudiant));
        }
        return dossiers;
    }

    // Vérifie si un dosiser est déjà pris en charge (rendez-vous déjà créé) ou non
    public boolean estPrisEnCharge(int refDossier) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
          "SELECT COUNT(*) FROM dossier as d INNER JOIN rendezvous as rdv ON rdv.ref_dossier = d.id_dossier WHERE rdv.ref_dossier = ?;"
        );
        ps.setInt(1, refDossier);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            if (rs.getInt(1) == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

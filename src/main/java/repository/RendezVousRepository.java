package repository;

import java.sql.*;
import java.util.ArrayList;

import database.Database;
import modeles.Dossier;
import modeles.RendezVous;
import modeles.Salle;
import modeles.Etudiant;

public class RendezVousRepository {
    public boolean ajout(RendezVous rendezVous) throws SQLException {
        Database db = new Database();
        PreparedStatement deja = db.getConnection().prepareStatement(
          "SELECT COUNT(*) FROM rendezvous WHERE ref_dossier = ?"
        );
        deja.setInt(1, rendezVous.getRefDossier().getId_dossier());
        ResultSet rs = deja.executeQuery();
        if (rs.next()) {
            if (rs.getInt(1) == 0) {
                PreparedStatement ps = db.getConnection().prepareStatement(
                        "INSERT INTO rendezvous (date_rendezvous, heure_rendezvous, ref_dossier, ref_salle) VALUES (?, ?, ?, ?)"
                );
                ps.setString(1, rendezVous.getDate_rendezvous());
                ps.setString(2, rendezVous.getHeure_rendez());
                ps.setInt(3, rendezVous.getRefDossier().getId_dossier());
                ps.setInt(4, rendezVous.getRefSalle().getId_salle());
                ps.executeUpdate();
                ResultSet ps2 = deja.executeQuery();
                if (ps2.next()){
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean modifier(RendezVous rendezVous) throws SQLException {
        Database db = new Database();
        PreparedStatement ps2 = db.getConnection().prepareStatement(
                "UPDATE rendezvous SET date_rendezvous = ?, heure_rendezvous = ?, ref_salle = ? WHERE id_rendezvous = ?"
        );
        ps2.setString(1, rendezVous.getDate_rendezvous());
        ps2.setString(2, rendezVous.getHeure_rendez());
        ps2.setInt(3, rendezVous.getRefSalle().getId_salle());
        ps2.setInt(4, rendezVous.getId_rendezvous());
        ps2.executeUpdate();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "SELECT date_rendezvous, heure_rendezvous, ref_salle FROM rendezvous WHERE id_rendezvous = ?"
        );
        ps.setInt(1, rendezVous.getId_rendezvous());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (rs.getString("date_rendezvous").equals(rendezVous.getDate_rendezvous()) && rs.getString("heure_rendezvous").equals((rendezVous.getHeure_rendez())) && rs.getInt("ref_salle") == rendezVous.getRefSalle().getId_salle()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean annuler(RendezVous rendezVous) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
          "DELETE FROM rendezvous WHERE id_rendezvous = ?"
        );
        ps.setInt(1, rendezVous.getId_rendezvous());
        ps.executeUpdate();
        PreparedStatement ps2 = db.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM rendezvous WHERE id_rendezvous = ?"
        );
        ps2.setInt(1, rendezVous.getId_rendezvous());
        ResultSet rs = ps2.executeQuery();
        if (rs.next()) {
            if (rs.getInt(1) == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public ArrayList<RendezVous> getMesRendezVous(int idUtilisateur) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
          "SELECT rdv.id_rendezvous, rdv.date_rendezvous, rdv.heure_rendezvous, rdv.ref_dossier, rdv.ref_salle, s.nom_salle, s.occupe, s.professeur_present, e.nomEtudiant, e.prenomEtudiant, e.diplome, e.emailEtudiant, e.telephone, d.* FROM rendezvous as rdv INNER JOIN dossier as d ON d.id_dossier = rdv.ref_dossier INNER JOIN etudiant as e ON d.ref_etudiant = e.id_etudiant INNER JOIN salle as s ON s.id_salle = rdv.ref_salle WHERE s.professeur_present = ?;"
        );
        ps.setInt(1, idUtilisateur);
        ResultSet rs = ps.executeQuery();
        ArrayList<RendezVous> rendezVous = new ArrayList<>();
        while (rs.next()) {
            Salle salle = new Salle(rs.getInt("ref_salle"), rs.getString("nom_salle"), rs.getBoolean("occupe"), rs.getInt("professeur_present"));
            Etudiant etudiant = new Etudiant(rs.getInt("ref_etudiant"), rs.getString("nomEtudiant"), rs.getString("prenomEtudiant"), rs.getString("diplome"), rs.getString("emailEtudiant"), rs.getString("telephone"));
            Dossier dossier = new Dossier(rs.getInt("ref_dossier"), rs.getString("date"), rs.getString("heure"), rs.getString("filliere"), rs.getString("motivation"), etudiant);
            rendezVous.add(new RendezVous(rs.getInt("id_rendezvous"), rs.getString("date_rendezvous"), rs.getString("heure_rendezvous"), dossier, salle));
        }
        return rendezVous;
    }
}

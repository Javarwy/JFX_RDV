package repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import database.Database;
import modeles.Dossier;
import modeles.RendezVous;
import modeles.Salle;
import modeles.Etudiant;

public class RendezVousRepository {
    // Ajoute un rendez-vous dans la base de données
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
                ps.setDate(1, Date.valueOf(rendezVous.getDate_rendezvous()));
                ps.setTime(2, Time.valueOf(rendezVous.getHeure_rendez()));
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

    // Modifie un rendez-vous dans la base de données
    public boolean modifier(RendezVous rendezVous) throws SQLException {
        Database db = new Database();
        PreparedStatement ps2 = db.getConnection().prepareStatement(
                "UPDATE rendezvous SET date_rendezvous = ?, heure_rendezvous = ?, ref_salle = ? WHERE id_rendezvous = ?"
        );
        ps2.setDate(1, Date.valueOf(rendezVous.getDate_rendezvous()));
        ps2.setTime(2, Time.valueOf(rendezVous.getHeure_rendez()));
        ps2.setInt(3, rendezVous.getRefSalle().getId_salle());
        ps2.setInt(4, rendezVous.getId_rendezvous());
        ps2.executeUpdate();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "SELECT date_rendezvous, heure_rendezvous, ref_salle FROM rendezvous WHERE id_rendezvous = ?"
        );
        ps.setInt(1, rendezVous.getId_rendezvous());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (rs.getDate("date_rendezvous").toLocalDate().equals(rendezVous.getDate_rendezvous()) && rs.getTime("heure_rendezvous").toLocalTime().equals((rendezVous.getHeure_rendez())) && rs.getInt("ref_salle") == rendezVous.getRefSalle().getId_salle()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Supprime un rendez-vous de la base de données
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

    // Récupère la liste de tous les rendez-vous concernés par l'utilisateur
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
            rendezVous.add(new RendezVous(rs.getInt("id_rendezvous"), rs.getDate("date_rendezvous").toLocalDate(), rs.getTime("heure_rendezvous").toLocalTime(), dossier, salle));
        }
        return rendezVous;
    }

    public ArrayList<LocalTime> creneauxDisponibles(LocalDate localDate, int idUtilisateur) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "SELECT rdv.heure_rendezvous FROM rendezvous as rdv INNER JOIN salle as s ON s.id_salle = rdv.ref_salle WHERE rdv.date_rendezvous = ? AND s.professeur_present = ?;"
        );
        ps.setDate(1, Date.valueOf(localDate));
        ps.setInt(2, idUtilisateur);
        ResultSet rs = ps.executeQuery();
        ArrayList<LocalTime> horaires = new ArrayList<>();
        while (rs.next()) {
            horaires.add(rs.getTime("heure_rendezvous").toLocalTime());
        }
        return horaires;
    }

    public ArrayList<RendezVous> getMesRendezVousFiltres(String dateDebut, String dateFin, String heureDebut, String heureFin, String salleRecherche, String nomEtudiant, String prenomEtudiant, String emailEtudiant, String telephone, int idUtilisateur) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "SELECT rdv.id_rendezvous, rdv.date_rendezvous, rdv.heure_rendezvous, rdv.ref_dossier, rdv.ref_salle, s.nom_salle, s.occupe, s.professeur_present, e.nomEtudiant, e.prenomEtudiant, e.diplome, e.emailEtudiant, e.telephone, d.* FROM rendezvous as rdv INNER JOIN dossier as d ON d.id_dossier = rdv.ref_dossier INNER JOIN etudiant as e ON d.ref_etudiant = e.id_etudiant INNER JOIN salle as s ON s.id_salle = rdv.ref_salle WHERE s.professeur_present = ? AND rdv.date_rendezvous BETWEEN ? AND ? AND rdv.heure_rendezvous BETWEEN ? AND ? AND s.nom_salle LIKE ? AND e.nomEtudiant LIKE ? AND e.prenomEtudiant LIKE ? AND e.emailEtudiant LIKE ? AND e.telephone LIKE ?"
        );
        ps.setInt(1, idUtilisateur);
        if (!dateDebut.isEmpty()){
            ps.setString(2, dateDebut);
        } else {
            ps.setString(2, "1900-01-01");
        }
        if (!dateFin.isEmpty()){
            ps.setString(3, dateFin);
        } else {
            ps.setString(3, "2100-01-01");
        }
        if (!heureDebut.isEmpty()){
            ps.setString(4, heureDebut+":00");
        } else {
            ps.setString(4, "08:00:00");
        }
        if (!heureFin.isEmpty()){
            ps.setString(5, heureFin+":00");
        } else {
            ps.setString(5, "18:00:00");
        }
        if (!salleRecherche.isEmpty()){
            ps.setString(6, "%"+salleRecherche+"%");
        } else {
            ps.setString(6, "%");
        }
        if (!nomEtudiant.isEmpty()){
            ps.setString(7, "%"+nomEtudiant+"%");
        } else {
            ps.setString(7, "%");
        }
        if (!prenomEtudiant.isEmpty()){
            ps.setString(8, "%"+prenomEtudiant+"%");
        } else {
            ps.setString(8, "%");
        }
        if (!emailEtudiant.isEmpty()){
            ps.setString(9, "%"+emailEtudiant+"%");
        } else {
            ps.setString(9, "%");
        }
        if (!telephone.isEmpty()){
            ps.setString(10, "%"+telephone+"%");
        } else {
            ps.setString(10, "%");
        }
        ResultSet rs = ps.executeQuery();
        ArrayList<RendezVous> rendezVous = new ArrayList<>();
        while (rs.next()) {
            Salle salle = new Salle(rs.getInt("ref_salle"), rs.getString("nom_salle"), rs.getBoolean("occupe"), rs.getInt("professeur_present"));
            Etudiant etudiant = new Etudiant(rs.getInt("ref_etudiant"), rs.getString("nomEtudiant"), rs.getString("prenomEtudiant"), rs.getString("diplome"), rs.getString("emailEtudiant"), rs.getString("telephone"));
            Dossier dossier = new Dossier(rs.getInt("ref_dossier"), rs.getString("date"), rs.getString("heure"), rs.getString("filliere"), rs.getString("motivation"), etudiant);
            rendezVous.add(new RendezVous(rs.getInt("id_rendezvous"), rs.getDate("date_rendezvous").toLocalDate(), rs.getTime("heure_rendezvous").toLocalTime(), dossier, salle));
        }
        return rendezVous;
    }
}

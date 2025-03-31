package repository;

import java.sql.*;
import java.util.ArrayList;

import database.Database;
import modeles.Dossier;
import modeles.Etudiant;

public class DossierRepository {
    // Ajoute un dossier d'inscription dans la base de données
    public void ajoutDossier(Dossier dossier) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "INSERT INTO dossier (date, heure, filliere, motivation, ref_etudiant) VALUES (?,?,?,?,?)"
        );
        ps.setDate(1, Date.valueOf(dossier.getDate()));
        ps.setTime(2, Time.valueOf(dossier.getHeure()));
        ps.setString(3, dossier.getFilliere());
        ps.setString(4, dossier.getMotivation());
        ps.setInt(5, dossier.getRefEtudiant().getId_etudiant());
        ps.executeUpdate();
    }

    // Récupère une liste de tous les dossiers (avec informations de l'étudiant)
    public ArrayList<Dossier> getDossiers() throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement("SELECT d.id_dossier, d.date, d.heure, d.filliere, d.motivation, e.id_etudiant, e.prenomEtudiant, e.nomEtudiant, e.diplome, e.emailEtudiant, e.telephone FROM dossier as d INNER JOIN etudiant as e ON d.ref_etudiant = e.id_etudiant;");
        ResultSet rs = ps.executeQuery();
        ArrayList<Dossier> dossiers = new ArrayList<>();
        while(rs.next()) {
            Etudiant etudiant = new Etudiant(rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
            dossiers.add(new Dossier(rs.getInt(1), rs.getDate(2).toLocalDate(), rs.getTime(3).toLocalTime(), rs.getString(4), rs.getString(5), etudiant));
        }
        return dossiers;
    }

    // Récupère une liste des dossiers correspondants aux filtres
    public ArrayList<Dossier> getDossiersFiltres(String dateDebut, String dateFin, String heureDebut, String heureFin, String filiere, String motivation, String nom, String prenom, String diplome, String email, String telephone) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
          "SELECT d.id_dossier, d.date, d.heure, d.filliere, d.motivation, e.id_etudiant, e.prenomEtudiant, e.nomEtudiant, e.diplome, e.emailEtudiant, e.telephone FROM dossier as d INNER JOIN etudiant as e ON d.ref_etudiant = e.id_etudiant WHERE d.date BETWEEN ? AND ? AND d.heure BETWEEN ? AND ? AND d.filliere LIKE ? AND d.motivation LIKE ? AND e.nomEtudiant LIKE ? AND e.prenomEtudiant LIKE ? AND e.diplome LIKE ? AND e.emailEtudiant LIKE ? AND e.telephone LIKE ?"
        );
        if (!dateDebut.isEmpty()){
            ps.setString(1, dateDebut);
        } else {
            ps.setString(1, "1900-01-01");
        }
        if (!dateFin.isEmpty()){
            ps.setString(2, dateFin);
        } else {
            ps.setString(2, "2100-01-01");
        }
        if (!heureDebut.isEmpty()){
            ps.setString(3, heureDebut+":00");
        } else {
            ps.setString(3, "08:00:00");
        }
        if (!heureFin.isEmpty()){
            ps.setString(4, heureFin+":00");
        } else {
            ps.setString(4, "18:00:00");
        }
        if (!filiere.isEmpty()){
            ps.setString(5, "%"+filiere+"%");
        } else {
            ps.setString(5, "%");
        }
        if (!motivation.isEmpty()){
            ps.setString(6, "%"+motivation+"%");
        } else {
            ps.setString(6, "%");
        }
        if (!nom.isEmpty()){
            ps.setString(7, "%"+nom+"%");
        } else {
            ps.setString(7, "%");
        }
        if (!prenom.isEmpty()){
            ps.setString(8, "%"+prenom+"%");
        } else {
            ps.setString(8, "%");
        }
        if (!diplome.isEmpty()){
            ps.setString(9, "%"+diplome+"%");
        } else {
            ps.setString(9, "%");
        }
        if (!email.isEmpty()){
            ps.setString(10, "%"+email+"%");
        } else {
            ps.setString(10, "%");
        }
        if (!telephone.isEmpty()){
            ps.setString(11, "%"+telephone+"%");
        } else {
            ps.setString(11, "%");
        }
        ResultSet rs = ps.executeQuery();
        ArrayList<Dossier> dossiers = new ArrayList<>();
        while(rs.next()) {
            Etudiant etudiant = new Etudiant(rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
            dossiers.add(new Dossier(rs.getInt(1), rs.getDate(2).toLocalDate(), rs.getTime(3).toLocalTime(), rs.getString(4), rs.getString(5), etudiant));
        }
        return dossiers;
    }

    // Récupère la liste de toutes les filières demandées dans les dossiers
    public ArrayList<String> getFilieres() throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
          "SELECT DISTINCT filliere FROM dossier ORDER BY filliere ASC;"
        );
        ResultSet rs = ps.executeQuery();
        ArrayList<String> filieres = new ArrayList<>();
        while(rs.next()){
            filieres.add(rs.getString(1));
        }
        return filieres;
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

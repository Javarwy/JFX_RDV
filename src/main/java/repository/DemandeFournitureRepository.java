package repository;

import database.Database;
import modeles.DemandeFourniture;
import modeles.Fourniture;
import modeles.Utilisateur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DemandeFournitureRepository {
    // Ajoute une demande de fourniture dans la base de données
    public boolean ajout(DemandeFourniture demandeFourniture) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "INSERT INTO demandefourniture (quantite, raison, statut, ref_fourniture, ref_utilisateur) VALUES (?,?,?,?,?)"
        );
        ps.setInt(1, demandeFourniture.getQuantite());
        ps.setString(2, demandeFourniture.getRaison());
        ps.setString(3, demandeFourniture.getStatut());
        ps.setInt(4, demandeFourniture.getRefFourniture().getId_fourniture());
        ps.setInt(5 ,demandeFourniture.getRefUtilisateur().getId_utilisateur());
        ps.executeUpdate();
        PreparedStatement ps2 = db.getConnection().prepareStatement(
          "SELECT COUNT(*) FROM demandefourniture WHERE quantite = ? AND raison = ? AND statut = ? AND ref_fourniture = ? AND ref_utilisateur = ?"
        );
        ps2.setInt(1, demandeFourniture.getQuantite());
        ps2.setString(2, demandeFourniture.getRaison());
        ps2.setString(3, demandeFourniture.getStatut());
        ps2.setInt(4, demandeFourniture.getRefFourniture().getId_fourniture());
        ps2.setInt(5 ,demandeFourniture.getRefUtilisateur().getId_utilisateur());
        ResultSet rs = ps2.executeQuery();
        if (rs.next()) {
            if (rs.getInt(1) == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean annuler(DemandeFourniture demandeFourniture) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "DELETE FROM demandefourniture WHERE id_demandefourniture = ?"
        );
        ps.setInt(1, demandeFourniture.getIdDemandeFourniture());
        ps.executeUpdate();
        PreparedStatement ps2 = db.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM demandefourniture WHERE id_demandefourniture = ?"
        );
        ps2.setInt(1, demandeFourniture.getIdDemandeFourniture());
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

    // Récupère la liste de toutes les demandes de fournitures faites par l'utilisateur
    public ArrayList<DemandeFourniture> getDemandeFournituresByUtilisateur(Utilisateur utilisateur) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement("SELECT df.id_demandefourniture, df.quantite, df.raison, df.statut, df.ref_fourniture, f.libelle, f.description, f.prix, f.fournisseur FROM demandefourniture as df INNER JOIN fourniture as f ON f.id_fourniture = df.ref_fourniture WHERE df.ref_utilisateur = ?;");
        ps.setInt(1, utilisateur.getId_utilisateur());
        ResultSet rs = ps.executeQuery();
        ArrayList<DemandeFourniture> demandes = new ArrayList<>();
        while(rs.next()) {
            Fourniture fourniture = new Fourniture(rs.getInt("ref_fourniture"), rs.getString("libelle"), rs.getString("description"), rs.getDouble("prix"), rs.getString("fournisseur"));
            demandes.add(new DemandeFourniture(rs.getInt("id_demandefourniture"), rs.getInt("quantite"), rs.getString("raison"), rs.getString("statut"), fourniture, utilisateur));
        }
        return demandes;
    }

    public ArrayList<DemandeFourniture> getMesDemandeFournituresFiltres(String libelle, String description, double prixDebut, double prixFin, int quantiteDebut, int quantiteFin, String fournisseur, String raison, String statut, Utilisateur utilisateur) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "SELECT df.id_demandefourniture, df.quantite, df.raison, df.statut, df.ref_fourniture, f.libelle, f.description, f.prix, f.fournisseur FROM demandefourniture as df INNER JOIN fourniture as f ON f.id_fourniture = df.ref_fourniture WHERE df.ref_utilisateur = ? AND f.libelle LIKE ? AND f.description LIKE ? AND f.prix BETWEEN ? AND ? AND df.quantite BETWEEN ? AND ? AND f.fournisseur LIKE ? AND df.raison LIKE ? AND df.statut LIKE ?"
        );
        ps.setInt(1, utilisateur.getId_utilisateur());
        if (!libelle.isEmpty()) {
            ps.setString(2, "%"+libelle+"%");
        } else {
            ps.setString(2, "%");
        }
        if (!description.isEmpty()) {
            ps.setString(3, "%"+description+"%");
        } else {
            ps.setString(3, "%");
        }
        ps.setDouble(4, prixDebut);
        ps.setDouble(5, prixFin);
        ps.setInt(6, quantiteDebut);
        ps.setInt(7, quantiteFin);
        if (!fournisseur.isEmpty()) {
            ps.setString(8, "%"+fournisseur+"%");
        } else {
            ps.setString(8, "%");
        }
        if (!statut.isEmpty()) {
            ps.setString(9, "%"+statut+"%");
        } else {
            ps.setString(9, "%");
        }
        if (!statut.isEmpty()) {
            ps.setString(10, "%"+statut+"%");
        } else {
            ps.setString(10, "%");
        }
        ResultSet rs = ps.executeQuery();
        ArrayList<DemandeFourniture> demandes = new ArrayList<>();
        while (rs.next()) {
            Fourniture fourniture = new Fourniture(rs.getInt("ref_fourniture"), rs.getString("libelle"), rs.getString("description"), rs.getDouble("prix"), rs.getString("fournisseur"));
            demandes.add(new DemandeFourniture(rs.getInt("id_demandefourniture"), rs.getInt("quantite"), rs.getString("raison"), rs.getString("statut"), fourniture, utilisateur));
        }
        return demandes;
    }
}

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
    public boolean ajout(DemandeFourniture demandeFourniture) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "INSERT INTO demandefourniture (quantite, raison, ref_fourniture, ref_utilisateur) VALUES (?,?,?,?)"
        );
        ps.setInt(1, demandeFourniture.getQuantite());
        ps.setString(2, demandeFourniture.getRaison());
        ps.setInt(3, demandeFourniture.getRefFourniture().getId_fourniture());
        ps.setInt(4 ,demandeFourniture.getRefUtilisateur().getId_utilisateur());
        ps.executeUpdate();
        PreparedStatement ps2 = db.getConnection().prepareStatement(
          "SELECT COUNT(*) FROM demandefourniture WHERE quantite = ? AND raison = ? AND ref_fourniture = ? AND ref_utilisateur = ?"
        );
        ps2.setInt(1, demandeFourniture.getQuantite());
        ps2.setString(2, demandeFourniture.getRaison());
        ps2.setInt(3, demandeFourniture.getRefFourniture().getId_fourniture());
        ps2.setInt(4 ,demandeFourniture.getRefUtilisateur().getId_utilisateur());
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

    public ArrayList<DemandeFourniture> getDemandeFournituresByUtilisateur(Utilisateur utilisateur) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement("SELECT df.id_demandefourniture, df.quantite, df.raison, df.ref_fourniture, f.libelle, f.description, f.prix, f.fournisseur FROM demandefourniture as df INNER JOIN fourniture as f ON f.id_fourniture = df.ref_fourniture WHERE df.ref_utilisateur = ?;");
        ps.setInt(1, utilisateur.getId_utilisateur());
        ResultSet rs = ps.executeQuery();
        ArrayList<DemandeFourniture> demandes = new ArrayList<>();
        while(rs.next()) {
            Fourniture fourniture = new Fourniture(rs.getInt("ref_fourniture"), rs.getString("libelle"), rs.getString("description"), rs.getDouble("prix"), rs.getString("fournisseur"));
            demandes.add(new DemandeFourniture(rs.getInt("id_demandefourniture"), rs.getInt("quantite"), rs.getString("raison"), fourniture, utilisateur));
        }
        return demandes;
    }
}

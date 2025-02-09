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
    public ArrayList<DemandeFourniture> getDemandeFournituresByUtilisateur(Utilisateur utilisateur) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement("SELECT df.id_demandefourniture, df.quantite, df.raison, df.ref_fourniture, f.libelle, f.description, f.prix, f.fournisseur FROM demandefourniture as df INNER JOIN fourniture as f ON f.id_fourniture = df.ref_fourniture WHERE df.ref_utilisateur = ?;");
        ps.setInt(1, utilisateur.getId_utilisateur());
        ResultSet rs = ps.executeQuery();
        ArrayList<DemandeFourniture> demandes = new ArrayList<>();
        while(rs.next()) {
            Fourniture fourniture = new Fourniture(rs.getInt("ref_fourniture"), rs.getString("libelle"), rs.getString("description"), rs.getInt("prix"), rs.getString("fournisseur"));
            demandes.add(new DemandeFourniture(rs.getInt("demandefourniture"), rs.getInt("quantite"), rs.getString("raison"), fourniture, utilisateur));
        }
        return demandes;
    }
}

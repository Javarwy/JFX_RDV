package repository;

import database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modeles.Etudiant;


public class EtudiantRepository {

    public void etudiantListe(Etudiant etudiant) throws SQLException {
        Database db = new Database();

        Connection connection = db.getConnection();

        String SQLReqListe = "SELECT nomEtudiant, prenomEtudiant, diplome, EmailEtudiant, telephone FROM etudiant";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLReqListe)) {
            preparedStatement.setString(1, etudiant.getNomEtudiant());
            preparedStatement.setString(2, etudiant.getPrenomEtudiant());
            preparedStatement.setString(3, etudiant.getDiplome());
            preparedStatement.setString(4, etudiant.getEmailEtudiant());
            preparedStatement.setString(5, etudiant.getTelephone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Connexion Impossible", e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

    }

    public ArrayList<Etudiant> getEtudiant() throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement("SELECT id_etudiant, prenomEtudiant, nomEtudiant, diplome, emailEtudiant, telephone FROM etudiant ;");
        ResultSet rs = ps.executeQuery();
        ArrayList<Etudiant> etudiants = new ArrayList<>();
        while(rs.next()) {
            Etudiant etudiant = new Etudiant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));

        }
        return etudiants;
    }



}

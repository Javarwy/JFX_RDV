package repository;

import database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modeles.Etudiant;



public class EtudiantRepository {

    private Connection connection;

    public ArrayList<Etudiant> getEtudiant() throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement("SELECT id_etudiant, prenomEtudiant, nomEtudiant, diplome, emailEtudiant, telephone FROM etudiant ;");
        ResultSet rs = ps.executeQuery();
        ArrayList<Etudiant> etudiants = new ArrayList<>();
        while (rs.next()) {
            Etudiant etudiant = new Etudiant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            etudiants.add(etudiant);
        }
        return etudiants;
    }

    public void createFiche(Etudiant student) throws SQLException {
        Database db = new Database();

        connection = db.getConnection();
        String SQLReq = "INSERT INTO etudiant (nomEtudiant, prenomEtudiant, diplome, emailEtudiant, telephone) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLReq)) {
            preparedStatement.setString(1, student.getNomEtudiant());
            preparedStatement.setString(2, student.getPrenomEtudiant());
            preparedStatement.setString(3, student.getDiplome());
            preparedStatement.setString(4, student.getEmailEtudiant());
            preparedStatement.setString(5, student.getTelephone());
            preparedStatement.executeUpdate();


        } catch (SQLException e){
            throw new RuntimeException("Inscription Impossible", e);
        }finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }

        }



    }
}

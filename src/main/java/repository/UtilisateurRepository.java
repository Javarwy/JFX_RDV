package repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.Database;
import modeles.Utilisateur;

public class UtilisateurRepository {

    private boolean valide;
    private Connection connection;

    public void Hydration(boolean valide) {
        this.valide = valide;
    }

    public void inscription(Utilisateur user) throws SQLException {
        Database db = new Database();

        connection = db.getConnection();

        String SQLReq = "INSERT INTO utilisateur (prenom, nom, email, mdp, role, fourni_com) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLReq)) {
            preparedStatement.setString(1, user.getPrenom());
            preparedStatement.setString(2, user.getNom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getMotDePasse());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Inscription Impossible", e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    public void Connexion(Utilisateur user) throws SQLException {
        Database db = new Database();

        connection = db.getConnection();

        String SQLReqConnexion = "SELECT nom, prenom, email, motDePasse FROM utlisateur";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLReqConnexion)) {
            preparedStatement.setString(1, user.getPrenom());
            preparedStatement.setString(2, user.getNom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getMotDePasse());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Connexion Impossible", e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
}

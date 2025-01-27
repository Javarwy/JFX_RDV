package repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.Database;
import modeles.Utilisateur;

public class UtilisateurRepository {

    private boolean valide;
    private Connection connection;

    public void Hydration(boolean valide) {
        this.valide = valide;
    }

    public boolean inscription(Utilisateur user) throws SQLException {
        Database db = new Database();

        connection = db.getConnection();

        String SQLReq = "INSERT INTO utilisateur (nom, prenom, email, mdp, role, fourni_com) VALUES (?, ?, ?, ?, ?, null)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLReq)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getMotDePasse());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.executeUpdate();
            PreparedStatement reqPrepareSelect = db.getConnection().prepareStatement("SELECT * FROM utilisateur WHERE email = ?"
            );
            reqPrepareSelect.setString(1, user.getEmail());
            ResultSet resultatRequete = reqPrepareSelect.executeQuery();
            if (resultatRequete.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Inscription Impossible", e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    public boolean modification(Utilisateur utilisateur) throws SQLException {
        Database db = new Database();
        PreparedStatement requetePrepareModif = db.getConnection().prepareStatement(
                "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, mdp = ? WHERE id_utilisateur = ?"
        );
        requetePrepareModif.setString(1, utilisateur.getNom());
        requetePrepareModif.setString(2, utilisateur.getPrenom());
        requetePrepareModif.setString(3, utilisateur.getEmail());
        requetePrepareModif.setString(4, utilisateur.getMotDePasse());
        requetePrepareModif.setInt(5, utilisateur.getId_utilisateur());
        requetePrepareModif.executeUpdate();
        PreparedStatement reqPrepareSelect = db.getConnection().prepareStatement("SELECT * FROM utilisateur WHERE email = ?"
        );
        reqPrepareSelect.setString(1, utilisateur.getEmail());
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.next()){
            return true;
        } else {
                return false;
        }
    }

    public void Connexion(Utilisateur user) throws SQLException {
        Database db = new Database();

        connection = db.getConnection();

        String SQLReqConnexion = "SELECT nom, prenom, email, motDePasse FROM utilisateur";

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

    public Utilisateur getUtilisateurByEmail(String email) throws SQLException {
        Database db = new Database();
        PreparedStatement reqPrepareSelect = db.getConnection().prepareStatement("SELECT * FROM utilisateur WHERE email = ?"
        );
        reqPrepareSelect.setString(1, email);
        ResultSet resultSet = reqPrepareSelect.executeQuery();
        if (resultSet.next()) {
            Utilisateur utilisateur = new Utilisateur(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7));
            return utilisateur;
        } else {
            return null;
        }
    }

    public Utilisateur getUtilisateurById(int id) throws SQLException {
        Database db = new Database();
        PreparedStatement reqPrepareSelect = db.getConnection().prepareStatement("SELECT * FROM utilisateur WHERE id_utilisateur = ?"
        );
        reqPrepareSelect.setInt(1, id);
        ResultSet resultSet = reqPrepareSelect.executeQuery();
        if (resultSet.next()) {
            Utilisateur utilisateur = new Utilisateur(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7));
            return utilisateur;
        } else {
            return null;
        }
    }
}

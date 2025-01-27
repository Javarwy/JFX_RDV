package repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.Database;
import modeles.Fourniture;

public class FournitureRepository {
    
    public void getListFourniture(Fourniture fourniture) throws SQLException, IOException {
        Database db = new Database();
        Connection connection = db.getConnection();
        String SQLReqFurn = "SELECT id_fourniture, libelle, description, prix, fournisseur FROM fourniture";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLReqFurn)) {
            preparedStatement.setInt(1, fourniture.getId_fourniture());
            preparedStatement.setString(2, fourniture.getLibelle());
            preparedStatement.setString(3, fourniture.getDescription());
            preparedStatement.setInt(4, fourniture.getPrix());
            preparedStatement.setString(5, fourniture.getFournisseur());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Inscription Impossible", e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }


}}

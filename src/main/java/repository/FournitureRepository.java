package repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import database.Database;
import modeles.Fourniture;

public class FournitureRepository {

    Database db = new Database();
    Connection connection = db.getConnection();

    public ArrayList<Fourniture> getListFourniture() throws SQLException, IOException {
        String SQLReqFurn = "SELECT id_fourniture, libelle, description, prix, fournisseur FROM fourniture";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLReqFurn)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Fourniture> fournitures = new ArrayList<>();
            while (resultSet.next()) {
                Fourniture fourniture = new Fourniture(
                        resultSet.getInt("id_fourniture"),
                        resultSet.getString("libelle"),
                        resultSet.getString("description"),
                        resultSet.getInt("prix"),
                        resultSet.getString("fournisseur")
                );
                fournitures.add(fourniture);
            }
            return fournitures;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des fournitures", e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    public void addFourniture(Fourniture fourniture) throws SQLException, IOException {
        String SQLReqFurnAdd = "INSERT INTO fourniture (id_fourniture, libelle, description, prix, fournisseur) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLReqFurnAdd)) {
            preparedStatement.setInt(1, fourniture.getId_fourniture());
            preparedStatement.setString(2, fourniture.getLibelle());
            preparedStatement.setString(3, fourniture.getDescription());
            preparedStatement.setInt(4, fourniture.getPrix());
            preparedStatement.setString(5, fourniture.getFournisseur());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la fourniture", e);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
        public void deleteFourniture(Fourniture fourniture) throws SQLException, IOException {
            String SQLReqFurnDelete = "DELETE FROM fourniture WHERE id_fourniture = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLReqFurnDelete)) {
                preparedStatement.setInt(1, fourniture.getId_fourniture());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la suppression de la fourniture", e);
            } finally {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            }

        }

            public void updateFourniture(Fourniture fourniture) throws SQLException, IOException {
                String SQLReqFurnUpdate = "UPDATE fourniture SET libelle = ?, description = ?, prix = ?, fournisseur = ? WHERE id_fourniture = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(SQLReqFurnUpdate)) {
                    preparedStatement.setString(1, fourniture.getLibelle());
                    preparedStatement.setString(2, fourniture.getDescription());
                    preparedStatement.setInt(3, fourniture.getPrix());
                    preparedStatement.setString(4, fourniture.getFournisseur());
                    preparedStatement.setInt(5, fourniture.getId_fourniture());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException("Erreur lors de la mise à jour de la fourniture", e);
                } finally {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                }
            }
    }

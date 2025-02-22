package repository;

import database.Database;
import modeles.Logs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LogsRepository {
    public void ajout(Logs logs) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "INSERT INTO logs (action, datetime, ref_utilisateur) VALUES (?,?,?);"
        );
        ps.setString(1, logs.getAction());
        ps.setTimestamp(2, Timestamp.valueOf(logs.getDateTime()));
        ps.setInt(3, logs.getRefUtilisateur().getId_utilisateur());
        ps.executeUpdate();
    }
}

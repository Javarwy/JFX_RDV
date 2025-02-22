package repository;

import database.Database;
import modeles.Logs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LogsRepository {
    public boolean ajout(Logs logs) throws SQLException {
        Database db = new Database();
        PreparedStatement ps = db.getConnection().prepareStatement(
                "INSERT INTO logs (action, datetime, ref_utilisateur) VALUES (?,?,?);"
        );
        ps.setString(1, logs.getAction());
        ps.setTimestamp(2, Timestamp.valueOf(logs.getDateTime()));
        ps.setInt(3, logs.getRefUtilisateur().getId_utilisateur());
        ps.executeUpdate();
        PreparedStatement ps2 = db.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM logs WHERE action = ? AND datetime = ? AND ref_utilisateur = ?;"
        );
        ps2.setString(1, logs.getAction());
        ps2.setTimestamp(2, Timestamp.valueOf(logs.getDateTime()));
        ps2.setInt(3, logs.getRefUtilisateur().getId_utilisateur());
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
}

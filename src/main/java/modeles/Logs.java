package modeles;

import java.time.LocalDateTime;

public class Logs {
    private int idLog;
    private String action;
    private LocalDateTime dateTime;
    private Utilisateur refUtilisateur;

    public Logs(String action, LocalDateTime dateTime, Utilisateur refUtilisateur) {
        this.action = action;
        this.dateTime = dateTime;
        this.refUtilisateur = refUtilisateur;
    }

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Utilisateur getRefUtilisateur() {
        return refUtilisateur;
    }

    public void setRefUtilisateur(Utilisateur refUtilisateur) {
        this.refUtilisateur = refUtilisateur;
    }
}

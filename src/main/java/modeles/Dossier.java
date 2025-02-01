package modeles;

public class Dossier {

    public int id_dossier;
    public String date;
    public String heure;
    public String filliere;
    public String motivation;
    public Etudiant refEtudiant;

    public Dossier (int id_dossier, String date, String heure, String filliere, String motivation, Etudiant refEtudiant) {
        this.id_dossier = id_dossier;
        this.date = date;
        this.heure = heure;
        this.filliere = filliere;
        this.motivation = motivation;
        this.refEtudiant = refEtudiant;
    }

    public int getId_dossier() {
        return id_dossier;
    }

    public void setId_dossier(int id_dossier) {
        this.id_dossier = id_dossier;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getFilliere() {
        return filliere;
    }

    public void setFilliere(String filliere) {
        this.filliere = filliere;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public Etudiant getRefEtudiant() {
        return refEtudiant;
    }

    public void setRefEtudiant(Etudiant refEtudiant) {
        this.refEtudiant = refEtudiant;
    }
}






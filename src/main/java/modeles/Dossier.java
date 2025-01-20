package modeles;

public class Dossier {

    public int id_dossier;
    public String date;
    public String heure;
    public String filliere;
    public String motivation;
    public int ref_rdv;

    public Dossier (int id_dossier, String date, String heure, String filliere, String motivation, int ref_rdv) {
        this.id_dossier = id_dossier;
        this.date = date;
        this.heure = heure;
        this.filliere = filliere;
        this.motivation = motivation;
        this.ref_rdv = ref_rdv;
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

    public int getRef_rdv() {
        return ref_rdv;
    }

    public void setRef_rdv(int ref_rdv) {
        this.ref_rdv = ref_rdv;
    }
}






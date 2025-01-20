package modeles;

public class Salle {

    public int id_salle;
    public String nom_salle;
    public Boolean occupe;
    public String professeur_absent;
    public String rdv_ref;

    public Salle (int id_salle, String nom_salle, Boolean occupe, String professeur_absent, String rdv_ref) {
        this.id_salle = id_salle;
        this.nom_salle = nom_salle;
        this.occupe = occupe;
        this.professeur_absent = professeur_absent;
        this.rdv_ref = rdv_ref;
    }
    public int getId_salle() {
        return id_salle;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }

    public String getNom_salle() {
        return nom_salle;
    }

    public void setNom_salle(String nom_salle) {
        this.nom_salle = nom_salle;
    }

    public Boolean getOccupe() {
        return occupe;
    }

    public void setOccupe(Boolean occupe) {
        this.occupe = occupe;
    }

    public String getProfesseur_absent() {
        return professeur_absent;
    }

    public void setProfesseur_absent(String professeur_absent) {
        this.professeur_absent = professeur_absent;
    }

    public String getRdv_ref() {
        return rdv_ref;
    }

    public void setRdv_ref(String rdv_ref) {
        this.rdv_ref = rdv_ref;
    }
}






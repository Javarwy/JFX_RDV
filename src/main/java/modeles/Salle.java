package modeles;

public class Salle {

    public int id_salle;
    public String nom_salle;
    public Boolean occupe;
    public int professeur_absent;

    public Salle (int id_salle, String nom_salle, Boolean occupe, int professeur_absent) {
        this.id_salle = id_salle;
        this.nom_salle = nom_salle;
        this.occupe = occupe;
        this.professeur_absent = professeur_absent;
    }

    public Salle (int id_salle, String nom_salle) {
        this.id_salle = id_salle;
        this.nom_salle = nom_salle;
    }

    @Override
    public String toString() {
        return nom_salle;
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

    public int getProfesseur_absent() {
        return professeur_absent;
    }

    public void setProfesseur_absent(int professeur_absent) {
        this.professeur_absent = professeur_absent;
    }

}






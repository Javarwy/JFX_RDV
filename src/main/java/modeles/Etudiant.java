package modeles;

public class Etudiant {

    public int id_etudiant;
    public String prenomEtudiant;
    public String nomEtudiant;
    public String diplome;
    public String emailEtudiant;
    public String telephone;

 public Etudiant (int id_etudiant, String prenomEtudiant, String nomEtudiant, String diplome, String emailEtudiant, String telephone) {
        this.id_etudiant = id_etudiant;
        this.prenomEtudiant = prenomEtudiant;
        this.nomEtudiant = nomEtudiant;
        this.diplome = diplome;
        this.emailEtudiant = emailEtudiant;
        this.telephone = telephone;
    }

    public Etudiant ( String prenomEtudiant, String nomEtudiant, String diplome, String emailEtudiant, String telephone) {
        this.prenomEtudiant = prenomEtudiant;
        this.nomEtudiant = nomEtudiant;
        this.diplome = diplome;
        this.emailEtudiant = emailEtudiant;
        this.telephone = telephone;
    }

    public int getId_etudiant() {
        return id_etudiant;
    }

    public void setId_etudiant(int id_etudiant) {
        this.id_etudiant = id_etudiant;
    }

    public String getPrenomEtudiant() {
        return prenomEtudiant;
    }

    public void setPrenomEtudiant(String prenomEtudiant) {
        this.prenomEtudiant = prenomEtudiant;
    }

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getEmailEtudiant() {
        return emailEtudiant;
    }

    public void setEmailEtudiant(String emailEtudiant) {
        this.emailEtudiant = emailEtudiant;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}


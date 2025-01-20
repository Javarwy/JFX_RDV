package modeles;

public class Utilisateur {
    private int id_utilisateur;
    private String prenom;
    private String nom;
    private String email;
    private String mdp;
    private String role;
    private String fourni_com;

    public Utilisateur(int id_utilisateur, String prenom, String nom, String email, String mdp, String role, String fourni_com) {

        this.id_utilisateur = id_utilisateur;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.mdp = mdp;
        this.role = role;
        this.fourni_com = fourni_com;

    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return mdp;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String mdp) {
        this.mdp = mdp;
    }

    public String getRole() {
        return role;
    }

    public String getFourniCom() {
        return fourni_com;
    }

    public String setRole(String role) {
        this.role = role;
        return role;
    }

    public String setFourniCom(String fourni_com) {
        this.fourni_com = fourni_com;
        return fourni_com;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }
}


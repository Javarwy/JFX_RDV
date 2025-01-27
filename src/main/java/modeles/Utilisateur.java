package modeles;

public class Utilisateur {
    private int id_utilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private String role;
    private int fourni_com;

    public Utilisateur(int id_utilisateur, String nom, String prenom, String email, String mdp, String role, int fourni_com) {

        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.role = role;
        this.fourni_com = fourni_com;

    }

    public Utilisateur(String nom, String prenom, String email, String mdp, String role) {

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.role = role;

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

    public int getFourniCom() {
        return fourni_com;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setFourniCom(int fourni_com) {
        this.fourni_com = fourni_com;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }
}


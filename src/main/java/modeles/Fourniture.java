package modeles;

public class Fourniture {

    public int id_fourniture;
    public String libelle;
    public String description;
    public double prix;
    public String fournisseur;


    public Fourniture(int id_fourniture, String libelle, String description, double prix, String fournisseur) {
        this.id_fourniture = id_fourniture;
        this.libelle = libelle;
        this.description = description;
        this.prix = prix;
        this.fournisseur = fournisseur;
    }

    public Fourniture(String libelle, String description, double prix, String fournisseur) {
        this.libelle = libelle;
        this.description = description;
        this.prix = prix;
        this.fournisseur = fournisseur;
    }

    public int getId_fourniture() {
        return id_fourniture;
    }

    public void setId_fourniture(int id_fourniture) {
        this.id_fourniture = id_fourniture;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }
}


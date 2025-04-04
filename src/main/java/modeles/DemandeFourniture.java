package modeles;

public class DemandeFourniture {

    private int idDemandeFourniture;
    private int quantite;
    private String raison;
    private String statut;
    private Fourniture refFourniture;
    private Utilisateur refUtilisateur;

    public DemandeFourniture(int idDemandeFourniture, int quantite, String raison, String statut, Fourniture refFourniture, Utilisateur refUtilisateur){
        this.idDemandeFourniture = idDemandeFourniture;
        this.quantite = quantite;
        this.raison = raison;
        this.statut = statut;
        this.refFourniture = refFourniture;
        this.refUtilisateur = refUtilisateur;
    }

    public DemandeFourniture (int quantite, String raison, String statut, Fourniture refFourniture, Utilisateur refUtilisateur){
        this.quantite = quantite;
        this.raison = raison;
        this.statut = statut;
        this.refFourniture = refFourniture;
        this.refUtilisateur = refUtilisateur;
    }

    public int getIdDemandeFourniture() {return idDemandeFourniture; }
    public int getQuantite() {return quantite; }
    public String getRaison() {return raison; }
    public String getStatut() {return statut; }
    public Fourniture getRefFourniture() {return refFourniture; }
    public Utilisateur getRefUtilisateur() {return refUtilisateur; }

    public void setIdDemandeFourniture(int idDemandeFourniture) {this.idDemandeFourniture = idDemandeFourniture;}
    public void setQuantite(int quantite) {this.quantite = quantite;}
    public void setRaison(String raison) {this.raison = raison;}
    public void setStatut(String statut) {this.statut = statut;}
    public void setRefFourniture(Fourniture refFourniture) {this.refFourniture = refFourniture;}
    public void setRefUtilisateur(Utilisateur refUtilisateur) {this.refUtilisateur = refUtilisateur;}

}

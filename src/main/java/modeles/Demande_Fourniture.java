package modeles;

import java.time.LocalDateTime;
public class Demande_Fourniture {
    private int id;
    private int professeurId;
    private int fournitureId;
    private int quantite;
    private String raison;
    private LocalDateTime dateDemande;

    public Demande_Fourniture(int professeurId, int professeurId, int fournitureId, int quantite, String raison){
        this.professeurId = professeurId;
        this.fournitureId = fournitureId;
        this.quantite = quantite;
        this.raison = raison;
        this.dateDemande = LocalDateTime.now();
    }

    public int getId() {return id; }
    public int getFournitureId() {return fournitureId; }
    public int getProfesseurId() {return professeurId; }
    public int getQuantite() {return quantite; }
    public String getRaison() {return raison; }
    public LocalDateTime getDateDemande() {return dateDemande; }

    public void setId(int id) {this.id = id;}
    public void setProfesseurId(int professeurId) {this.professeurId = professeurId;}
    public void setFournitureId(int fournitureId) {this.fournitureId = fournitureId;}
    public void setQuantite(int quantite) {this.quantite = quantite;}
    public void setRaison(String raison) {this.raison = raison;}
    public void setDateDemande(LocalDateTime dateDemande)
    {this.dateDemande = dateDemande;}
}

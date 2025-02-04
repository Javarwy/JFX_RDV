package modeles;

public class RendezVous {

    public int id_rendezvous;
    public String date_rendezvous;
    public String heure_rendez;
    public Dossier refDossier;
    public Salle refSalle;

    public RendezVous (int id_rendezvous, String date_rendezvous, String heure_rendez) {

        this.id_rendezvous = id_rendezvous;
        this.date_rendezvous = date_rendezvous;
        this.heure_rendez = heure_rendez;
    }
    public RendezVous (String date_rendezvous, String heure_rendez, Dossier refDossier, Salle refSalle) {

        this.date_rendezvous = date_rendezvous;
        this.heure_rendez = heure_rendez;
        this.refDossier = refDossier;
        this.refSalle = refSalle;
    }

    public int getId_rendezvous() {
        return id_rendezvous;
    }

    public void setId_rendezvous(int id_rendezvous) {
        this.id_rendezvous = id_rendezvous;
    }

    public String getDate_rendezvous() {
        return date_rendezvous;
    }

    public void setDate_rendezvous(String date_rendezvous) {
        this.date_rendezvous = date_rendezvous;
    }

    public String getHeure_rendez() {
        return heure_rendez;
    }

    public void setHeure_rendez(String heure_rendez) {
        this.heure_rendez = heure_rendez;
    }

    public Dossier getRefDossier() {
        return refDossier;
    }

    public void setRefDossier(Dossier refDossier) {
        this.refDossier = refDossier;
    }

    public Salle getRefSalle() {
        return refSalle;
    }

    public void setRefSalle(Salle refSalle) {
        this.refSalle = refSalle;
    }
}

package modeles;

public class RendezVous {

    public int id_rendezvous;
    public String date_rendezvous;
    public String heure_rendez;

    public RendezVous (int id_rendezvous, String date_rendezvous, String heure_rendez) {

        this.id_rendezvous = id_rendezvous;
        this.date_rendezvous = date_rendezvous;
        this.heure_rendez = heure_rendez;
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
}

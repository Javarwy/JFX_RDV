package appli.professeur;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import modeles.RendezVous;
import modeles.Salle;
import modeles.UtilisateurConnecte;
import repository.RendezVousRepository;
import repository.SalleRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifierRdvController implements Initializable {
    @FXML
    private Label idRdv;
    @FXML
    private DatePicker date;
    @FXML
    private Spinner<Integer> heure;
    @FXML
    private Spinner<Integer> minute;
    @FXML
    private ComboBox<Salle> salle;
    @FXML
    private Label erreur;

    private RendezVous rdvSel;

    public ModifierRdvController(RendezVous rdvSel) {
        this.rdvSel = rdvSel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Modifier rendez-vous");
        this.idRdv.setText("Id. rendez-vous : " + this.rdvSel.getId_rendezvous() + "\n(dossier de " + this.rdvSel.getRefDossier().getRefEtudiant().getNomEtudiant() + " " + this.rdvSel.getRefDossier().getRefEtudiant().getPrenomEtudiant() + ")");
        this.date.setValue(LocalDate.parse(this.rdvSel.getDate_rendezvous()));
        this.heure.getValueFactory().setValue(Integer.parseInt(this.rdvSel.getHeure_rendez().split(":")[0]));
        this.minute.getValueFactory().setValue(Integer.parseInt(this.rdvSel.getHeure_rendez().split(":")[1]));
        this.salle.setValue(this.rdvSel.getRefSalle());
        this.salle.getItems().add(this.rdvSel.getRefSalle());
        ArrayList<Salle> salles;
        try {
            salles = new SalleRepository().getSallesLibres();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Salle salle : salles) {
            this.salle.getItems().add(salle);
        }
    }

    @FXML
    protected void confirmer() throws IOException, SQLException {
        if (this.date.getValue() == null || this.heure.getValue() == null || this.minute.getValue() == null || this.salle.getValue() == null) {
            this.erreur.setText("Veuillez mettre une date, une heure et une salle.");
            this.erreur.setVisible(true);
        } else {
            String date = this.date.getValue().toString();
            int heure = this.heure.getValue();
            int minute = this.minute.getValue();
            Salle salle = this.salle.getValue();
            String time = String.format("%02d:%02d:%02d", heure, minute, 0);
            RendezVousRepository rendezVousRepository = new RendezVousRepository();
            boolean check = false;
            check = rendezVousRepository.modifier(new RendezVous(this.rdvSel.getId_rendezvous(), date, time, this.rdvSel.getRefDossier(), this.salle.getValue()));
            if (check == true){
                if (this.rdvSel.getRefSalle().getId_salle() != salle.getId_salle()) {
                    SalleRepository salleRepository = new SalleRepository();
                    salleRepository.reserver(new Salle(salle.getId_salle(), salle.getNom_salle(), true, UtilisateurConnecte.getInstance().getId_utilisateur()));
                    salleRepository.liberer(this.rdvSel.getRefSalle());
                }
                StartApplication.changeScene("professeur/rdvProfesseurView.fxml");
            } else {
                this.erreur.setText("Le rendez-vous n'a pas eu de modifications.");
                this.erreur.setVisible(true);
            }
        }
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/rdvProfesseurView.fxml");
    }
}

package appli.professeur;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import modeles.Logs;
import modeles.RendezVous;
import modeles.Salle;
import modeles.UtilisateurConnecte;
import repository.LogsRepository;
import repository.RendezVousRepository;
import repository.SalleRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    // Données de la colonne sélectionnée depuis le TableView de la page de rendez-vous
    private RendezVous rdvSel;

    public ModifierRdvController(RendezVous rdvSel) {
        this.rdvSel = rdvSel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Modifier rendez-vous");
        this.idRdv.setText("Id. rendez-vous : " + this.rdvSel.getId_rendezvous() + "\n(dossier de " + this.rdvSel.getRefDossier().getRefEtudiant().getNomEtudiant() + " " + this.rdvSel.getRefDossier().getRefEtudiant().getPrenomEtudiant() + ")");
        this.date.setValue(this.rdvSel.getDate_rendezvous());
        this.heure.getValueFactory().setValue(this.rdvSel.getHeure_rendez().getHour());
        this.minute.getValueFactory().setValue(this.rdvSel.getHeure_rendez().getMinute());
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
        // Si les champs n'ont pas tous été remplis, afficher l'erreur suivante
        if (this.date.getValue() == null || this.heure.getValue() == null || this.minute.getValue() == null || this.salle.getValue() == null) {
            this.erreur.setText("Veuillez mettre une date, une heure et une salle.");
            this.erreur.setVisible(true);
        // Si aucun des champs n'a été modifié, afficher l'erreur suivante
        } else if ((this.date.getValue()).equals(this.rdvSel.getDate_rendezvous()) && (LocalTime.of(this.heure.getValue(), this.minute.getValue(), 0)).equals(this.rdvSel.getHeure_rendez()) && salle.getValue().equals(this.rdvSel.getRefSalle())) {
            this.erreur.setText("Veuillez modifier au moins une information.");
            this.erreur.setVisible(true);
        } else {
            LocalDate date = this.date.getValue();
            int heure = this.heure.getValue();
            int minute = this.minute.getValue();
            // Mélange heure et minute et le met en format HH:MM:SS
            LocalTime time = LocalTime.of(heure, minute, 0);
            Salle salle = this.salle.getValue();
            RendezVousRepository rendezVousRepository = new RendezVousRepository();
            boolean check = true;
            // Liste comportant les horaires des rendez-vous de l'utilisateur de la date sélectionnée
            ArrayList<LocalTime> horaires;
            horaires = rendezVousRepository.creneauxDisponibles(date, UtilisateurConnecte.getInstance().getId_utilisateur());
            // Retire l'horaire du rendez-vous sélectionné de la liste
            horaires.remove(this.rdvSel.getHeure_rendez());
            boolean matinOccupe = false;
            boolean apremOccupe = false;
            for (LocalTime horaire : horaires){
                // Vérifie si un rendez-vous est déjà pris entre 8:00 et 13:00
                if ((horaire.isAfter(LocalTime.of(8, 0)) && horaire.isBefore(LocalTime.of(13, 1)))) {
                    matinOccupe = true;
                }
                // Vérifie si un rendez-vous est déjà pris entre 13:00 et 18:00
                if ((horaire.isAfter(LocalTime.of(13, 0))) && horaire.isBefore(LocalTime.of(18, 1))) {
                    apremOccupe = true;
                }
            }
            // Exclut l'horaire du rendez-vous sélectionné, celui-ci étant modifié
            if (!time.equals(this.rdvSel.getHeure_rendez())) {
                // Si l'un des rendez-vous du jour se tient déjà entre 8h et 13h ou entre 13h et 18h, empêcher le processus de modification
                if ((time.isAfter(LocalTime.of(7, 59)) && time.isBefore(LocalTime.of(13, 1)))) {
                    if (matinOccupe && apremOccupe) {
                        check = false;
                        this.erreur.setText("Vos demi-journées sont déjà occupées à cette date. Veuillez choisir une autre date.");
                        this.erreur.setVisible(true);
                    } else if (matinOccupe) {
                        check = false;
                        this.erreur.setText("Votre demi-journée 8:00-12:30 est déjà occupée à cette date. Veuillez choisir entre 14:00 et 18:00.");
                        this.erreur.setVisible(true);
                    }
                } else if ((time.isAfter(LocalTime.of(12, 59)) && time.isBefore(LocalTime.of(18, 1)))){
                    if (apremOccupe && matinOccupe) {
                        check = false;
                        this.erreur.setText("Vos demi-journées sont déjà occupées à cette date. Veuillez choisir une autre date.");
                        this.erreur.setVisible(true);
                    } else if (apremOccupe) {
                        check = false;
                        this.erreur.setText("Votre demi-journée 14:00-18:00 est déjà occupée à cette date. Veuillez choisir entre 08:00 et 12:30.");
                        this.erreur.setVisible(true);
                    }
                }
            }
            // Si le processus d'horaire est validé
            if (check == true){
                check = rendezVousRepository.modifier(new RendezVous(this.rdvSel.getId_rendezvous(), date, time, this.rdvSel.getRefDossier(), this.salle.getValue()));
                if (check == true){
                    if (this.rdvSel.getRefSalle().getId_salle() != salle.getId_salle()) {
                        SalleRepository salleRepository = new SalleRepository();
                        salleRepository.reserver(new Salle(salle.getId_salle(), salle.getNom_salle(), true, UtilisateurConnecte.getInstance().getId_utilisateur()));
                        new LogsRepository().ajout(new Logs("Modification de la salle id. "+salle.getId_salle()+" (occupe = 1 | professeur_present = "+UtilisateurConnecte.getInstance().getId_utilisateur()+")", LocalDateTime.now(), UtilisateurConnecte.getInstance()));
                        salleRepository.liberer(this.rdvSel.getRefSalle());
                        new LogsRepository().ajout(new Logs("Modification de la salle id. "+this.rdvSel.getRefSalle().getId_salle()+" (occupe = 0 | professeur_present = null)", LocalDateTime.now(), UtilisateurConnecte.getInstance()));
                    }
                    new LogsRepository().ajout(new Logs("Modification du rendez-vous id. "+this.rdvSel.getId_rendezvous(), LocalDateTime.now(), UtilisateurConnecte.getInstance()));
                    StartApplication.changeScene("professeur/rdvProfesseurView.fxml");
                } else {
                    this.erreur.setText("Erreur lors de la modification. Si le problème persiste, veuillez contacter le support.");
                    this.erreur.setVisible(true);
                }
            }
        }
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/rdvProfesseurView.fxml");
    }
}

package appli.professeur;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import modeles.*;
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

public class PrendreRdvProfesseurController implements Initializable {

    @FXML
    private TableView<Salle> tableauSalle;
    @FXML
    private Button confirmerRdv;
    @FXML
    private Label idDossier;
    @FXML
    private Label erreur;
    @FXML
    private VBox form;
    @FXML
    private DatePicker date;
    @FXML
    private Spinner<Integer> heure;
    @FXML
    private Spinner<Integer> minute;

    // Données de la colonne sélectionnée depuis le TableView de la page des dossiers d'inscription
    private Dossier dossierSel;

    // Données de la colonne sélectionnée depuis le TableView de cette page
    private Salle salleSel;

    public PrendreRdvProfesseurController(Dossier dossierSel){
        this.dossierSel = dossierSel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Prise de rendez-vous");
        this.idDossier.setText("Rendez-vous pour dossier n°" + this.dossierSel.getId_dossier() + " (" + this.dossierSel.getRefEtudiant().getNomEtudiant() + " " + this.dossierSel.getRefEtudiant().getPrenomEtudiant() + ")");
        String[][] colonnes = {
                {"Id. salle", "id_salle"},
                {"Nom", "nom_salle"}
        };
        // Parcours de l'ensemble des colonnes
        for (int i = 0; i < colonnes.length; i++) {
            TableColumn<Salle,String> maColonne = new TableColumn<>(colonnes[i][0]);
            maColonne.setCellValueFactory(new PropertyValueFactory<Salle,String>(colonnes[i][1]));
            tableauSalle.getColumns().add(maColonne);
        }
        this.tableauSalle.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        SalleRepository salleRepository = new SalleRepository();
        ArrayList<Salle> salles;
        try {
            salles = salleRepository.getSallesLibres();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Ajoute les données de la liste dans les colonnes du TableView
        ObservableList<Salle> observableList = tableauSalle.getItems();
        observableList.setAll(salles);
        // Si aucune salle n'est disponible, afficher ce message sur le tableau
        if (salles.isEmpty()) {
            this.tableauSalle.setPlaceholder(new Label("Aucune salle disponible. Veuillez prendre rendez-vous ultérieurement."));
        }
        this.date.setValue(LocalDate.now());
    }
    @FXML
    protected void onSalleSelection(MouseEvent event) throws IOException {
        // Un clic gauche = Enregistre les données de la colonne sélectionnée et active le formulaire
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            confirmerRdv.setDisable(false);
            TablePosition cell = tableauSalle.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            this.salleSel = tableauSalle.getItems().get(indexLigne);
            this.form.setVisible(true);
            this.form.setManaged(true);
        }
    }
    @FXML
    protected void confirmerRdv() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer rendez-vous");
        alert.setHeaderText("Souhaitez-vous confirmer votre rendez-vous dans la salle n°" + this.salleSel.getId_salle() + " (" + this.salleSel.getNom_salle() + ") ?");
        alert.showAndWait().ifPresent(reponse -> {
            // Si OK cliqué, confirme (ajoute) le rendez-vous
            if (reponse == ButtonType.OK){
                // Si les champs n'ont pas tous été remplis, afficher l'erreur suivante
                if (this.date.getValue() == null || this.heure.getValue() == null || this.minute.getValue() == null){
                    this.erreur.setText("Veuillez mettre une date et une heure.");
                    this.erreur.setVisible(true);
                } else {
                    LocalDate date = this.date.getValue();
                    int heure = this.heure.getValue();
                    int minute = this.minute.getValue();
                    // Mélange heure et minute et le met en format HH:MM:SS
                    LocalTime time = LocalTime.of(heure, minute, 0);
                    RendezVousRepository rendezVousRepository = new RendezVousRepository();
                    boolean check = true;
                    // Liste comportant les horaires des rendez-vous de l'utilisateur de la date sélectionnée
                    ArrayList<LocalTime> horaires;
                    try {
                        horaires = rendezVousRepository.creneauxDisponibles(date, UtilisateurConnecte.getInstance().getId_utilisateur());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    boolean matinOccupe = false;
                    boolean apremOccupe = false;
                    for (LocalTime horaire : horaires) {
                        // Vérifie si un rendez-vous est déjà pris entre 8:00 et 13:00
                        if ((horaire.isAfter(LocalTime.of(8, 0)) && horaire.isBefore(LocalTime.of(13, 1)))) {
                            matinOccupe = true;
                        }
                        // Vérifie si un rendez-vous est déjà pris entre 13:00 et 18:00
                        if ((horaire.isAfter(LocalTime.of(13, 0))) && horaire.isBefore(LocalTime.of(18, 1))) {
                            apremOccupe = true;
                        }
                        System.out.println(horaire + "(" + matinOccupe + " | " + apremOccupe + ")");
                    }
                    // Si l'un des rendez-vous du jour se tient déjà entre 8h et 13h ou entre 13h et 18h, empêcher le processus de création
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
                    // Si le processus d'horaire est validé
                    if (check == true){
                        try {
                            check = rendezVousRepository.ajout(new RendezVous(date, time, this.dossierSel, this.salleSel));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        if (check == true){
                            SalleRepository salleRepository = new SalleRepository();
                            try {
                                check = salleRepository.reserver(new Salle(this.salleSel.getId_salle(), this.salleSel.getNom_salle(), true, UtilisateurConnecte.getInstance().getId_utilisateur()));
                                new LogsRepository().ajout(new Logs("Modification de la salle id. "+this.salleSel.getId_salle()+" (occupe = 1 | professeur_present = "+UtilisateurConnecte.getInstance().getId_utilisateur()+")", LocalDateTime.now(), UtilisateurConnecte.getInstance()));
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            if (check == true){
                                try {
                                    new LogsRepository().ajout(new Logs("Création d'un rendez-vous pour le dossier id. "+this.dossierSel.getId_dossier(), LocalDateTime.now(), UtilisateurConnecte.getInstance()));
                                    StartApplication.changeScene("professeur/menuProfesseurView.fxml");
                                } catch (IOException | SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                this.erreur.setText("Une erreur est survenue.");
                                this.erreur.setVisible(true);
                            }
                        } else {
                            this.erreur.setText("Un rendez-vous est déjà pris avec ce dossier.");
                            this.erreur.setVisible(true);
                        }
                    }
                }
            } else if (reponse == ButtonType.CANCEL){
                System.out.println("CANCEL");
            }
        });
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/dossiersProfesseurView.fxml");
    }
}
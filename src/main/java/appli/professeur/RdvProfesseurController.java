package appli.professeur;

import appli.StartApplication;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RdvProfesseurController implements Initializable {

    @FXML
    private TableView<RendezVous> tableauRdv;
    @FXML
    private Button modifRdv;
    @FXML
    private Button annulerRdv;
    @FXML
    private Label erreur;
    @FXML
    private VBox rechercheAvancee;
    @FXML
    private DatePicker dateDebut;
    @FXML
    private DatePicker dateFin;
    @FXML
    private Spinner<Integer> heureDebut;
    @FXML
    private Spinner<Integer> minuteDebut;
    @FXML
    private Spinner<Integer> heureFin;
    @FXML
    private Spinner<Integer> minuteFin;
    @FXML
    private ComboBox<Salle> salleFiltre;
    @FXML
    private TextField nomRecherche;
    @FXML
    private TextField prenomRecherche;
    @FXML
    private TextField emailRecherche;
    @FXML
    private TextField telRecherche;
    @FXML
    private Button filtrer;
    @FXML
    private Button reset;

    // Données de la colonne sélectionnée depuis le TableView
    private RendezVous rdvSel;

    // Liste des rendez-vous
    private ArrayList<RendezVous> rendezVous;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Rendez-vous");
        String[][] colonnes = {
                {"Id. rdv", "id_rendezvous"},
                {"Date", "date_rendezvous"},
                {"Heure", "heure_rendez"},
                {"Id. dossier", "refDossier"},
                {"Id. salle", "refSalle"},
                {"Salle", "nom_salle"},
                {"Nom", "nomEtudiant"},
                {"Prénom", "prenomEtudiant"},
                {"E-mail", "emailEtudiant"},
                {"Téléphone", "telephone"}
        };
        // Parcours de l'ensemble des colonnes
        for (int i = 0; i < colonnes.length; i++) {
            TableColumn<RendezVous, String> maColonne = new TableColumn<>(colonnes[i][0]);
            maColonne.setCellValueFactory(new PropertyValueFactory<RendezVous,String>(colonnes[i][1]));
            switch(colonnes[i][1]){
                // Pour la colonne "date_rendezvous", mettre la date en format jour/mois/année (dd/MM/yyyy)
                case "date_rendezvous":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty((cellData.getValue().getDate_rendezvous().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                    );
                    break;
                // Pour la colonne "refDossier", cache la colonne et insère l'id du dossier dedans
                case "refDossier":
                    maColonne.setVisible(false);
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(cellData.getValue().getRefDossier().getId_dossier()))
                    );
                    break;
                // Pour la colonne "refSalle", cache la colonne et insère l'id de la salle dedans
                case "refSalle":
                    maColonne.setVisible(false);
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(cellData.getValue().getRefSalle().getId_salle()))
                    );
                    break;
                // Pour la colonne "refDossier", insère le nom de la salle
                case "nom_salle":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(cellData.getValue().getRefSalle().getNom_salle()))
                    );
                    break;
                // Pour la colonne "nomEtudiant", insère le nom de l'étudiant
                case "nomEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(cellData.getValue().getRefDossier().getRefEtudiant().getNomEtudiant()))
                    );
                    break;
                // Pour la colonne "prenomEtudiant", insère le prénom de l'étudiant
                case "prenomEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(cellData.getValue().getRefDossier().getRefEtudiant().getPrenomEtudiant()))
                    );
                    break;
                // Pour la colonne "emailEtudiant", insère l'email de l'étudiant
                case "emailEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(cellData.getValue().getRefDossier().getRefEtudiant().getEmailEtudiant()))
                    );
                    break;
                // Pour la colonne "telephone", insère le téléphone de l'étudiant
                case "telephone":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(cellData.getValue().getRefDossier().getRefEtudiant().getTelephone()))
                    );
                    break;
                default:
                    break;
            }
            tableauRdv.getColumns().add(maColonne);
        }
        tableauRdv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        RendezVousRepository rendezVousRepository = new RendezVousRepository();
        SalleRepository salleRepository = new SalleRepository();
        ArrayList<Salle> salles;
        try {
            this.rendezVous = rendezVousRepository.getMesRendezVous(UtilisateurConnecte.getInstance().getId_utilisateur());
            salles = salleRepository.getSalles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Si aucun rendez-vous prochainement, afficher ce message sur le tableau
        if (this.rendezVous.isEmpty()) {
            this.tableauRdv.setPlaceholder(new Label("Aucun rendez-vous prochainement."));
        }
        // Ajoute les données de la liste dans les colonnes du TableView
        ObservableList<RendezVous> observableList = tableauRdv.getItems();
        observableList.setAll(this.rendezVous);
        // Si un rendez-vous se déroule le jour même, afficher une notification de rappel après chargement de la page
        Platform.runLater(() -> {
            for (RendezVous rdv : this.rendezVous) {
                if (rdv.getDate_rendezvous().isEqual(LocalDateTime.now().toLocalDate())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Rendez-vous le " + rdv.getDate_rendezvous().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    String informations = "Rendez-vous aujourd'hui à " + rdv.getHeure_rendez() +
                            "\npour le dossier n°" + rdv.getRefDossier().getId_dossier() + " (" + rdv.getRefDossier().getRefEtudiant().getNomEtudiant() + " " + rdv.getRefDossier().getRefEtudiant().getPrenomEtudiant() +
                            ") !"
                            ;
                    alert.setHeaderText(informations);
                    alert.showAndWait();
                }
            }
        });
        this.salleFiltre.getItems().add(null);
        for (Salle salle : salles) {
            this.salleFiltre.getItems().add(salle);
        }
        this.dateDebut.setValue(LocalDate.now());
        this.dateFin.setValue(LocalDate.now());
        this.heureFin.getValueFactory().setValue(18);
    }
    @FXML
    protected void onRdvSelection(MouseEvent event) {
        // Un clic gauche = Enregistre les données de la colonne sélectionnée et active les boutons
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1){
            TablePosition cell = tableauRdv.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            this.rdvSel = tableauRdv.getItems().get(indexLigne);
            modifRdv.setDisable(false);
            annulerRdv.setDisable(false);
        // Double-clic gauche = Affiche les informations du dossier d'inscription concerné par le rendez-vous
        } else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
            TablePosition cell = tableauRdv.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            modifRdv.setDisable(false);
            annulerRdv.setDisable(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informations du dossier d'inscription");
            String informations =
                    "Dossier de : " + this.rdvSel.getRefDossier().getRefEtudiant().getNomEtudiant() + " " + this.rdvSel.getRefDossier().getRefEtudiant().getPrenomEtudiant() + "\n" +
                    "Créé le : " + this.rdvSel.getRefDossier().getDate() + " à " + this.rdvSel.getRefDossier().getHeure() + "\n" +
                    "Souhaite intégrer : " + this.rdvSel.getRefDossier().getFilliere() + "\n" +
                    "Motivation : " + this.rdvSel.getRefDossier().getMotivation()
            ;
            alert.setHeaderText(informations);
            alert.setContentText("Dossier pris en charge pour le rendez-vous n°" + this.rdvSel.getId_rendezvous() + ".");
            alert.showAndWait();
        }
    }
    // Redirection vers la page de modification de rendez-vous (avec le rdv sélectionné)
    @FXML
    protected void modifRdv() {
        StartApplication.changeScene("professeur/modifierRdvView.fxml", new ModifierRdvController(this.rdvSel));
    }

    // Affiche un alert avec OK/Cancel pour annuler (supprimer) le rendez-vous
    @FXML
    protected void annulerRdv() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Annuler le rendez-vous");
        alert.setHeaderText("Souhaitez-vous annuler ce rendez-vous ?");
        alert.setContentText("Cette action est irréversible !");
        alert.showAndWait().ifPresent(reponse -> {
            // Si OK cliqué, annule (supprime) le rendez-vous
            if (reponse == ButtonType.OK){
                RendezVousRepository rendezVousRepository = new RendezVousRepository();
                boolean check = false;
                try {
                    check = rendezVousRepository.annuler(this.rdvSel);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (check == true){
                    SalleRepository salleRepository = new SalleRepository();
                    try {
                        new LogsRepository().ajout(new Logs("Modification de la salle id. "+this.rdvSel.getRefSalle().getId_salle(), LocalDateTime.now(), UtilisateurConnecte.getInstance()));
                        check = salleRepository.liberer(this.rdvSel.getRefSalle());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (check == true){
                        try {
                            new LogsRepository().ajout(new Logs("Suppression du rendez-vous pour le dossier id. "+this.rdvSel.getRefDossier().getId_dossier(), LocalDateTime.now(), UtilisateurConnecte.getInstance()));
                            StartApplication.changeScene("professeur/rdvProfesseurView.fxml");
                        } catch (IOException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        this.erreur.setText("Une erreur est survenue.");
                        this.erreur.setVisible(true);
                    }
                } else {
                    this.erreur.setText("Ce rendez-vous n'existe déjà plus.");
                    this.erreur.setVisible(true);
                }
            } else if (reponse == ButtonType.CANCEL){
                System.out.println("CANCEL");
            }
        });
    }

    // Affiche ou désactive les champs de recherche avancée
    @FXML
    protected void activerRecherche() {
        if (this.rechercheAvancee.isVisible()) {
            this.rechercheAvancee.setVisible(false);
            this.rechercheAvancee.setManaged(false);
        } else {
            this.rechercheAvancee.setVisible(true);
            this.rechercheAvancee.setManaged(true);
        }
    }

    // Effectue une requête à la bdd avec les filtres demandés
    @FXML
    protected void filtrage() throws SQLException {
        if (this.dateDebut == null && this.dateFin == null && this.heureDebut == null && this.heureFin == null && this.nomRecherche == null && this.prenomRecherche == null && this.emailRecherche == null && this.telRecherche == null) {
            System.out.println("Aucun filtre.");
        } else {
            String dateDebut = this.dateDebut.getValue() != null ? this.dateDebut.getValue().toString() : "";
            String dateFin = this.dateFin.getValue() != null ? this.dateFin.getValue().toString() : "";
            String heureDebut = this.heureDebut.getValue() != null ? this.heureDebut.getValue().toString() : "";
            String minuteDebut = this.minuteDebut.getValue() != null ? this.minuteDebut.getValue().toString() : "";
            LocalTime timeDebut = LocalTime.of(Integer.parseInt(heureDebut), Integer.parseInt(minuteDebut), 0);
            String heureFin = this.heureFin.getValue() != null ? this.heureFin.getValue().toString() : "";
            String minuteFin = this.minuteFin.getValue() != null ? this.minuteFin.getValue().toString() : "";
            LocalTime timeFin = LocalTime.of(Integer.parseInt(heureFin), Integer.parseInt(minuteFin), 0);
            String salle = this.salleFiltre.getValue() != null ? this.salleFiltre.getValue().toString() : "";
            String nom = this.nomRecherche.getText();
            String prenom = this.prenomRecherche.getText();
            String email = this.emailRecherche.getText();
            String tel = this.telRecherche.getText();
            ArrayList<RendezVous> mesRdv;
            mesRdv = new RendezVousRepository().getMesRendezVousFiltres(dateDebut, dateFin, timeDebut.toString(), timeFin.toString(), salle, nom, prenom, email, tel, UtilisateurConnecte.getInstance().getId_utilisateur());
            ObservableList<RendezVous> observableList = tableauRdv.getItems();
            // Appliquer sur le tableau les résultats filtrés par la requête
            observableList.setAll(mesRdv);
        }
    }

    // Réinitialise les filtres (champs en valeur par défaut)
    @FXML
    protected void resetFiltres() {
        this.dateDebut.setValue(LocalDate.now());
        this.dateFin.setValue(LocalDate.now());
        this.heureDebut.getValueFactory().setValue(8);
        this.minuteDebut.getValueFactory().setValue(0);
        this.heureFin.getValueFactory().setValue(18);
        this.minuteFin.getValueFactory().setValue(0);
        this.salleFiltre.getSelectionModel().clearSelection();
        this.nomRecherche.clear();
        this.prenomRecherche.clear();
        this.emailRecherche.clear();
        this.telRecherche.clear();
        ObservableList<RendezVous> observableList = tableauRdv.getItems();
        observableList.setAll(this.rendezVous);
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/menuProfesseurView.fxml");
    }
}
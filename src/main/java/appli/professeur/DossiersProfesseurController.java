package appli.professeur;

import appli.StartApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import modeles.Dossier;
import repository.DossierRepository;
import repository.EtudiantRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DossiersProfesseurController implements Initializable {

    @FXML
    private TableView<Dossier> tableauDossier;
    @FXML
    private Label erreur;
    @FXML
    private Button rdv;
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
    private ComboBox<String> filiereRecherche;
    @FXML
    private TextField motivationRecherche;
    @FXML
    private TextField nomRecherche;
    @FXML
    private TextField prenomRecherche;
    @FXML
    private ComboBox<String> diplomeRecherche;
    @FXML
    private TextField emailRecherche;
    @FXML
    private TextField telRecherche;
    @FXML
    private Button filtrer;
    @FXML
    private Button reset;

    // Données de la colonne sélectionnée depuis le TableView
    private Dossier dossierSel;

    // Liste des dossiers d'inscription
    private ArrayList<Dossier> dossiers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Dossiers d'inscription");
        String[][] colonnes = {
                {"Id. dossier", "id_dossier"},
                {"Date", "date"},
                {"Heure", "heure"},
                {"Filière", "filliere"},
                {"Motivation", "motivation"},
                {"Id. étudiant", "id_etudiant"},
                {"Nom", "nomEtudiant"},
                {"Prénom", "prenomEtudiant"},
                {"Diplôme", "diplome"},
                {"E-mail", "emailEtudiant"},
                {"Téléphone", "telephone"}
        };
        // Parcours de l'ensemble des colonnes
        for (int i = 0; i < colonnes.length; i++) {
            TableColumn<Dossier,String> maColonne = new TableColumn<>(colonnes[i][0]);
            maColonne.setCellValueFactory(new PropertyValueFactory<Dossier,String>(colonnes[i][1]));
            switch(colonnes[i][1]){
                // Pour la colonne "id_etudiant", cache la colonne et insère l'id de l'étudiant dedans
                case "id_etudiant":
                    maColonne.setVisible(false);
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(cellData.getValue().getRefEtudiant().getId_etudiant()))
                    );
                    break;
                // Pour la colonne "nomEtudiant", insère le nom de l'étudiant
                case "nomEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(cellData.getValue().getRefEtudiant().getNomEtudiant())
                    );
                    break;
                // Pour la colonne "prenomEtudiant", insère le prénom de l'étudiant
                case "prenomEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(cellData.getValue().getRefEtudiant().getPrenomEtudiant())
                    );
                    break;
                // Pour la colonne "diplome", insère le diplôme de l'étudiant
                case "diplome":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(cellData.getValue().getRefEtudiant().getDiplome())
                    );
                    break;
                // Pour la colonne "emailEtudiant", insère l'email de l'étudiant
                case "emailEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(cellData.getValue().getRefEtudiant().getEmailEtudiant())
                    );
                    break;
                // Pour la colonne "telephone", insère le téléphone de l'étudiant
                case "telephone":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(cellData.getValue().getRefEtudiant().getTelephone())
                    );
                    break;
                default:
                    break;
            }
            tableauDossier.getColumns().add(maColonne);
        }
        tableauDossier.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        DossierRepository dossierRepository = new DossierRepository();
        ArrayList<String> filieres;
        EtudiantRepository etudiantRepository = new EtudiantRepository();
        ArrayList<String> diplomes;
        try {
            this.dossiers = dossierRepository.getDossiers();
            filieres = dossierRepository.getFilieres();
            diplomes = etudiantRepository.getDiplomes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Si aucun dossier d'inscription disponible, afficher ce message sur le tableau
        if (this.dossiers.isEmpty()){
            this.tableauDossier.setPlaceholder(new Label("Aucun dossier d'inscription disponible."));
        }
        // Ajoute les données de la liste dans les colonnes du TableView
        ObservableList<Dossier> observableList = tableauDossier.getItems();
        observableList.setAll(this.dossiers);
        this.filiereRecherche.getItems().add(null);
        this.diplomeRecherche.getItems().add(null);
        for (String s : filieres){
            this.filiereRecherche.getItems().add(s);
        }
        for (String s : diplomes){
            this.diplomeRecherche.getItems().add(s);
        }
        this.dateDebut.setValue(LocalDate.now());
        this.dateFin.setValue(LocalDate.now());
        this.heureFin.getValueFactory().setValue(23);
        this.minuteFin.getValueFactory().setValue(59);
    }
    @FXML
    protected void onDossierSelection(MouseEvent event) throws IOException, SQLException {
        DossierRepository dossierRepository = new DossierRepository();
        // Vérifie si le dossier est déjà pris en charge, auquel cas pas besoin de faire de rendez-vous
        boolean estPrisEnCharge = false;
        // Un clic gauche = Enregistre les données de la colonne sélectionnée et active les boutons
        if (event.getButton() == MouseButton.PRIMARY  && event.getClickCount() == 1){
            TablePosition cell = tableauDossier.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            this.dossierSel = tableauDossier.getItems().get(indexLigne);
            estPrisEnCharge = dossierRepository.estPrisEnCharge(this.dossierSel.getId_dossier());
            if (estPrisEnCharge == true){
                rdv.setDisable(true);
                erreur.setText("Ce dossier est déjà pris en charge.");
                erreur.setVisible(true);
            } else {
                rdv.setDisable(false);
                erreur.setVisible(false);
            }
            // Double-clic gauche = Enregistre les données de la colonne sélectionnée et redirection vers prise de rdv
        } else if (event.getButton() == MouseButton.PRIMARY  && event.getClickCount() == 2) {
            TablePosition cell = tableauDossier.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            estPrisEnCharge = dossierRepository.estPrisEnCharge(this.dossierSel.getId_dossier());
            if (estPrisEnCharge == true){
                rdv.setDisable(true);
                erreur.setText("Ce dossier est déjà pris en charge.");
                erreur.setVisible(true);
            } else {
                prendreRdv();
            }
        }
    }
    @FXML
    protected void prendreRdv() throws IOException {
        StartApplication.changeScene("professeur/prendreRdvProfesseurView.fxml", new PrendreRdvProfesseurController(this.dossierSel));
    }

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

    @FXML
    protected void filtrage() throws SQLException {
        if (this.dateDebut == null && this.dateFin == null && this.heureDebut == null && this.heureFin == null && this.filiereRecherche == null && this.motivationRecherche == null && this.nomRecherche == null && this.prenomRecherche == null && this.diplomeRecherche == null && this.emailRecherche == null && this.telRecherche == null) {
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
            String filiere = this.filiereRecherche.getValue() != null ? this.filiereRecherche.getValue() : "";
            String motivation = this.motivationRecherche.getText();
            String nom = this.nomRecherche.getText();
            String prenom = this.prenomRecherche.getText();
            String diplome = this.diplomeRecherche.getValue() != null ? this.diplomeRecherche.getValue() : "";
            String email = this.emailRecherche.getText();
            String tel = this.telRecherche.getText();
            ArrayList<Dossier> dossiers;
            dossiers = new DossierRepository().getDossiersFiltres(dateDebut, dateFin, timeDebut.toString(), timeFin.toString(), filiere, motivation, nom, prenom, diplome, email, tel);
            ObservableList<Dossier> observableList = tableauDossier.getItems();
            // Appliquer sur le tableau les résultats filtrés par la requête
            observableList.setAll(dossiers);
        }
    }

    @FXML
    protected void resetFiltres() {
        this.dateDebut.setValue(LocalDate.now());
        this.dateFin.setValue(LocalDate.now());
        this.heureDebut.getValueFactory().setValue(0);
        this.minuteDebut.getValueFactory().setValue(0);
        this.heureFin.getValueFactory().setValue(23);
        this.minuteFin.getValueFactory().setValue(59);
        this.filiereRecherche.getSelectionModel().clearSelection();
        this.nomRecherche.clear();
        this.prenomRecherche.clear();
        this.diplomeRecherche.getSelectionModel().clearSelection();
        this.emailRecherche.clear();
        this.telRecherche.clear();
        ObservableList<Dossier> observableList = tableauDossier.getItems();
        observableList.setAll(this.dossiers);
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/menuProfesseurView.fxml");
    }
}
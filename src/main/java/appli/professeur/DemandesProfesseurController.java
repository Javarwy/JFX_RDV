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
import modeles.DemandeFourniture;
import modeles.Logs;
import modeles.UtilisateurConnecte;
import repository.DemandeFournitureRepository;
import repository.LogsRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DemandesProfesseurController implements Initializable {

    @FXML
    private TableView<DemandeFourniture> tableauDemandes;
    @FXML
    private Label erreur;
    @FXML
    private Button nouvelleDemande;
    @FXML
    private Button annulerDemande;
    @FXML
    private VBox rechercheAvancee;
    @FXML
    private TextField libelleRecherche;
    @FXML
    private TextField descriptionRecherche;
    @FXML
    private Spinner<Double> prixDebutRecherche;
    @FXML
    private Spinner<Double> prixFinRecherche;
    @FXML
    private TextField fournisseurRecherche;
    @FXML
    private Spinner<Integer> quantiteDebutRecherche;
    @FXML
    private Spinner<Integer> quantiteFinRecherche;
    @FXML
    private TextField raisonRecherche;
    @FXML
    private ComboBox<String> statutRecherche;

    // Données de la colonne sélectionnée depuis le TableView
    private DemandeFourniture demandeSel;

    // Liste des demandes de fournitures
    private ArrayList<DemandeFourniture> demandes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Demande de fournitures");
        String[][] colonnes = {
                {"Id. demande", "idDemandeFourniture"},
                {"Quantité", "quantite"},
                {"Raison", "raison"},
                {"Statut", "statut"},
                {"Id. fourniture", "id_fourniture"},
                {"Libellé", "libelle"},
                {"Description", "description"},
                {"Prix (€)", "prix"},
                {"Fournisseur", "fournisseur"}
        };
        // Parcours de l'ensemble des colonnes
        for (int i = 0; i < colonnes.length; i++) {
            TableColumn<DemandeFourniture,String> maColonne = new TableColumn<>(colonnes[i][0]);
            maColonne.setCellValueFactory(new PropertyValueFactory<DemandeFourniture,String>(colonnes[i][1]));
            switch(colonnes[i][1]){
                case "id_fourniture":
                    maColonne.setVisible(false);
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(cellData.getValue().getRefFourniture().getId_fourniture()))
                    );
                    break;
                case "libelle":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(cellData.getValue().getRefFourniture().getLibelle())
                    );
                    break;
                case "description":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(cellData.getValue().getRefFourniture().getDescription())
                    );
                    break;
                case "prix":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(String.valueOf(cellData.getValue().getRefFourniture().getPrix()))
                    );
                    break;
                case "fournisseur":
                    maColonne.setCellValueFactory(cellData ->
                            new SimpleStringProperty(cellData.getValue().getRefFourniture().getFournisseur())
                    );
                    break;
                default:
                    break;
            }
            tableauDemandes.getColumns().add(maColonne);
        }
        DemandeFournitureRepository demandeFournitureRepository = new DemandeFournitureRepository();
        try {
            this.demandes = demandeFournitureRepository.getDemandeFournituresByUtilisateur(UtilisateurConnecte.getInstance());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Si aucune demande de fourniture effectuée, afficher ce message sur le tableau
        if (this.demandes.isEmpty()) {
            this.tableauDemandes.setPlaceholder(new Label("Aucune demande de fournitures effectuée."));
        } else {
            // Tri de la liste pour mettre les demandes au statut "En cours de validation" en premier dans la liste
            this.demandes.sort((d1, d2) -> {
                if (d1.getStatut().equals("En cours de validation") && !d2.getStatut().equals("En cours de validation")) {
                    return -1;
                } else if (!d1.getStatut().equals("En cours de validation") && d2.getStatut().equals("En cours de validation")) {
                    return 1;
                } else {
                    return d1.getStatut().compareTo(d2.getStatut());
                }
            });
        }
        // Ajoute les données de la liste dans les colonnes du TableView
        ObservableList<DemandeFourniture> observableList = tableauDemandes.getItems();
        observableList.setAll(this.demandes);
        this.prixDebutRecherche.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 99999.99, 0.0, 0.01));
        this.prixFinRecherche.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 99999.99, 0.0, 0.01));
        this.statutRecherche.getItems().addAll(null, "En cours de validation", "Refusée", "Acceptée");
    }
    @FXML
    protected void onDemandeSelection(MouseEvent event) {
        // Un clic gauche = Enregistre les données de la colonne sélectionnée
        if (event.getButton() == MouseButton.PRIMARY  && event.getClickCount() == 1){
            TablePosition cell = tableauDemandes.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            this.demandeSel = tableauDemandes.getItems().get(indexLigne);
            this.annulerDemande.setDisable(false);
        } else if (event.getButton() == MouseButton.PRIMARY  && event.getClickCount() == 2) {
            TablePosition cell = tableauDemandes.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
        }
    }
    @FXML
    protected void nouvelleDemande() throws IOException {
        StartApplication.changeScene("professeur/nouvelleDemandeProfesseurView.fxml");
    }

    @FXML
    protected void annulerDemande() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Annuler la demande de fournitures");
        alert.setHeaderText("Souhaitez-vous annuler cette demande de fournitures ?");
        alert.setContentText("Cette action est irréversible !");
        alert.showAndWait().ifPresent(reponse -> {
            // Si OK cliqué, annule (supprime) le rendez-vous
            if (reponse == ButtonType.OK){
                DemandeFournitureRepository demandeFournitureRepository = new DemandeFournitureRepository();
                boolean check = false;
                try {
                    check = demandeFournitureRepository.annuler(this.demandeSel);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (check == true){
                    try {
                        new LogsRepository().ajout(new Logs("Suppression de la demande de fourniture id. "+this.demandeSel.getIdDemandeFourniture(), LocalDateTime.now(), UtilisateurConnecte.getInstance()));
                        StartApplication.changeScene("professeur/demandesProfesseurView.fxml");
                    } catch (IOException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    this.erreur.setText("Cette demande de fournitures n'existe déjà plus.");
                    this.erreur.setVisible(true);
                }
            }
        });
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
        if (this.libelleRecherche == null && this.descriptionRecherche == null && this.prixDebutRecherche == null && this.prixFinRecherche == null && this.fournisseurRecherche == null && this.quantiteDebutRecherche == null && this.quantiteFinRecherche == null && this.raisonRecherche == null && this.statutRecherche == null) {
            System.out.println("Aucun filtre.");
        } else {
            String libelle = this.libelleRecherche.getText();
            String description = this.descriptionRecherche.getText();
            double prixDebut = this.prixDebutRecherche.getValue();
            double prixFin = this.prixFinRecherche.getValue();
            int quantiteDebut = this.quantiteDebutRecherche.getValue();
            int quantiteFin = this.quantiteFinRecherche.getValue();
            String fournisseur = this.fournisseurRecherche.getText();
            String raison = this.raisonRecherche.getText();
            String statut = this.statutRecherche.getValue() != null ? this.statutRecherche.getValue() : "";
            ArrayList<DemandeFourniture> demandeFournitures;
            demandeFournitures = new DemandeFournitureRepository().getMesDemandeFournituresFiltres(libelle, description, prixDebut, prixFin, quantiteDebut, quantiteFin, fournisseur, raison, statut, UtilisateurConnecte.getInstance());
            ObservableList<DemandeFourniture> observableList = tableauDemandes.getItems();
            // Appliquer sur le tableau les résultats filtrés par la requête
            observableList.setAll(demandeFournitures);
        }
    }

    @FXML
    protected void resetFiltres() {
        this.libelleRecherche.clear();
        this.descriptionRecherche.clear();
        this.prixDebutRecherche.getValueFactory().setValue(0.00);
        this.prixFinRecherche.getValueFactory().setValue(0.00);
        this.quantiteDebutRecherche.getValueFactory().setValue(0);
        this.quantiteFinRecherche.getValueFactory().setValue(0);
        this.fournisseurRecherche.clear();
        this.raisonRecherche.clear();
        this.statutRecherche.getSelectionModel().clearSelection();
        ObservableList<DemandeFourniture> observableList = tableauDemandes.getItems();
        observableList.setAll(this.demandes);
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/menuProfesseurView.fxml");
    }
}
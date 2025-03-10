package appli.secretaire;

import appli.StartApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import modeles.Dossier;
import modeles.Etudiant;
import repository.DossierRepository;
import repository.EtudiantRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListeDossierController implements Initializable {
    @FXML
    private TableView<Dossier> tableauDossier;

    // Liste des dossiers d'inscription
    private ArrayList<Dossier> dossiers;

    // Données de la colonne sélectionnée depuis le TableView
    private Dossier dossierSel;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        StartApplication.changeTitle("Liste des dossiers");
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
        for (int i = 0; i < colonnes.length; i++){
            TableColumn<Dossier,String> maColonne = new TableColumn<>(colonnes[i][0]);
            maColonne.setCellValueFactory(new PropertyValueFactory<Dossier,String>(colonnes[i][1]));
            switch (colonnes[i][1]) {
                // Pour la colonne "id_etudiant", cache la colonne et insère l'id de l'étudiant dedans
                case "id_etudiant":
                    maColonne.setVisible(false);
                    maColonne.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRefEtudiant().getId_etudiant())));
                    break;
                // Pour la colonne "nomEtudiant", insère le nom de l'étudiant
                case "nomEtudiant":
                    maColonne.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRefEtudiant().getNomEtudiant())));
                    break;
                // Pour la colonne "prenomEtudiant", insère le prénom de l'étudiant
                case "prenomEtudiant":
                    maColonne.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRefEtudiant().getPrenomEtudiant())));
                    break;
                // Pour la colonne "diplome", insère le diplôme de l'étudiant
                case "diplome":
                    maColonne.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRefEtudiant().getDiplome())));
                    break;
                // Pour la colonne "emailEtudiant", insère l'email de l'étudiant
                case "emailEtudiant":
                    maColonne.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRefEtudiant().getEmailEtudiant())));
                    break;
                // Pour la colonne "telephone", insère le téléphone de l'étudiant
                case "telephone":
                    maColonne.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRefEtudiant().getTelephone())));
                    break;
                default:
                    break;
                }
                tableauDossier.getColumns().add(maColonne);
            }
            tableauDossier.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
            DossierRepository dossierRepository = new DossierRepository();
            ArrayList<String> filières;
            EtudiantRepository etudiantRepository = new EtudiantRepository();
            ArrayList<String> diplomes;
            try{
                 this.dossiers = dossierRepository.getDossiers();
                 filières = dossierRepository.getFilieres();
                 diplomes = etudiantRepository.getDiplomes();
        } catch (SQLException e){
                throw new RuntimeException(e);
            }
        // Si aucun dossier d'inscription disponible, afficher ce message sur le tableau
        if (this.dossiers.isEmpty()){
            this.tableauDossier.setPlaceholder(new Label("Aucun dossier d'inscription disponible."));
        }
        // Ajoute les données de la liste dans les colonnes du TableView
        ObservableList<Dossier> observableList = tableauDossier.getItems();
        observableList.setAll(this.dossiers);
    }

    @FXML
    protected void onBara(MouseEvent event) throws IOException{

        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1){
            TablePosition cell = tableauDossier.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            this.dossierSel = tableauDossier.getItems().get(indexLigne);
        }else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
            TablePosition cell = tableauDossier.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
        }
    }

    @FXML
    protected void filtrage() {

    }

    @FXML
    protected void resetFiltres() {

    }

    @FXML
    protected void retour() throws IOException{
        StartApplication.changeScene("secretaire/menuSecretaireView.fxml");
    }
}

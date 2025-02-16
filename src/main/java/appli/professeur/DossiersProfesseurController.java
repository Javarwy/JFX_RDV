package appli.professeur;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import modeles.Dossier;
import repository.DossierRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DossiersProfesseurController implements Initializable {

    @FXML
    private TableView<Dossier> tableauDossier;
    @FXML
    private Label erreur;
    @FXML
    private Button rdv;

    // Données de la colonne sélectionnée depuis le TableView
    private Dossier dossierSel;

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
                            new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getRefEtudiant().getId_etudiant()))
                    );
                    break;
                // Pour la colonne "nomEtudiant", insère le nom de l'étudiant
                case "nomEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRefEtudiant().getNomEtudiant())
                    );
                    break;
                // Pour la colonne "prenomEtudiant", insère le prénom de l'étudiant
                case "prenomEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRefEtudiant().getPrenomEtudiant())
                    );
                    break;
                // Pour la colonne "diplome", insère le diplôme de l'étudiant
                case "diplome":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRefEtudiant().getDiplome())
                    );
                    break;
                // Pour la colonne "emailEtudiant", insère l'email de l'étudiant
                case "emailEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRefEtudiant().getEmailEtudiant())
                    );
                    break;
                // Pour la colonne "telephone", insère le téléphone de l'étudiant
                case "telephone":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRefEtudiant().getTelephone())
                    );
                    break;
                default:
                    break;
            }
            tableauDossier.getColumns().add(maColonne);
        }
        DossierRepository dossierRepository = new DossierRepository();
        ArrayList<Dossier> dossiers;
        try {
            dossiers = dossierRepository.getDossiers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Ajoute les données de la liste dans les colonnes du TableView
        ObservableList<Dossier> observableList = tableauDossier.getItems();
        observableList.setAll(dossiers);
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
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/menuProfesseurView.fxml");
    }
}
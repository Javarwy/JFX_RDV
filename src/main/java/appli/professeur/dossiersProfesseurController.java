package appli.professeur;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

public class dossiersProfesseurController implements Initializable {

    @FXML
    private TableView<Dossier> tableauDossier;
    @FXML
    private Button rdv;

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
        for (int i = 0; i < colonnes.length; i++) {
            TableColumn<Dossier,String> maColonne = new TableColumn<>(colonnes[i][0]);
            maColonne.setCellValueFactory(new PropertyValueFactory<Dossier,String>(colonnes[i][1]));
            switch(colonnes[i][1]){
                case "id_etudiant":
                    maColonne.setVisible(false);
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getRefEtudiant().getId_etudiant()))
                    );
                    break;
                case "nomEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRefEtudiant().getNomEtudiant())
                    );
                    break;
                case "prenomEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRefEtudiant().getPrenomEtudiant())
                    );
                    break;
                case "diplome":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRefEtudiant().getDiplome())
                    );
                    break;
                case "emailEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRefEtudiant().getEmailEtudiant())
                    );
                    break;
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
        ObservableList<Dossier> observableList = tableauDossier.getItems();
        observableList.setAll(dossiers);
    }
    @FXML
    protected void onDossierSelection(MouseEvent event) throws IOException {
        if (event.getButton() == MouseButton.PRIMARY  && event.getClickCount() == 1){
            rdv.setDisable(false);
            TablePosition cell = tableauDossier.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            Dossier dossier = tableauDossier.getItems().get(indexLigne);
        } else if (event.getButton() == MouseButton.PRIMARY  && event.getClickCount() == 2) {
            TablePosition cell = tableauDossier.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            Dossier dossier = tableauDossier.getItems().get(indexLigne);
            prendreRdv();
        }
    }
    @FXML
    protected void prendreRdv() throws IOException {
        StartApplication.changeScene("professeur/prendreRdvProfesseurView.fxml", new prendreRdvProfesseurController(this.dossierSel));
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/menuProfesseurView.fxml");
    }
}
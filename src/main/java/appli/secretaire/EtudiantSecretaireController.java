package appli.secretaire;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import modeles.Dossier;
import modeles.Etudiant;
import repository.EtudiantRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EtudiantSecretaireController implements Initializable {

    @FXML
    private TableView<Etudiant> tableauEtudiant;

    private Etudiant etudiantSel;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Liste des fiches étudiant");
        String[][] colonnes = {
                {"Id. etudiant", "id_etudiant"},
                {"Prenom", "prenomEtudiant"},
                {"Nom", "nomEtudiant"},
                {"Diplôme", "diplome"},
                {"E-mail", "emailEtudiant"},
                {"Téléphone", "telephone"}
        };

        for (int i = 0; i < colonnes.length; i++) {
            TableColumn<Etudiant,String> maColonne = new TableColumn<>(colonnes[i][0]);
            maColonne.setCellValueFactory(new PropertyValueFactory<Etudiant,String>(colonnes[i][1]));
            tableauEtudiant.getColumns().add(maColonne);
        }
        // Permet de rendre la taille des colonnes du TableView dynamique
        tableauEtudiant.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        EtudiantRepository etudiantRepository = new EtudiantRepository();
        ArrayList<Etudiant> etudiants;
        try {
            etudiants = etudiantRepository.getEtudiant();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        ObservableList<Etudiant> observableList = tableauEtudiant.getItems();
        observableList.setAll(etudiants);
    }

    @FXML
    protected void bara(MouseEvent event) throws IOException {

        if (event.getButton() == MouseButton.PRIMARY  && event.getClickCount() == 1){
            TablePosition cell = tableauEtudiant.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            this.etudiantSel= tableauEtudiant.getItems().get(indexLigne);
        } else if (event.getButton() == MouseButton.PRIMARY  && event.getClickCount() == 2) {
            TablePosition cell = tableauEtudiant.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
        }

    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("secretaire/menuSecretaireView.fxml");
    }
}

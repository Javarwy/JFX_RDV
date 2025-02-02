package appli.professeur;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
        String[][] colonnes = {
                {"Id. dossier", "id_dossier"},
                {"Date", "date"},
                {"Heure", "heure"},
                {"Filière", "filliere"},
                {"Motivation", "motivation"},
                {"Étudiant", "refEtudiant"}
        };
        for (int i = 0; i < colonnes.length; i++) {
            TableColumn<Dossier,String> maColonne = new TableColumn<>(colonnes[i][0]);
            maColonne.setCellValueFactory(new PropertyValueFactory<Dossier,String>(colonnes[i][1]));
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
    protected void onDossierSelection() throws IOException {

    }
    @FXML
    protected void prendreRdv() throws IOException {

    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/menuProfesseurView.fxml");
    }
}
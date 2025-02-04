package appli.fourniture;

import appli.StartApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modeles.Fourniture;
import repository.FournitureRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class fournitureAffichage implements Initializable {

    @FXML
    private TableView<Fourniture> tableauFourniture;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[][] colonnes = {
                {"ID Fourniture", "id_fourniture"},
                {"Nom", "libelle"},
                {"Fournisseur", "fournisseur"},
                {"Prix", "prix"},
                {"Motivation", "motivation"},
                {"Étudiant", "refEtudiant"}
        };

        for (String[] colonne : colonnes) {
            TableColumn<Fourniture, String> maColonne = new TableColumn<>(colonne[0]);
            maColonne.setCellValueFactory(new PropertyValueFactory<>(colonne[1]));
            tableauFourniture.getColumns().add(maColonne);
        }

        chargerDonnees();
    }

        private void chargerDonnees() {
        FournitureRepository fournitureRepository = new FournitureRepository();
        try {
            ArrayList<Fourniture> fournitures = fournitureRepository.getListFourniture();
            ObservableList<Fourniture> observableList = FXCollections.observableArrayList(fournitures);
            tableauFourniture.setItems(observableList);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onDossierSelection() {
    }

    @FXML
    protected void prendreRdv() {
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/menuProfesseurView.fxml");
    }
}

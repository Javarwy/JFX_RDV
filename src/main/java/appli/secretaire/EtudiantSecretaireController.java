package appli.secretaire;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modeles.Dossier;
import modeles.Etudiant;
import repository.EtudiantRepository;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EtudiantSecretaireController implements Initializable {

    @FXML
    private TableView<Etudiant> tableauEtudiant;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Liste des fiches etudiant");
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
        EtudiantRepository etudiantRepository = new EtudiantRepository();
        ArrayList<Etudiant> etudiants;
        try {
            etudiants = etudiantRepository.getEtudiant();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        ObservableList<Etudiant> observableList = tableauEtudiant.getItems();;
        observableList.setAll(etudiants);



    }
}

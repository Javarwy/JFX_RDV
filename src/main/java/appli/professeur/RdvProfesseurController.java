package appli.professeur;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modeles.RendezVous;
import modeles.UtilisateurConnecte;
import repository.RendezVousRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RdvProfesseurController implements Initializable {

    @FXML
    private TableView<RendezVous> tableauRdv;
    @FXML
    private Button modifRdv;
    @FXML
    private Button annulerRdv;

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
        for (int i = 0; i < colonnes.length; i++) {
            TableColumn<RendezVous, String> maColonne = new TableColumn<>(colonnes[i][0]);
            maColonne.setCellValueFactory(new PropertyValueFactory<RendezVous,String>(colonnes[i][1]));
            switch(colonnes[i][1]){
                case "refDossier":
                    maColonne.setVisible(false);
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getRefDossier().getId_dossier()))
                    );
                    break;
                case "refSalle":
                    maColonne.setVisible(false);
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getRefSalle().getId_salle()))
                    );
                    break;
                case "nom_salle":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getRefSalle().getNom_salle()))
                    );
                    break;
                case "nomEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getRefDossier().getRefEtudiant().getNomEtudiant()))
                    );
                    break;
                case "prenomEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getRefDossier().getRefEtudiant().getPrenomEtudiant()))
                    );
                    break;
                case "emailEtudiant":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getRefDossier().getRefEtudiant().getEmailEtudiant()))
                    );
                    break;
                case "telephone":
                    maColonne.setCellValueFactory(cellData ->
                            new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getRefDossier().getRefEtudiant().getTelephone()))
                    );
                    break;
                default:
                    break;
            }
            tableauRdv.getColumns().add(maColonne);
        }
        RendezVousRepository rendezVousRepository = new RendezVousRepository();
        ArrayList<RendezVous> rendezVous;
        try {
            rendezVous = rendezVousRepository.getMesRendezVous(UtilisateurConnecte.getInstance().getId_utilisateur());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<RendezVous> observableList = tableauRdv.getItems();
        observableList.setAll(rendezVous);
    }
    @FXML
    protected void onRdvSelection(MouseEvent event) throws IOException {

    }
    @FXML
    protected void modifRdv() {

    }

    @FXML
    protected void annulerRdv() {

    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/menuProfesseurView.fxml");
    }
}
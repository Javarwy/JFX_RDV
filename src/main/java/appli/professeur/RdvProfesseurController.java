package appli.professeur;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import modeles.RendezVous;
import modeles.UtilisateurConnecte;
import repository.RendezVousRepository;
import repository.SalleRepository;

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
    @FXML
    private Label erreur;

    private RendezVous rdvSel;

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
    protected void onRdvSelection(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1){
            TablePosition cell = tableauRdv.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            this.rdvSel = tableauRdv.getItems().get(indexLigne);
            modifRdv.setDisable(false);
            annulerRdv.setDisable(false);
        } else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
            TablePosition cell = tableauRdv.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            modifRdv.setDisable(false);
            annulerRdv.setDisable(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informations du dossier d'inscription");
            String informations =
                    "Dossier de : " + this.rdvSel.getRefDossier().getRefEtudiant().getNomEtudiant() + " " + this.rdvSel.getRefDossier().getRefEtudiant().getPrenomEtudiant() + "\n" +
                    "Créé le : " + this.rdvSel.getRefDossier().getDate() + " à " + this.rdvSel.getRefDossier().getHeure() + "\n" +
                    "Souhaite intégrer : " + this.rdvSel.getRefDossier().getFilliere() + "\n" +
                    "Motivation : " + this.rdvSel.getRefDossier().getMotivation()
            ;
            alert.setHeaderText(informations);
            alert.setContentText("Dossier pris en charge pour le rendez-vous n°" + this.rdvSel.getId_rendezvous() + ".");
            alert.showAndWait();
        }
    }
    @FXML
    protected void modifRdv() {
        StartApplication.changeScene("professeur/modifierRdvView.fxml", new ModifierRdvController(this.rdvSel));
    }

    @FXML
    protected void annulerRdv() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Annuler le rendez-vous");
        alert.setHeaderText("Souhaitez-vous annuler ce rendez-vous ?");
        alert.setContentText("Cette action est irréversible !");
        alert.showAndWait().ifPresent(reponse -> {
            if (reponse == ButtonType.OK){
                RendezVousRepository rendezVousRepository = new RendezVousRepository();
                boolean check = false;
                try {
                    check = rendezVousRepository.annuler(this.rdvSel);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (check == true){
                    SalleRepository salleRepository = new SalleRepository();
                    try {
                        check = salleRepository.liberer(this.rdvSel.getRefSalle());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (check == true){
                        try {
                            StartApplication.changeScene("professeur/rdvProfesseurView.fxml");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        this.erreur.setText("Une erreur est survenue.");
                        this.erreur.setVisible(true);
                    }
                } else {
                    this.erreur.setText("Ce rendez-vous n'existe déjà plus.");
                    this.erreur.setVisible(true);
                }
            } else if (reponse == ButtonType.CANCEL){
                System.out.println("CANCEL");
            }
        });
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/menuProfesseurView.fxml");
    }
}
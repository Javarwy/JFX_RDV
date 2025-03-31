package appli.secretaire;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modeles.Dossier;
import modeles.Etudiant;
import modeles.RendezVous;
import repository.DossierRepository;
import repository.EtudiantRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrendreDossierSecretaireController implements Initializable {

    @FXML
    private Label labelEtudiant;
    @FXML
    private TextField filiereField;
    @FXML
    private TextArea motivationArea;

    private Etudiant etudiantSel;

    public PrendreDossierSecretaireController(Etudiant etudiantSel) {
        this.etudiantSel = etudiantSel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        StartApplication.changeTitle("Prendre dossier");
        this.labelEtudiant.setText("Id. etudiant : " + this.etudiantSel.getId_etudiant() + "\nNom : " + this.etudiantSel.getNomEtudiant() + "\nPrénom : " + this.etudiantSel.getPrenomEtudiant());
    }

    @FXML
    protected void ajoutDossier() throws SQLException, IOException {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String filiere = this.filiereField.getText();
        String motivation = this.motivationArea.getText();
        Etudiant etudiant = this.etudiantSel;
        new DossierRepository().ajoutDossier(new Dossier(date, time, filiere, motivation, etudiant));
        StartApplication.changeScene("secretaire/listeDossierInscriptionView.fxml");
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("secretaire/creationDossierInscriptionView.fxml");
    }

}

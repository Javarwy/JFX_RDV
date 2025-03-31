package appli.secretaire;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modeles.UtilisateurConnecte;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenusecretaireController implements Initializable {
    @FXML
    private Label welcomeText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Accueil");
        this.welcomeText.setText("Bienvenue, "+UtilisateurConnecte.getInstance().getNom()+" "+UtilisateurConnecte.getInstance().getPrenom()+" !");
    }

    @FXML
    public void creationFiche(ActionEvent event) throws IOException {
        StartApplication.changeScene("secretaire/CreationFicheEtudiantView.fxml");

    }

    public void listeFiche(ActionEvent event) throws IOException {
        StartApplication.changeScene("secretaire/listeFicheEtudiantView.fxml");

    }
    @FXML
    public void creationDossier(ActionEvent event) throws IOException {
        StartApplication.changeScene("secretaire/creationDossierInscriptionView.fxml");

    }

    public void listeDossier() throws IOException {
        StartApplication.changeScene("secretaire/listeDossierInscriptionView.fxml");

    }


    @FXML
    public void deconnexion(ActionEvent event) throws IOException {
        UtilisateurConnecte.clearInstance();
        StartApplication.changeScene("accueil/loginView.fxml");

    }



}

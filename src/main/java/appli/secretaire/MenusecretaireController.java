package appli.secretaire;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modeles.UtilisateurConnecte;

import java.io.IOException;

public class MenusecretaireController {
    @FXML
    private Label welcomeText;

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

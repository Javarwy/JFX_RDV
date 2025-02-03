package appli.accueil;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import modeles.UtilisateurConnecte;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private Label erreur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Accueil");
        this.welcomeText.setText("Bienvenue, "+UtilisateurConnecte.getInstance().getNom()+" "+UtilisateurConnecte.getInstance().getPrenom()+" !");
    }
    @FXML
    protected void modifierInfos() throws IOException {
        StartApplication.changeScene("accueil/modifierInfosView.fxml");
    }

    @FXML
    protected void deconnexion() throws IOException {
        UtilisateurConnecte.clearInstance();
        StartApplication.changeScene("accueil/loginView.fxml");
    }
}
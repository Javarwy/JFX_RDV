package appli.accueil;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modeles.Utilisateur;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.UtilisateurRepository;

import java.io.IOException;
import java.sql.SQLException;

public class AccueilController {
    @FXML
    private Label erreur;

    @FXML
    protected void deconnexion() throws IOException {
        StartApplication.changeScene("accueil/loginView.fxml");
    }
}
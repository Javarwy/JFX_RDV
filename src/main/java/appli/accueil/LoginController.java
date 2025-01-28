package appli.accueil;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import modeles.Utilisateur;
import modeles.UtilisateurConnecte;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.UtilisateurRepository;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private Label erreur;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void connexion() throws SQLException, IOException {
        String email = this.emailField.getText();
        String password = this.passwordField.getText();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (email.isEmpty() || password.isEmpty()) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
        } else {
            UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
            Utilisateur check = utilisateurRepository.getUtilisateurByEmail(email);
            if (check != null){
                if (!encoder.matches(password, check.getMotDePasse())){
                    this.erreur.setText("Mot de passe incorrect.");
                    this.erreur.setVisible(true);
                } else {
                    UtilisateurConnecte.initInstance(check);
                    redirection();
                }
            } else {
                this.erreur.setText("Utilisateur inexistant.");
                this.erreur.setVisible(true);
            }
        }
    }

    @FXML
    protected void redirection() throws IOException {
        switch (UtilisateurConnecte.getInstance().getRole()){
            case "Secrétaire":
                StartApplication.changeScene("secretaire/menuSecretaireView.fxml");
                break;
            case "Professeur":
                StartApplication.changeScene("professeur/menuProfesseurView.fxml");
                break;
            case "Gestionnaire de stock":
                StartApplication.changeScene("stock/stockView.fxml");
                break;
            default:
                StartApplication.changeScene("accueil/accueilView.fxml");
                break;
        }
    }

    @FXML
    protected void inscription() throws IOException {
        StartApplication.changeScene("accueil/inscriptionView.fxml");
    }
}
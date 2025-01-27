package appli.accueil;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modeles.Utilisateur;
import modeles.UtilisateurConnecte;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.UtilisateurRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierInfosController implements Initializable {
    @FXML
    private Label erreur;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.nomField.setText(UtilisateurConnecte.getInstance().getNom());
        this.prenomField.setText(UtilisateurConnecte.getInstance().getPrenom());
        this.emailField.setText(UtilisateurConnecte.getInstance().getEmail());
        this.passwordField.setPromptText(UtilisateurConnecte.getInstance().getMotDePasse());
    }

    @FXML
    protected void confirmer() throws SQLException, IOException {
        String nom = this.nomField.getText();
        String prenom = this.prenomField.getText();
        String email = this.emailField.getText();
        String password = this.passwordField.getText();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (nom.isEmpty() && prenom.isEmpty() && email.isEmpty() && password.isEmpty()){
            this.erreur.setText("Veuillez remplir au minimum un champ.");
            this.erreur.setVisible(true);
        } else {
            this.erreur.setVisible(false);
            Utilisateur utilisateur = new Utilisateur(
                UtilisateurConnecte.getInstance().getId_utilisateur(), nom, prenom, email, UtilisateurConnecte.getInstance().getMotDePasse(), UtilisateurConnecte.getInstance().getRole()
            );
            if (!encoder.matches(password, utilisateur.getMotDePasse()) && !password.isEmpty()){
                utilisateur.setMotDePasse(encoder.encode(password));
            }
            UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
            Utilisateur emailCheck = utilisateurRepository.getUtilisateurByEmail(email);
            if (emailCheck == null || emailCheck.getEmail().equals(email)){
                this.erreur.setVisible(false);
                boolean check = utilisateurRepository.modification(utilisateur);
                if (check == true){
                    UtilisateurConnecte.clearInstance();
                    UtilisateurConnecte.initInstance(utilisateur);
                    StartApplication.changeScene("accueil/accueilView.fxml");
                } else {
                    this.erreur.setText("Erreur lors de la modification.");
                    this.erreur.setVisible(true);
                }
            } else {
                this.erreur.setText("L'e-mail a déjà été pris !");
                this.erreur.setVisible(true);
            }
        }
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("accueil/accueilView.fxml");
    }

}
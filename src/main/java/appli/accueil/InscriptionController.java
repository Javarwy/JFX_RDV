package appli.accueil;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modeles.Utilisateur;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.UtilisateurRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InscriptionController implements Initializable {
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
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private ChoiceBox<String> roleField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Inscription");
        this.roleField.getItems().addAll("Professeur","Secrétaire","Gestionnaire de stock");
    }

    @FXML
    protected void inscription() throws SQLException, IOException {
        String nom = this.nomField.getText();
        String prenom = this.prenomField.getText();
        String email = this.emailField.getText();
        String password = this.passwordField.getText();
        String confirmPassword = this.confirmPasswordField.getText();
        String role = this.roleField.getSelectionModel().getSelectedItem();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role.equals("- Choisir un rôle -")) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
        } else {
            if (password.equals(confirmPassword)) {
                if (isPasswordValid(password) == false){
                    this.erreur.setText("Le mot de passe doit contenir au moins 12 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial (@$!%*?&).");
                    this.erreur.setVisible(true);
                } else {
                    this.erreur.setVisible(false);
                    Utilisateur utilisateur = new Utilisateur(
                            nom, prenom, email, encoder.encode(password), role
                    );
                    UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
                    Utilisateur emailCheck = utilisateurRepository.getUtilisateurByEmail(email);
                    if (emailCheck != null) {
                        this.erreur.setText("L'adresse e-mail est déjà utilisée !");
                        this.erreur.setVisible(true);
                    } else {
                        this.erreur.setVisible(false);
                        boolean check = utilisateurRepository.inscription(utilisateur);
                        if (check == true){
                            this.erreur.setVisible(false);
                            this.nomField.clear();
                            this.prenomField.clear();
                            this.emailField.clear();
                            this.passwordField.clear();
                            this.confirmPasswordField.clear();
                            StartApplication.changeScene("accueil/loginView.fxml");
                        } else {
                            this.erreur.setText("Erreur lors de l'ajout.");
                            this.erreur.setVisible(true);
                        }
                    }
                }
            } else {
                this.erreur.setText("Erreur, les mots de passe ne coïncident pas !");
                this.erreur.setVisible(true);
            }
        }
    }

    @FXML
    protected void connexion() throws IOException {
        StartApplication.changeScene("accueil/loginView.fxml");
    }
    protected boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$";
        if (!password.matches(passwordPattern)) {
            return false;
        } else{
            return true;
        }
    }
}
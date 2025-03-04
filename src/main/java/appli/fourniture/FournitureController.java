package appli.fourniture;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modeles.Fourniture;
import modeles.UtilisateurConnecte;
import repository.FournitureRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FournitureController implements Initializable {

    @FXML
    private Label erreur;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private ChoiceBox<String> roleField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    protected void changeSceneTableauFournitures() throws IOException {
        StartApplication.changeScene("stock/stockView.fxml");
    }

    @FXML
    protected void modifierInfos() throws IOException {
        StartApplication.changeScene("stock/menuStockView.fxml");
    }

    @FXML
    protected void ajouterFournitureMenu() throws IOException {
        StartApplication.changeScene("stock/addStockView.fxml");
    }

    @FXML
    protected void ajoutFourniture() throws IOException, SQLException {
        String libelle = this.nomField.getText();
        String description = this.prenomField.getText();
        double prix;
        try {
            prix = Double.parseDouble(this.emailField.getText());
        } catch (NumberFormatException e) {
            this.erreur.setText("Le prix doit être un nombre.");
            this.erreur.setVisible(true);
            return;
        }
        String fournisseur = this.passwordField.getText();

        if (libelle.isEmpty() || description.isEmpty() || prix == 0 || fournisseur.isEmpty()) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
            return;
        }

        Fourniture fourniture = new Fourniture(libelle, description, prix, fournisseur);

        FournitureRepository fournitureRepository = new FournitureRepository();
        fournitureRepository.addFourniture(fourniture);
    }

    @FXML
    protected void modifierFourniture() throws IOException, SQLException {
        String libelle = this.nomField.getText();
        String description = this.prenomField.getText();
        double prix;
        try {
            prix = Double.parseDouble(this.emailField.getText());
        } catch (NumberFormatException e) {
            this.erreur.setText("Le prix doit être un nombre.");
            this.erreur.setVisible(true);
            return;
        }
        String fournisseur = this.passwordField.getText();

        if (libelle.isEmpty() || description.isEmpty() || prix == 0 || fournisseur.isEmpty()) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
            return;
        }

        Fourniture fourniture = new Fourniture(libelle, description, prix, fournisseur);

        FournitureRepository fournitureRepository = new FournitureRepository();
        fournitureRepository.updateFourniture(fourniture);
    }

    @FXML
    protected void supprimerFourniture() throws IOException, SQLException {
        String libelle = this.nomField.getText();
        String description = this.prenomField.getText();
        double prix;
        try {
            prix = Double.parseDouble(this.emailField.getText());
        } catch (NumberFormatException e) {
            this.erreur.setText("Le prix doit être un nombre.");
            this.erreur.setVisible(true);
            return;
        }
        String fournisseur = this.passwordField.getText();

        if (libelle.isEmpty() || description.isEmpty() || prix == 0 || fournisseur.isEmpty()) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
            return;
        }

        Fourniture fourniture = new Fourniture(libelle, description, prix, fournisseur);

        FournitureRepository fournitureRepository = new FournitureRepository();
        fournitureRepository.deleteFourniture(fourniture);
    }

    @FXML
    protected void supprimerFournitureMenu() throws IOException {
        StartApplication.changeScene("stock/removeStockView.fxml");
    }

    @FXML
    protected void modifierFournitureMenu() throws IOException {
        StartApplication.changeScene("stock/modifyStockView.fxml");
    }

    @FXML
    protected void deconnexion() throws IOException {
        UtilisateurConnecte.clearInstance();
        StartApplication.changeScene("accueil/loginView.fxml");
    }
}

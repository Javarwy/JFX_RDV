package appli.fourniture;

import appli.StartApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import modeles.DemandeFourniture;
import modeles.Fourniture;
import modeles.UtilisateurConnecte;
import repository.DemandeFournitureRepository;
import repository.FournitureRepository;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FournitureController implements Initializable {

    @FXML
    private Label erreur;

    // TextField Fourniture
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    // TextField DemandeFourniture
    @FXML
    private TextField DnomField;
    @FXML
    private TextField DprenomField;
    @FXML
    private TextField DemailField;
    @FXML
    private TextField DpasswordField;

    @FXML
    private ChoiceBox<String> roleField;
    @FXML
    private ListView<Fourniture> listviewFourniture;

    // Nouveau ListView pour les DemandeFourniture
    @FXML
    private ListView<DemandeFourniture> listviewDemandeFourniture;

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
        String libelle = nomField.getText();
        String description = prenomField.getText();
        double prix;
        try {
            prix = Double.parseDouble(emailField.getText());
        } catch (NumberFormatException e) {
            erreur.setText("Le prix doit être un nombre.");
            erreur.setVisible(true);
            return;
        }
        String fournisseur = passwordField.getText();
        if (libelle.isEmpty() || description.isEmpty() || prix == 0 || fournisseur.isEmpty()) {
            erreur.setText("Veuillez remplir tous les champs.");
            erreur.setVisible(true);
            return;
        }
        Fourniture fourniture = new Fourniture(libelle, description, prix, fournisseur);
        FournitureRepository fournitureRepository = new FournitureRepository();
        fournitureRepository.addFourniture(fourniture);
        StartApplication.changeScene("stock/menuStockView.fxml");
    }

    @FXML
    protected void modifierFourniture() throws IOException, SQLException {
        String libelle = nomField.getText();
        String description = prenomField.getText();
        double prix;
        try {
            prix = Double.parseDouble(emailField.getText());
        } catch (NumberFormatException e) {
            erreur.setText("Le prix doit être un nombre.");
            erreur.setVisible(true);
            return;
        }
        String fournisseur = passwordField.getText();
        if (libelle.isEmpty() || description.isEmpty() || prix == 0 || fournisseur.isEmpty()) {
            erreur.setText("Veuillez remplir tous les champs.");
            erreur.setVisible(true);
            return;
        }
        Fourniture fourniture = new Fourniture(libelle, description, prix, fournisseur);
        FournitureRepository fournitureRepository = new FournitureRepository();
        fournitureRepository.updateFourniture(fourniture);
        StartApplication.changeScene("stock/menuStockView.fxml");
    }

    @FXML
    protected void supprimerFourniture() throws IOException, SQLException {
        String libelle = nomField.getText();
        String description = prenomField.getText();
        double prix;
        try {
            prix = Double.parseDouble(emailField.getText());
        } catch (NumberFormatException e) {
            erreur.setText("Le prix doit être un nombre.");
            erreur.setVisible(true);
            return;
        }
        String fournisseur = passwordField.getText();
        if (libelle.isEmpty() || description.isEmpty() || prix == 0 || fournisseur.isEmpty()) {
            erreur.setText("Veuillez remplir tous les champs.");
            erreur.setVisible(true);
            return;
        }
        Fourniture fourniture = new Fourniture(libelle, description, prix, fournisseur);
        FournitureRepository fournitureRepository = new FournitureRepository();
        fournitureRepository.deleteFourniture(fourniture);
        StartApplication.changeScene("stock/menuStockView.fxml");
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

    @FXML
    protected void page_pres_fourniture() throws IOException {
        StartApplication.changeScene("stock/menuStockView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chargerDonnees();
    }

    private void chargerDonnees() {
        FournitureRepository fournitureRepository = new FournitureRepository();
        try {
            ArrayList<Fourniture> fournitures = fournitureRepository.getListFourniture();
            ObservableList<Fourniture> observableList = FXCollections.observableArrayList(fournitures);
            if (listviewFourniture != null) {
                listviewFourniture.setItems(observableList);
            } else {
                System.out.println("listviewFourniture null.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void chargerDonneesDemandeFourni() {
        DemandeFournitureRepository demandeFournitureRepository = new DemandeFournitureRepository();
        try {
            ArrayList<DemandeFourniture> demandeFournitures = demandeFournitureRepository.getDemandeFournituresByUtilisateur(UtilisateurConnecte.getInstance());
            ObservableList<DemandeFourniture> observableList = FXCollections.observableArrayList(demandeFournitures);
            if (listviewDemandeFourniture != null) {
                listviewDemandeFourniture.setItems(observableList);
            } else {
                System.out.println("listviewDemandeFourniture.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void noDemandeFourniture(){
    }

    @FXML
    private void yesDemandeFourniture(){
    }

    @FXML
    protected void onDossierSelection() {
    }

    @FXML
    protected void prendreRdv() {
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("stock/menuStockView.fxml");
    }
}

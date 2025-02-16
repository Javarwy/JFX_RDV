package appli.professeur;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

public class NouvelleDemandeProfesseurController implements Initializable {
    @FXML
    private VBox boxFournitures;
    @FXML
    private Label erreur;

    // Liste des fournitures récupérées depuis la base de données
    private ArrayList<Fourniture> fournitures = new ArrayList<>();

    // Liste contenant les boxes des différentes fournitures
    private ArrayList<HBox> boxesFournitures = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Nouvelle demande de fournitures");
        try {
            fournitures = new FournitureRepository().getListFourniture();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        ajouter();
    }

    @FXML
    protected void ajouter() {
        // Crée le box pour les champs et l'aligne au centre de la page
        HBox fourniture = new HBox(5);
        fourniture.setAlignment(Pos.CENTER);

        // Champ fourniture
        Label labelFourniture = new Label("Fourniture :");
        ComboBox<Fourniture> choixFourniture = new ComboBox<>();
        choixFourniture.setId("choixFourniture");
        choixFourniture.getItems().addAll(this.fournitures);

        // Champ quantité
        Label labelQuantite = new Label("Quantite :");
        Spinner<Integer> quantiteField = new Spinner<Integer>();
        quantiteField.setId("quantite");
        quantiteField.setEditable(true);
        quantiteField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));

        // Champ raison
        Label labelRaison = new Label("Raison :");
        TextArea raisonArea = new TextArea();
        raisonArea.setId("raison");
        this.boxFournitures.getChildren().add(fourniture);

        // Bouton pour retirer la box fourniture actuelle
        Button retirer = new Button("Retirer");
        retirer.setText("-");
        retirer.setOnAction(e -> {
            this.boxFournitures.getChildren().remove(fourniture);
            this.boxesFournitures.remove(fourniture);
        });

        // Ajoute les différents champs dans la box actuelle
        fourniture.getChildren().addAll(labelFourniture, choixFourniture, labelQuantite, quantiteField, labelRaison, raisonArea, retirer);

        this.boxesFournitures.add(fourniture);
    }

    @FXML
    protected void confirmer() throws IOException, SQLException {
        // Parcourt toutes les boxes des fournitures
        for (HBox box : boxesFournitures){
            // Récupère les valeurs dans les champs
            Fourniture fourniture = ((ComboBox<Fourniture>) box.lookup("#choixFourniture")).getValue();
            int quantite = ((Spinner<Integer>) box.lookup("#quantite")).getValue();
            String raison = ((TextArea) box.lookup("#raison")).getText();
            if (fourniture == null || quantite < 1 || raison.isEmpty()){
                this.erreur.setText("Veuillez remplir tous les champs.");
                this.erreur.setVisible(true);
            } else {
                DemandeFournitureRepository demandeFournitureRepository = new DemandeFournitureRepository();
                boolean check = false;
                check = demandeFournitureRepository.ajout(new DemandeFourniture(quantite, raison, fourniture, UtilisateurConnecte.getInstance()));
                if (check == true){
                    StartApplication.changeScene("professeur/demandesProfesseurView.fxml");
                } else {
                    this.erreur.setText("Une erreur est survenue. Veuillez réessayer. Si l'erreur persiste, veuillez contacter le support.");
                    this.erreur.setVisible(true);
                }
            }
        }
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/demandesProfesseurView.fxml");
    }
}

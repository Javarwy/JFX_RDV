package appli.professeur;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private ComboBox<Fourniture> fourniture;
    @FXML
    private Spinner<Integer> quantite;
    @FXML
    private TextField raison;
    @FXML
    private Label erreur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Nouvelle demande de fournitures");
        ArrayList<Fourniture> fournitures;
        try {
            fournitures = new FournitureRepository().getListFourniture();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        for (Fourniture fourniture : fournitures){
            this.fourniture.getItems().add(fourniture);
        }
    }

    @FXML
    protected void confirmer() throws IOException, SQLException {
        if (this.fourniture.getValue() == null || this.quantite.getValue() == null || this.raison.getText() == null) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
        } else {
            Fourniture fourniture = this.fourniture.getValue();
            int quantite = this.quantite.getValue();
            String raison = this.raison.getText();
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

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/demandesProfesseurView.fxml");
    }
}

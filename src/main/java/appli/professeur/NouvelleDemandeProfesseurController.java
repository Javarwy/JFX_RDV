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
    private Spinner<Integer> quantite;
    @FXML
    private TextField raison;
    @FXML
    private ComboBox<Fourniture> fourniture;
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

    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/demandesProfesseurView.fxml");
    }
}

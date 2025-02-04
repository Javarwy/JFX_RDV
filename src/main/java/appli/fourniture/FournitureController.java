package appli.fourniture;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import modeles.UtilisateurConnecte;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FournitureController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    protected void changeSceneTableauFournitures() throws IOException {
        StartApplication.changeScene("stock/stockView.fxml");
    }

}

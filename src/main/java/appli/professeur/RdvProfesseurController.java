package appli.professeur;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import modeles.RendezVous;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RdvProfesseurController implements Initializable {

    @FXML
    private TableView<RendezVous> tableauRdv;
    @FXML
    private Button modifRdv;
    @FXML
    private Button annulerRdv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Rendez-vous");
    }
    @FXML
    protected void onRdvSelection(MouseEvent event) throws IOException {

    }
    @FXML
    protected void modifRdv() {

    }

    @FXML
    protected void annulerRdv() {

    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/menuProfesseurView.fxml");
    }
}
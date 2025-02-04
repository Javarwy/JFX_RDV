package appli.professeur;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import modeles.Dossier;
import modeles.Salle;
import repository.SalleRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class prendreRdvProfesseurController implements Initializable {

    @FXML
    private TableView<Salle> tableauSalle;
    @FXML
    private Button confirmerRdv;
    @FXML
    private Label idDossier;
    @FXML
    private Label labelDate;
    @FXML
    private DatePicker date;
    @FXML
    private Label labelTime;
    @FXML
    private ComboBox time;

    private Dossier dossierSel;
    private Salle salleSel;

    public prendreRdvProfesseurController(Dossier dossierSel){
        this.dossierSel = dossierSel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Prise de rendez-vous");
        this.idDossier.setText("Rendez-vous pour dossier n°" + this.dossierSel.getId_dossier() + " (" + this.dossierSel.getRefEtudiant().getNomEtudiant() + " " + this.dossierSel.getRefEtudiant().getPrenomEtudiant() + ")");
        String[][] colonnes = {
                {"Id. salle", "id_salle"},
                {"Nom", "nom_salle"}
        };
        for (int i = 0; i < colonnes.length; i++) {
            TableColumn<Salle,String> maColonne = new TableColumn<>(colonnes[i][0]);
            maColonne.setCellValueFactory(new PropertyValueFactory<Salle,String>(colonnes[i][1]));
            tableauSalle.getColumns().add(maColonne);
        }
        SalleRepository salleRepository = new SalleRepository();
        ArrayList<Salle> salles;
        try {
            salles = salleRepository.getSallesLibres();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Salle> observableList = tableauSalle.getItems();
        observableList.setAll(salles);
    }
    @FXML
    protected void onSalleSelection(MouseEvent event) throws IOException {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            confirmerRdv.setDisable(false);
            TablePosition cell = tableauSalle.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            this.salleSel = tableauSalle.getItems().get(indexLigne);
            this.labelDate.setVisible(true);
            this.date.setVisible(true);
            this.labelTime.setVisible(true);
            this.time.setVisible(true);
        }
    }
    @FXML
    protected void confirmerRdv() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer rendez-vous");
        alert.setHeaderText("Souhaitez-vous confirmer votre rendez-vous dans la salle n°" + this.salleSel.getId_salle() + " (" + this.salleSel.getNom_salle() + ") ?");
        alert.showAndWait().ifPresent(reponse -> {
            if (reponse == ButtonType.OK){
                System.out.println("OK");
            } else if (reponse == ButtonType.CANCEL){
                System.out.println("CANCEL");
            }
        });
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/dossiersProfesseurView.fxml");
    }
}
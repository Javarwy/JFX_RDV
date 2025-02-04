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
import modeles.RendezVous;
import modeles.Salle;
import modeles.UtilisateurConnecte;
import repository.RendezVousRepository;
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
    private Label erreur;
    @FXML
    private Label labelDate;
    @FXML
    private DatePicker date;
    @FXML
    private Label labelHeure;
    @FXML
    private Spinner heure;
    @FXML
    private Label labelMinute;
    @FXML
    private Spinner minute;

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
            this.labelHeure.setVisible(true);
            this.heure.setVisible(true);
            this.labelMinute.setVisible(true);
            this.minute.setVisible(true);
        }
    }
    @FXML
    protected void confirmerRdv() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer rendez-vous");
        alert.setHeaderText("Souhaitez-vous confirmer votre rendez-vous dans la salle n°" + this.salleSel.getId_salle() + " (" + this.salleSel.getNom_salle() + ") ?");
        alert.showAndWait().ifPresent(reponse -> {
            if (reponse == ButtonType.OK){
                if (this.date.getValue() == null || this.heure.getValue() == null || this.minute.getValue() == null){
                    this.erreur.setText("Veuillez mettre une date et une heure.");
                    this.erreur.setVisible(true);
                } else {
                    String date = this.date.getValue().toString();
                    String heure = this.heure.getValue().toString();
                    String minute = this.minute.getValue().toString();
                    heure = heure + ":" + minute;
                    RendezVousRepository rendezVousRepository = new RendezVousRepository();
                    boolean check = false;
                    try {
                        check = rendezVousRepository.ajout(new RendezVous(date, heure, this.dossierSel, this.salleSel));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (check == true){
                        SalleRepository salleRepository = new SalleRepository();
                        try {
                            check = salleRepository.reserver(new Salle(this.salleSel.getId_salle(), this.salleSel.getNom_salle(), true, UtilisateurConnecte.getInstance().getId_utilisateur()));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        if (check == true){
                            try {
                                StartApplication.changeScene("professeur/menuProfesseurView.fxml");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            this.erreur.setText("Une erreur est survenue.");
                            this.erreur.setVisible(true);
                        }
                    } else {
                        this.erreur.setText("Un rendez-vous est déjà pris avec ce dossier.");
                        this.erreur.setVisible(true);
                    }
                }
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
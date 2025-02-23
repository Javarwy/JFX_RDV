package appli.professeur;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import modeles.DemandeFourniture;
import modeles.UtilisateurConnecte;
import repository.DemandeFournitureRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DemandesProfesseurController implements Initializable {

    @FXML
    private TableView<DemandeFourniture> tableauDemandes;
    @FXML
    private Label erreur;
    @FXML
    private Button nouvelleDemande;

    // Données de la colonne sélectionnée depuis le TableView
    private DemandeFourniture demandeSel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StartApplication.changeTitle("Demande de fournitures");
        String[][] colonnes = {
                {"Id. demande", "idDemandeFourniture"},
                {"Quantité", "quantite"},
                {"Raison", "raison"},
                {"Statut", "statut"},
                {"Fourniture", "refFourniture"}
        };
        // Parcours de l'ensemble des colonnes
        for (int i = 0; i < colonnes.length; i++) {
            TableColumn<DemandeFourniture,String> maColonne = new TableColumn<>(colonnes[i][0]);
            maColonne.setCellValueFactory(new PropertyValueFactory<DemandeFourniture,String>(colonnes[i][1]));
            tableauDemandes.getColumns().add(maColonne);
        }
        DemandeFournitureRepository demandeFournitureRepository = new DemandeFournitureRepository();
        ArrayList<DemandeFourniture> demandes;
        try {
            demandes = demandeFournitureRepository.getDemandeFournituresByUtilisateur(UtilisateurConnecte.getInstance());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Tri de la liste pour mettre les demandes au statut "En cours de validation" en premier dans la liste
        demandes.sort((d1, d2) -> {
            if (d1.getStatut().equals("En cours de validation") && !d2.getStatut().equals("En cours de validation")) {
                return -1;
            } else if (!d1.getStatut().equals("En cours de validation") && d2.getStatut().equals("En cours de validation")) {
                return 1;
            } else {
                return d1.getStatut().compareTo(d2.getStatut());
            }
        });
        // Ajoute les données de la liste dans les colonnes du TableView
        ObservableList<DemandeFourniture> observableList = tableauDemandes.getItems();
        observableList.setAll(demandes);
    }
    @FXML
    protected void onDemandeSelection(MouseEvent event) {
        // Un clic gauche = Enregistre les données de la colonne sélectionnée
        if (event.getButton() == MouseButton.PRIMARY  && event.getClickCount() == 1){
            TablePosition cell = tableauDemandes.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            this.demandeSel = tableauDemandes.getItems().get(indexLigne);
        } else if (event.getButton() == MouseButton.PRIMARY  && event.getClickCount() == 2) {
            TablePosition cell = tableauDemandes.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
        }
    }
    @FXML
    protected void nouvelleDemande() throws IOException {
        StartApplication.changeScene("professeur/nouvelleDemandeProfesseurView.fxml");
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("professeur/menuProfesseurView.fxml");
    }
}
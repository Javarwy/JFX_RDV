package appli.secretaire;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modeles.Etudiant;
import java.io.IOException;
import java.sql.SQLException;

import modeles.Utilisateur;
import repository.EtudiantRepository;


public class CreationFicheSecretaireController {

    @FXML
    private Label erreur;

    @FXML
    private TextField prenomEtudiantField;

    @FXML
    private TextField nomEtudiantField;

    @FXML
    private TextField diplomeField;

    @FXML
    private TextField emailEtudiantField;

    @FXML
    private TextField telephoneField;

    @FXML
    protected void createFiche() throws SQLException, IOException{
        String prenomEtudiant = this.prenomEtudiantField.getText();
        String nomEtudiant = this.nomEtudiantField.getText();
        String diplome = this.diplomeField.getText();
        String emailEtudiant = this.emailEtudiantField.getText();
        String telephone = this.telephoneField.getText();
            new EtudiantRepository().createFiche(new Etudiant(prenomEtudiant,nomEtudiant,diplome,emailEtudiant,telephone));


    }

}

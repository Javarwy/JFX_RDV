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
    private TextField prenomField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField diplomeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telephoneField;

    @FXML
    protected void createFiche() throws SQLException, IOException{
        String prenom = this.prenomField.getText();
        String nom = this.nomField.getText();
        String diplome = this.diplomeField.getText();
        String email = this.emailField.getText();
        String telephone = this.telephoneField.getText();
        EtudiantRepository etudiantRepository= new EtudiantRepository();

    }

}

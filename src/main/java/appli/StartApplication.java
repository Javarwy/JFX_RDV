package appli;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    private static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("accueil/loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        scene.getStylesheets().add(StartApplication.class.getResource("styles/style.css").toExternalForm());
        mainStage.getIcons().add(new Image(StartApplication.class.getResourceAsStream("images/logo-schuman-short.png")));
        mainStage.setTitle("Connexion");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void changeScene(String nomDuFichierFxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(nomDuFichierFxml));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        scene.getStylesheets().add(StartApplication.class.getResource("styles/style.css").toExternalForm());
        mainStage.setScene(scene);
    }

    public static void changeScene(String nomDuFichierFxml, Object controller){
        mainStage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(nomDuFichierFxml));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1600, 900);
            scene.getStylesheets().add(StartApplication.class.getResource("styles/style.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }

    public static void changeTitle(String titre){
        mainStage.setTitle(titre);
    }

    public static void main(String[] args) {
        launch();
    }
}
package pl.oldzi.assecoTask;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.network_calls.BaseNetworkManager;
import pl.oldzi.assecoTask.util.SceneManager;
import pl.oldzi.assecoTask.view.LoginController;
import pl.oldzi.assecoTask.view.dialogs.AlertDialogController;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/fonts/Inconsolata-Regular.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/fonts/SEGOEUIL.ttf"), 14);

        SceneManager sceneManager = SceneManager.getInstance();
        FXMLLoader loader = sceneManager.setupLoader("/LoginScreen.fxml");
        Stage stage = sceneManager.setupDialogStage("Asseco Cerfificate Manager", loader);
        stage.setMaxHeight(300);
        stage.setMaxWidth(420);
        LoginController controller = loader.getController();
        controller.setOwnerStage(primaryStage);

        stage.show();



    }

    public static void main(String[] args) {
        launch(args);
    }
}

package pl.oldzi.assecoTask.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.model.Credentials;
import pl.oldzi.assecoTask.util.SceneManager;
import pl.oldzi.assecoTask.view.dialogs.WarningDialogController;


public class BaseController {

    private Stage stage;
    private Credentials credentials;

    public void showNoConnectionDialog() {
        Platform.runLater(
                () -> {
                        SceneManager sceneManager = SceneManager.getInstance();
                        FXMLLoader loader = sceneManager.setupLoader("/WarningDialog.fxml");
                        Stage dialogStage = sceneManager.setupDialogStage("No Connection", loader);
                        WarningDialogController controller = loader.getController();
                        controller.setDialogStage(dialogStage);
                        controller.setContent("No Connection", "Please check your network connection");
                        controller.disableCancelButton();
                });
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


}

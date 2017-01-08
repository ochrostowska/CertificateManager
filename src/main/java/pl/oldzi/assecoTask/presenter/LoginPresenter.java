package pl.oldzi.assecoTask.presenter;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.model.Credentials;
import pl.oldzi.assecoTask.network_calls.BaseNetworkManager;
import pl.oldzi.assecoTask.util.SceneManager;
import pl.oldzi.assecoTask.view.LoginController;
import pl.oldzi.assecoTask.view.MainPanelController;
import pl.oldzi.assecoTask.view.dialogs.AlertDialogController;
import pl.oldzi.assecoTask.view.dialogs.WarningDialogController;

public class LoginPresenter implements LoginCommunicator {

    private LoginController view;
    private BaseNetworkManager networkManager;
    private Credentials userCredentials;

    public LoginPresenter(LoginController view) {
        this.view = view;
    }

    public void retrieveToken(String username, String password) {
        networkManager = BaseNetworkManager.getInstance();
        networkManager.setCommunicator(this);
        networkManager.getToken(username, password);
    }

    @Override
    public void tokenRetrieved(String username, String password) {
        userCredentials = new Credentials(username, password);
        Platform.runLater( () -> {
            this.view.closeStage();
            launchMainPanel();

        });
    }

    @Override
    public void noInternetConnection() {
        this.view.showNoConnectionDialog();
    }

    @Override
    public void tokenNotRetrieved() {
        showInvalidCredentialsDialog("Invalid credentials");
    }

    @Override
    public void tokenValid(boolean valid) {
        if (!valid) {
            if (userCredentials != null) {
                retrieveToken(userCredentials.getUsername(), userCredentials.getPassword());
            }
        }
    }

    public void showInvalidCredentialsDialog(String errorMessage) {
        Platform.runLater(
                () -> {
                    SceneManager sceneManager = SceneManager.getInstance();
                    FXMLLoader loader = sceneManager.setupLoader("/AlertDialog.fxml");
                    Stage dialogStage = sceneManager.setupDialogStage("Error", loader);
                    AlertDialogController controller = loader.getController();
                    controller.setDialogStage(dialogStage);
                    controller.setContent("Authentication Error", errorMessage);
                    dialogStage.show();
                });

    }

    private void launchMainPanel() {
        SceneManager sceneManager = SceneManager.getInstance();
        FXMLLoader loader = sceneManager.setupLoader("/MainScreen.fxml");
        Stage mainStage = sceneManager.setupDialogStage("Asseco Certificate Manager", loader);
        MainPanelController controller = loader.getController();
        controller.setStage(mainStage);
        controller.start(userCredentials);
        mainStage.show();
    }
}

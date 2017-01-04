package pl.oldzi.assecoTask.presenter;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import pl.oldzi.assecoTask.model.Credentials;
import pl.oldzi.assecoTask.network_calls.BaseNetworkManager;
import pl.oldzi.assecoTask.view.LoginController;

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
        this.view.launchMainPanel(userCredentials);
    }

    @Override
    public void noInternetConnection() {
        this.view.showNoConnectionDialog();
    }

    @Override
    public void tokenNotRetrieved() {
        showInvalidCredentialsDialog();
    }

    @Override
    public void tokenValid(boolean valid) {
        if(!valid) {
            if(userCredentials!=null) {
                retrieveToken(userCredentials.getUsername(), userCredentials.getPassword());
            }
        }

    }

    public void showInvalidCredentialsDialog() {
        Platform.runLater(
                () -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Authorisation error");
                    alert.setContentText("Try to log again");
                    alert.showAndWait();
                }
        );
    }
}

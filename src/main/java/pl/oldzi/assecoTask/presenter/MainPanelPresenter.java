package pl.oldzi.assecoTask.presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.model.Credentials;
import pl.oldzi.assecoTask.network_calls.BaseNetworkManager;
import pl.oldzi.assecoTask.util.SceneManager;
import pl.oldzi.assecoTask.view.LoginController;
import pl.oldzi.assecoTask.view.MainPanelController;

public class MainPanelPresenter implements LoginCommunicator {

    private MainPanelController view;
    private BaseNetworkManager networkManager;
    private Credentials credentials;
    private SceneManager sceneManager;

    public MainPanelPresenter(MainPanelController view, Credentials credentials) {
        this.view = view;
        this.credentials = credentials;
        networkManager = BaseNetworkManager.getInstance();
        networkManager.setCommunicator(this);
        sceneManager = SceneManager.getInstance();
        this.view.setUserNameInHeader(credentials.getUsername());
    }

    @Override
    public void tokenNotRetrieved() {

    }

    @Override
    public void tokenValid(boolean valid) {
        if (!valid) {
            logOut();
        }
    }

    @Override
    public void tokenRetrieved(String username, String password) {

    }

    @Override
    public void noInternetConnection() {
        this.view.showNoConnectionDialog();
    }

    public void logOut() {
        FXMLLoader loader = sceneManager.setupLoader("/LoginScreen.fxml");
        Stage loginStage = sceneManager.setupDialogStage("Asseco Certificate Manager", loader);
        LoginController controller = loader.getController();
        controller.setOwnerStage(loginStage);
        loginStage.show();
        BaseNetworkManager.getInstance().logOut();
        credentials = null;
    }

    public void showMyUsers(AnchorPane container) {
        FXMLLoader loader = sceneManager.setupLoader("/MyUsersTable.fxml");
        container.getChildren().setAll(sceneManager.setupChildView(loader));
    }

    public void showUsersDetails(AnchorPane container) {
        FXMLLoader loader = sceneManager.setupLoader("/UserDetailsTable.fxml");
        container.getChildren().setAll(sceneManager.setupChildView(loader));
    }

    public void showManageUsers(AnchorPane container) {
        FXMLLoader loader = sceneManager.setupLoader("/ManageUsersTable.fxml");
        container.getChildren().setAll(sceneManager.setupChildView(loader));
    }

    public void showCertificates(AnchorPane container) {
        FXMLLoader loader = sceneManager.setupLoader("/CertTable.fxml");
        container.getChildren().setAll(sceneManager.setupChildView(loader));
    }
}

package pl.oldzi.assecoTask.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.model.Credentials;
import pl.oldzi.assecoTask.network_calls.BaseNetworkManager;
import pl.oldzi.assecoTask.util.SceneManager;
import pl.oldzi.assecoTask.util.UIEffectManager;
import pl.oldzi.assecoTask.view.table_controllers.CertTableController;
import pl.oldzi.assecoTask.view.table_controllers.ManageUsersTableController;
import pl.oldzi.assecoTask.view.table_controllers.MyUsersTableController;
import pl.oldzi.assecoTask.view.table_controllers.UserDetailsTableController;

import java.io.IOException;

public class MainPanelController extends BaseController {
    @FXML
    private AnchorPane dataContainerAnchorPane;
    @FXML
    private AnchorPane userDataAnchorPane;
    @FXML
    private Label nameLabel;
    @FXML
    private ImageView userImage;
    @FXML
    private Label headerLabel;

    private Stage ownerStage;
    private Credentials credentials;
    private SceneManager sceneManager;

    @FXML
    private void initialize() {
        setupUIEffects();
    }

    public void start() {
        sceneManager = SceneManager.getInstance();
        nameLabel.setText(credentials.getUsername());
        onMyUsers();
    }

    public void setStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    @Override
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }


    @FXML
    private void onLogOut() {
        FXMLLoader loader = sceneManager.setupLoader("/LoginScreen.fxml");
        Stage loginStage = sceneManager.setupDialogStage("Asseco Certificate Manager", loader);
        LoginController controller = loader.getController();
        controller.setOwnerStage(loginStage);
        loginStage.show();
        BaseNetworkManager.getInstance().logOut(credentials.getToken());
        credentials = null;
        ownerStage.close();
    }

    @FXML
    private void onMyUsers() {
        FXMLLoader loader = sceneManager.setupLoader("/MyUsersTable.fxml");
        dataContainerAnchorPane.getChildren().setAll(sceneManager.setupChildView(loader));
        MyUsersTableController controller = loader.getController();
        controller.setCredentials(credentials);
        headerLabel.setText("My users");
    }

    @FXML
    private void onUsersDetails() {
        FXMLLoader loader = sceneManager.setupLoader("/UserDetailsTable.fxml");
        dataContainerAnchorPane.getChildren().setAll(sceneManager.setupChildView(loader));
        UserDetailsTableController controller = loader.getController();
//        controller.setCredentials(credentials);
        headerLabel.setText("Users details");
    }

    @FXML
    private void onManageUsers() {
        FXMLLoader loader = sceneManager.setupLoader("/ManageUsersTable.fxml");
        dataContainerAnchorPane.getChildren().setAll(sceneManager.setupChildView(loader));
        ManageUsersTableController controller = loader.getController();
        //  controller.setCredentials(credentials);
        headerLabel.setText("Manage Users");
    }

    @FXML
    private void onCertificates() {
        FXMLLoader loader = sceneManager.setupLoader("/CertTable.fxml");
        dataContainerAnchorPane.getChildren().setAll(sceneManager.setupChildView(loader));
        CertTableController controller = loader.getController();
        controller.setCredentials(credentials);
        controller.setStage(ownerStage);
        headerLabel.setText("Manage Certificates");
    }

    private void setupUIEffects() {
        UIEffectManager effectManager = UIEffectManager.getInstance();
        effectManager.addOnEnterGlowEffect(userImage, Color.PAPAYAWHIP);
        effectManager.addSnowBackground(userDataAnchorPane);
    }
}


package pl.oldzi.assecoTask.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.model.Credentials;
import pl.oldzi.assecoTask.presenter.MainPanelPresenter;
import pl.oldzi.assecoTask.util.UIEffectManager;

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
    private MainPanelPresenter presenter;

    @FXML
    private void initialize() {
        setupUIEffects();
    }

    public void start(Credentials credentials) {
        presenter = new MainPanelPresenter(this, credentials);
        onMyUsers();
    }

    public void setStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    @FXML
    private void onLogOut() {
        presenter.logOut();
        ownerStage.close();
    }

    @FXML
    private void onMyUsers() {
        presenter.showMyUsers(dataContainerAnchorPane);
        headerLabel.setText("My users");
    }

    @FXML
    private void onUsersDetails() {
        presenter.showUsersDetails(dataContainerAnchorPane);
        headerLabel.setText("Users details");
    }

    @FXML
    private void onManageUsers() {
        presenter.showManageUsers(dataContainerAnchorPane);
        headerLabel.setText("Manage Users");
    }

    @FXML
    private void onCertificates() {
        presenter.showCertificates(dataContainerAnchorPane);
        headerLabel.setText("Manage Certificates");
    }

    private void setupUIEffects() {
        UIEffectManager effectManager = UIEffectManager.getInstance();
        effectManager.addOnEnterGlowEffect(userImage, Color.PAPAYAWHIP);
        effectManager.addSnowBackground(userDataAnchorPane);
    }

    public void setUserNameInHeader(String username) {
        nameLabel.setText(username);
    }
}


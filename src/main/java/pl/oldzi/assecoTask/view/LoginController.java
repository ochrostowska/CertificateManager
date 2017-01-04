package pl.oldzi.assecoTask.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.model.Credentials;
import pl.oldzi.assecoTask.presenter.LoginPresenter;
import pl.oldzi.assecoTask.util.SceneManager;
import pl.oldzi.assecoTask.util.UIEffectManager;

public class LoginController extends BaseController {

    private Stage ownerStage;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ImageView deerImageView;
    @FXML
    private Button loginButton;
    @FXML
    private AnchorPane anchorPane;

    private LoginPresenter loginPresenter;

    public LoginController() {
    }

    @FXML
    private void initialize() {
        loginPresenter = new LoginPresenter(this);
        setupUIEffects();
        usernameField.setText("java_developerbj2BgPih");
        passwordField.setText("yBIQPyVS");
    }

    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    @FXML
    private void handleLogin() {
        if (validateInput()) {
            loginPresenter.retrieveToken(usernameField.getText(), passwordField.getText());
        }
    }

    private boolean validateInput() {
        String errorMessage = "";
        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += "No valid username!\n";
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    public void launchMainPanel(Credentials userCredentials) {
        Platform.runLater(
                () -> {
                        SceneManager sceneManager = SceneManager.getInstance();
                        FXMLLoader loader = sceneManager.setupLoader("/MainScreen.fxml");
                        Stage mainStage = sceneManager.setupDialogStage("Asseco Certificate Manager", loader);
                        MainPanelController controller = loader.getController();
                        controller.setStage(mainStage);
                        controller.start(userCredentials);
                        mainStage.show();
                        ownerStage.close();
                });
    }

    private void setupUIEffects() {
        UIEffectManager effectManager = UIEffectManager.getInstance();
        effectManager.addOnEnterGlowEffect(loginButton, Color.BEIGE);
        effectManager.addOnEnterGlowEffect(deerImageView, Color.PAPAYAWHIP);
        effectManager.addGlowEffect(usernameField, Color.PAPAYAWHIP);
        effectManager.addGlowEffect(passwordField, Color.PAPAYAWHIP);
        effectManager.addSnowBackground(anchorPane);
    }
}

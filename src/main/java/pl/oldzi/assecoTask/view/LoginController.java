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
           loginPresenter.showInvalidCredentialsDialog(errorMessage);
            return false;
        }
    }

    private void setupUIEffects() {
        UIEffectManager effectManager = UIEffectManager.getInstance();
        effectManager.addSnowBackground(anchorPane);
        UIEffectManager.addOnEnterGlowEffect(loginButton, Color.BEIGE);
        UIEffectManager.addOnEnterGlowEffect(deerImageView, Color.PAPAYAWHIP);
        UIEffectManager.addGlowEffect(usernameField, Color.PAPAYAWHIP);
        UIEffectManager.addGlowEffect(passwordField, Color.PAPAYAWHIP);
    }

    public void closeStage() {
        ownerStage.close();
    }
}

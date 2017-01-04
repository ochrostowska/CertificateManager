package pl.oldzi.assecoTask.view.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.model.User;

public class EditDialogController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField ageField;
    @FXML
    private Label titleLabel;

    private Stage dialogStage;
    private User changingUser;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUser(User user, String dialogTitle) {
        titleLabel.setText(dialogTitle);
        if (user != null) {
            changingUser = user;
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            firstNameField.setText(user.getFirstName());
            lastNameField.setText(user.getLastName());
            ageField.setText(user.getAge());
        } else {
            changingUser = new User();
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public User getUser() {
        if(okClicked) {
            return changingUser;
        }
        return User.GHOST;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            String username = usernameField.getText();
            changingUser.setUsername(username);
            changingUser.setPassword(passwordField.getText());
            changingUser.setFirstName(firstNameField.getText());
            changingUser.setLastName(lastNameField.getText());
            changingUser.setAge(ageField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText().trim().isEmpty()) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameField.getText().trim().isEmpty()) {
            errorMessage += "No valid last name!\n";
        }
        if (ageField.getText().trim().isEmpty()) {
            errorMessage += "No valid age!\n";
        }
        try {
            Integer.parseInt(ageField.getText().trim());
        } catch (Exception e) {
            errorMessage += "Age format is wrong!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}

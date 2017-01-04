package pl.oldzi.assecoTask.view.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.model.User;

public class FilenameDialogController {
    @FXML
    private TextField fileNameTextField;
    @FXML
    private Label incorrectLabel;

    private Stage dialogStage;
    private boolean okClicked = false;
    private String filename = "cert";

    @FXML
    private void initialize() {
        fileNameTextField.setText(filename);
        incorrectLabel.setVisible(false);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public String getFilename() {
        if(okClicked) {
            return filename;
        }
        return " ";
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        if (fileNameTextField.getText().trim().isEmpty()) {
            incorrectLabel.setVisible(true);
            return false;
        }
        filename = fileNameTextField.getText().trim();
        return true;
    }
}

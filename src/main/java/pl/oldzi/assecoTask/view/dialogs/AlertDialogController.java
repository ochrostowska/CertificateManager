package pl.oldzi.assecoTask.view.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertDialogController {
    @FXML
    private Label titleLabel;
    @FXML
    private Label messageLabel;

    private Stage dialogStage;
    private boolean okClicked = false;
    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void initialize() {
        messageLabel.setWrapText(true);
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setContent(String title, String message) {
        titleLabel.setText(title);
        messageLabel.setText(message);
    }

    @FXML
    private void handleOk() {
        okClicked = true;
        dialogStage.close();
    }
}

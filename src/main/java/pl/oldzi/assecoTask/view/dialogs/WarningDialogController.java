package pl.oldzi.assecoTask.view.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WarningDialogController extends AlertDialogController{
    @FXML
    private Button cancelButton;
    private Stage dialogStage;

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void disableCancelButton() {
        cancelButton.setVisible(false);
    }
}

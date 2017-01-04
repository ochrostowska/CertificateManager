package pl.oldzi.assecoTask.util;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.view.dialogs.FilenameDialogController;

import java.io.IOException;

public class SceneManager {

    private static SceneManager ourInstance;

    public static SceneManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new SceneManager();
        }
        return ourInstance;
    }

    public FXMLLoader setupLoader(String resourcePath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(resourcePath));
        return loader;
    }

    public Stage setupDialogStage(String title, FXMLLoader loader) {
        AnchorPane page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    public AnchorPane setupChildView(FXMLLoader loader) {
        AnchorPane a = null;
        try {
            a = loader.load();
            AnchorPane.setBottomAnchor(a, 0.0);
            AnchorPane.setLeftAnchor(a, 0.0);
            AnchorPane.setRightAnchor(a, 0.0);
            AnchorPane.setTopAnchor(a, 0.0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }
}

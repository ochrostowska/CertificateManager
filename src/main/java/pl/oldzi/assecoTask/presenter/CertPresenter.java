package pl.oldzi.assecoTask.presenter;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.model.Cert;
import pl.oldzi.assecoTask.network_calls.BaseNetworkManager;
import pl.oldzi.assecoTask.util.CertHelper;
import pl.oldzi.assecoTask.util.SceneManager;
import pl.oldzi.assecoTask.view.dialogs.FilenameDialogController;
import pl.oldzi.assecoTask.view.dialogs.WarningDialogController;
import pl.oldzi.assecoTask.view.table_controllers.CertTableController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CertPresenter implements CertDataCommunicator {

    private CertTableController view;
    private BaseNetworkManager networkManager;

    public CertPresenter(CertTableController view) {
        this.view = view;
        networkManager = BaseNetworkManager.getInstance();
        networkManager.setCommunicator(this);
    }

    public void getCerts() {
        networkManager.getCerts();
    }

    public void addCert() {
        File pemFile =  CertHelper.choosePemFile(this.view.getStage());
        if (pemFile != null) {
            String certInString = CertHelper.getCertContentAsString(pemFile);
            networkManager.addCert(CertHelper.encodeCertContentToPEM(certInString));
        }
    }

    public void updateCert(Cert edited) {
        File pemFile = CertHelper.choosePemFile(this.view.getStage());
        if (pemFile != null) {
            String certInString = CertHelper.getCertContentAsString(pemFile);
            networkManager.editCert(edited.getId(), CertHelper.encodeCertContentToPEM(certInString));
        }
    }

    public void deleteCert(Cert cert) {
        SceneManager manager = SceneManager.getInstance();
        FXMLLoader loader = manager.setupLoader("/WarningDialog.fxml");
        Stage dialogStage = manager.setupDialogStage("Warning", loader);

        WarningDialogController controller = loader.getController();
        controller.setContent("Delete certificate", "Are you sure you want to delete certificate with id " + cert.getId() + " ?");
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();

        if (controller.isOkClicked())
            networkManager.deleteCert(cert.getId());
    }

    @Override
    public void certDataRetrieved(ObservableList<Cert> certs) {
        if (certs != null) {
            this.view.parseData(certs);
        }
    }

    @Override
    public void certDeleted(boolean success) {
        getCerts();
    }

    @Override
    public void certAdded(boolean success) {
        getCerts();
    }

    @Override
    public void noInternetConnection() {
        this.view.showNoConnectionDialog();
    }

    public void saveCertToFile(Cert cert) {
        SceneManager sceneManager = SceneManager.getInstance();
        FXMLLoader loader = sceneManager.setupLoader("/FileNameDialog.fxml");
        Stage dialogStage = sceneManager.setupDialogStage("Save certificate", loader);
        FilenameDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();

        String filename = controller.getFilename();
        if (!filename.trim().isEmpty()) {
            File destination = new File(filename + ".pem");
            String path = destination.getAbsolutePath();
            System.out.println(destination.getAbsolutePath());
            try {
                Path p = Files.write(Paths.get(path), cert.getRaw_bytes().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                System.out.println(p.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

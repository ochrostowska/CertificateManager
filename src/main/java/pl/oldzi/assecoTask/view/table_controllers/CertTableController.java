package pl.oldzi.assecoTask.view.table_controllers;

import com.google.common.io.Files;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.model.Cert;
import pl.oldzi.assecoTask.model.User;
import pl.oldzi.assecoTask.presenter.CertPresenter;
import pl.oldzi.assecoTask.util.SceneManager;
import pl.oldzi.assecoTask.view.BaseController;
import pl.oldzi.assecoTask.view.dialogs.EditDialogController;
import pl.oldzi.assecoTask.view.dialogs.FilenameDialogController;
import pl.oldzi.assecoTask.view.dialogs.WarningDialogController;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class CertTableController extends BaseController {
    @FXML
    private TableView<Cert> certTableView;
    @FXML
    private TableColumn<Cert, String> idColumn;
    @FXML
    private TableColumn<Cert, String> ownerColumn;
    @FXML
    private TableColumn<Cert, String> commonNameColumn;
    @FXML
    private TableColumn<Cert, String> validFromColumn;
    @FXML
    private TableColumn<Cert, String> validToColumn;

    private CertPresenter presenter;
    private Stage stage;

    public CertTableController() {
    }

    @FXML
    private void initialize() {
        presenter = new CertPresenter(this);
        getData();
        setValues();
        addActionColumns();
    }

    @FXML
    private void onAddNew() {
        presenter.addCert();
    }

    private void setValues() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        ownerColumn.setCellValueFactory(cellData -> cellData.getValue().ownerProperty());
        commonNameColumn.setCellValueFactory(cellData -> cellData.getValue().certCommonNameProperty());
        validFromColumn.setCellValueFactory(cellData -> cellData.getValue().certValidBeginProperty());
        validToColumn.setCellValueFactory(cellData -> cellData.getValue().certValidEndProperty());
    }

    private void addActionColumns() {
        certTableView.getColumns().add(createEditColumn());
        certTableView.getColumns().add(createDeleteColumn());
        certTableView.getColumns().add(createSaveColumn());
    }

    private void getData() {
        presenter.getCerts();
    }

    public void parseData(ObservableList<Cert> certs) {
        System.out.println(certs.size());
        System.out.println(certs.get(0).certValidBeginProperty());
        certTableView.setItems(certs);
    }

    private TableColumn<Cert, Cert> createEditColumn() {
        TableColumn<Cert, Cert> column = new TableColumn<>(" ");

        column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        column.setCellFactory(param -> new TableCell<Cert, Cert>() {
            private final Button button = new Button("Edit");

            @Override
            protected void updateItem(Cert cert, boolean empty) {
                super.updateItem(cert, empty);
                if (cert == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> presenter.updateCert(cert));
            }
        });
        return column;
    }

    private TableColumn<Cert, Cert> createDeleteColumn() {
        TableColumn<Cert, Cert> column = new TableColumn<>(" ");

        column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        column.setCellFactory(param -> new TableCell<Cert, Cert>() {
            private final Button button = new Button("Delete");

            @Override
            protected void updateItem(Cert cert, boolean empty) {
                super.updateItem(cert, empty);
                if (cert == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> presenter.deleteCert(cert));
            }
        });
        return column;
    }

    private TableColumn<Cert, Cert> createSaveColumn() {
        TableColumn<Cert, Cert> column = new TableColumn<>(" ");

        column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        column.setCellFactory(param -> new TableCell<Cert, Cert>() {
            private final Button button = new Button("Save");

            @Override
            protected void updateItem(Cert cert, boolean empty) {
                super.updateItem(cert, empty);
                if (cert == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> presenter.saveCertToFile(cert));
            }
        });
        return column;
    }

    @Override
    public void setStage(Stage stage) {
        super.setStage(stage);
    }

    public Stage getStage() {
        return stage;
    }
}

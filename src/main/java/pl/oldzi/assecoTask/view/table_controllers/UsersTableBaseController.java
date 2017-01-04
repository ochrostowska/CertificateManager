package pl.oldzi.assecoTask.view.table_controllers;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.model.Credentials;
import pl.oldzi.assecoTask.model.User;
import pl.oldzi.assecoTask.presenter.UserPresenter;
import pl.oldzi.assecoTask.view.BaseController;
import pl.oldzi.assecoTask.view.dialogs.EditDialogController;

import java.io.IOException;


public abstract class UsersTableBaseController extends BaseController {
    @FXML
    private TableView<User> userDetailsTable;
    @FXML
    private TableColumn<User, String> usernameColumn;

    private UserPresenter userPresenter;
    private Credentials credentials;

    @FXML
    private void initialize() {
    }

    protected void setPresenter(UserPresenter presenter) {
        userPresenter = presenter;
    }

    public void getData() {
        userPresenter.getUserDetails();
    }

    public void parseData(ObservableList<User> users) {
        userDetailsTable.setItems(users);
    }

    protected TableColumn<User, User> addDeleteColumn() {
        TableColumn<User, User> deleteColumn = new TableColumn<>(" ");

        deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        deleteColumn.setCellFactory(param -> new TableCell<User, User>() {
            private final Button delete = new Button("Delete");
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (user == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(delete);
                delete.setOnAction(event -> userPresenter.deleteUser(user));
            }
        });
        return deleteColumn;
    }

    protected TableColumn<User, User> addEditColumn() {
        TableColumn<User, User> column = new TableColumn<>(" ");

        column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        column.setCellFactory(param -> new TableCell<User, User>() {
            private final Button button= new Button("Edit");
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (user == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);

                button.setOnAction(event -> userPresenter.updateUser(user));
            }
        });
        return column;
    }
}
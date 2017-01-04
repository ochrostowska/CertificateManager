package pl.oldzi.assecoTask.view.table_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.oldzi.assecoTask.model.User;
import pl.oldzi.assecoTask.presenter.UserPresenter;

public class MyUsersTableController extends UsersTableBaseController {
    @FXML
    private TableView<User> userDetailsTable;
    @FXML
    private TableColumn<User, String> usernameColumn;

    private UserPresenter userPresenter;

    public MyUsersTableController() {}

    @FXML
    private void initialize() {
        userPresenter = new UserPresenter(this);
        setPresenter(userPresenter);
        getData();
        addActionColumns();
    }

    private void addActionColumns() {
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        TableColumn<User, User> editColumn = addEditColumn();
        TableColumn<User, User> deleteColumn = addDeleteColumn();
        userDetailsTable.getColumns().add(editColumn);
        userDetailsTable.getColumns().add(deleteColumn);
    }

    @FXML
    private void onAddNew() {
        userPresenter.addUser();
    }
}

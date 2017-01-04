package pl.oldzi.assecoTask.view.table_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.oldzi.assecoTask.model.User;

public class ManageUsersTableController extends UserDetailsTableController {
    @FXML
    private TableView<User> userDetailsTable;
    @Override
    protected void addActionColumns() {
        TableColumn<User, User> c = addDeleteColumn();
        userDetailsTable.getColumns().add(c);
    }
}

package pl.oldzi.assecoTask.view.table_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.oldzi.assecoTask.model.User;
import pl.oldzi.assecoTask.presenter.UserPresenter;

public class UserDetailsTableController extends UsersTableBaseController {
    @FXML
    private TableView<User> userDetailsTable;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> ageColumn;
    @FXML
    private TableColumn<User, String> ownerColumn;

    private UserPresenter userPresenter;

    public UserDetailsTableController() {
    }

    @FXML
    private void initialize() {
        userPresenter = new UserPresenter(this);
        setPresenter(userPresenter);
        getData();
        setValues();
        addActionColumns();
    }

    private void setValues() {
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty());
        ownerColumn.setCellValueFactory(cellData -> cellData.getValue().ownerProperty());

    }

    protected void addActionColumns() {
        TableColumn<User, User> editColumn = addEditColumn();
        userDetailsTable.getColumns().add(editColumn);
    }
}

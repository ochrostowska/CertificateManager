package pl.oldzi.assecoTask.view.table_controllers;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import pl.oldzi.assecoTask.model.User;
import pl.oldzi.assecoTask.presenter.UserPresenter;
import pl.oldzi.assecoTask.util.UIEffectManager;

public class MyUsersTableController extends UsersTableBaseController {
    @FXML
    private TableView<User> userDetailsTable;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> surnameColumn;
    @FXML
    private TextField nameFilter;
    @FXML
    private TextField surnameFilter;


    private UserPresenter userPresenter;

    public MyUsersTableController() {}

    @FXML
    private void initialize() {
        userPresenter = new UserPresenter(this);
        setPresenter(userPresenter);
        getData();
        addActionColumns();
        setupUIEffects();
    }

    private void addActionColumns() {
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        TableColumn<User, User> editColumn = addEditColumn();
        TableColumn<User, User> deleteColumn = addDeleteColumn();
        userDetailsTable.getColumns().add(editColumn);
        userDetailsTable.getColumns().add(deleteColumn);
    }

    @FXML
    private void onAddNew() {
        userPresenter.addUser();
    }

    private void setupUIEffects() {
        UIEffectManager.addGlowEffect(nameFilter, Color.BLANCHEDALMOND);
        UIEffectManager.addGlowEffect(surnameFilter, Color.BLANCHEDALMOND);
    }

    @Override
    public void parseData(ObservableList<User> users) {
        FilteredList<User> filteredData = new FilteredList<>(users, p -> true);

        nameFilter.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(user -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            return user.getFirstName().toLowerCase().contains(lowerCaseFilter);
        }));

        surnameFilter.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(user -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            return user.getLastName().toLowerCase().contains(lowerCaseFilter);
        }));

        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(userDetailsTable.comparatorProperty());
        userDetailsTable.setItems(sortedData);
    }
}

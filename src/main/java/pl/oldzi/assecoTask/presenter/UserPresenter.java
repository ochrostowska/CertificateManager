package pl.oldzi.assecoTask.presenter;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import pl.oldzi.assecoTask.model.User;
import pl.oldzi.assecoTask.network_calls.BaseNetworkManager;
import pl.oldzi.assecoTask.util.SceneManager;
import pl.oldzi.assecoTask.view.dialogs.EditDialogController;
import pl.oldzi.assecoTask.view.dialogs.WarningDialogController;
import pl.oldzi.assecoTask.view.table_controllers.MyUsersTableController;
import pl.oldzi.assecoTask.view.table_controllers.UserDetailsTableController;
import pl.oldzi.assecoTask.view.table_controllers.UsersTableBaseController;

public class UserPresenter implements UserDataCommunicator {

    private UsersTableBaseController viewController;
    private BaseNetworkManager networkManager;
    private SceneManager sceneManager = SceneManager.getInstance();

    public UserPresenter(UserDetailsTableController view) {
        this.viewController = view;
        networkManager = BaseNetworkManager.getInstance();
        networkManager.setCommunicator(this);
    }

    public UserPresenter(MyUsersTableController myUsersView) {
        this.viewController = myUsersView;
        networkManager = BaseNetworkManager.getInstance();
        networkManager.setCommunicator(this);
    }

    public void getUserDetails() {
        networkManager.getUsers();
    }

    public void addUser() {
        FXMLLoader loader = sceneManager.setupLoader("/EditDialog.fxml");
        Stage dialogStage = sceneManager.setupDialogStage("Add User", loader);

        EditDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUser(null, "Add user");
        dialogStage.showAndWait();
        User user = controller.getUser();

        if (user != User.GHOST && controller.isOkClicked()) {
            networkManager.addUser(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getAge());
        }
    }

    public void updateUser(User user) {
        FXMLLoader loader = sceneManager.setupLoader("/EditDialog.fxml");
        Stage dialogStage = sceneManager.setupDialogStage("Edit User", loader);

        EditDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUser(user, "Edit user");
        dialogStage.showAndWait();

        if (controller.getUser() != User.GHOST && controller.isOkClicked()) {
            networkManager.editUser(user.getUsername(), user.getFirstName(), user.getLastName(), user.getAge());
        }
    }

    public void deleteUser(User user) {
        FXMLLoader loader = sceneManager.setupLoader("/WarningDialog.fxml");
        Stage dialogStage = sceneManager.setupDialogStage("Warning", loader);

        WarningDialogController controller = loader.getController();
        controller.setContent("Delete user", "Are you sure you want to delete user " + user.getUsername() + " ?");
        controller.setDialogStage(dialogStage);
        dialogStage.showAndWait();

        if (controller.isOkClicked()) {
            networkManager.deleteUser(user.getUsername());
        }
    }

    @Override
    public void userDataRetrieved(ObservableList<User> users) {
        if (users != null) {
            this.viewController.parseData(users);
        }
    }

    @Override
    public void userAdded(boolean success) {
        networkManager.getUsers();
    }

    @Override
    public void userDeleted(boolean success) {
        networkManager.getUsers();
    }

    @Override
    public void tokenNotRetrieved() {
    }

    @Override
    public void tokenValid(String token, boolean valid) {
    }

    @Override
    public void tokenRetrieved(String username, String password, String accessToken) {
    }

    @Override
    public void noInternetConnection() {

    }

}

package pl.oldzi.assecoTask.presenter;

import javafx.collections.ObservableList;
import pl.oldzi.assecoTask.model.User;

public interface UserDataCommunicator {
    void userDataRetrieved(ObservableList<User> users);
    void userDeleted(boolean success);
    void userAdded(boolean success);
    void noInternetConnection();
}

package pl.oldzi.assecoTask.presenter;

public interface LoginCommunicator {
    void tokenNotRetrieved();
    void tokenValid(boolean valid);
    void tokenRetrieved(String username, String password);

    void noInternetConnection();
}

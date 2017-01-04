package pl.oldzi.assecoTask.presenter;

public interface LoginCommunicator {
    void tokenNotRetrieved();
    void tokenValid(String token, boolean valid);
    void tokenRetrieved(String username, String password, String accessToken);

    void noInternetConnection();
}

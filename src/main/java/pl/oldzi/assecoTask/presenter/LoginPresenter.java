package pl.oldzi.assecoTask.presenter;

import pl.oldzi.assecoTask.model.Credentials;
import pl.oldzi.assecoTask.network_calls.BaseNetworkManager;
import pl.oldzi.assecoTask.view.LoginController;

public class LoginPresenter implements LoginCommunicator {

    private LoginController view;
    private BaseNetworkManager networkManager;
    private Credentials userCredentials;

    public LoginPresenter(LoginController view) {
        this.view = view;
    }

    public void retrieveToken(String username, String password) {
        networkManager = BaseNetworkManager.getInstance();
        networkManager.setCommunicator(this);
        networkManager.getToken(username, password);
    }

    @Override
    public void tokenRetrieved(String username, String password, String accessToken) {
        userCredentials = new Credentials(username, password, accessToken);
        this.view.launchMainPanel(userCredentials);
    }

    @Override
    public void noInternetConnection() {
        this.view.showNoConnectionDialog();
    }

    @Override
    public void tokenNotRetrieved() {
        this.view.showInvalidCredentialsDialog();
    }

    @Override
    public void tokenValid(String token, boolean valid) {
        if(!valid) {
            if(userCredentials!=null) {
                retrieveToken(userCredentials.getUsername(), userCredentials.getPassword());
            }
        }
    }
}

package pl.oldzi.assecoTask.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Credentials {

    private StringProperty username;
    private StringProperty password;
    private StringProperty token;

    public Credentials(String username, String password, String token) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.token = new SimpleStringProperty(token);
    }
    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getToken() {
        return token.get();
    }

    public StringProperty tokenProperty() {
        return token;
    }

    public void setToken(String token) {
        this.token.set(token);
    }
}
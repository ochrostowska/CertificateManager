package pl.oldzi.assecoTask.model;

import javafx.beans.property.*;

import java.util.LinkedHashMap;

public class User {

    private StringProperty username;
    private StringProperty password;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty age;
    private StringProperty owner;
    public static final User GHOST = new User(" ", " ", " ", " ", " ", " ");

    public User() {
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.age = new SimpleStringProperty();
        this.owner = new SimpleStringProperty();
    }

    public User(String username, String password, String firstName, String lastName, String age, String owner) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.age = new SimpleStringProperty(age);
        this.owner = new SimpleStringProperty(owner);
    }

    public User(LinkedHashMap<String, String> personData) {
        this.firstName = new SimpleStringProperty(personData.get("firstName"));
        this.lastName = new SimpleStringProperty(personData.get("lastName"));
        this.username = new SimpleStringProperty(personData.get("username"));
        this.age = new SimpleStringProperty(personData.get("age"));
        this.password = new SimpleStringProperty(personData.get("password"));
        this.owner = new SimpleStringProperty(personData.get("owner"));
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

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getAge() {
        return age.get();
    }

    public StringProperty ageProperty() {
        return age;
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public String getOwner() {
        return owner.get();
    }

    public StringProperty ownerProperty() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }
}

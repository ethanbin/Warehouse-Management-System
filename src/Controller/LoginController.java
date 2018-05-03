package Controller;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
    //References to our objects and fields create in SceneBuilder.
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    SceneController sceneController;

    public void init(SceneController sc)  {
        sceneController = sc;
    }

    //Methods to be called by events in GUI
    @FXML
    protected void loginAction(ActionEvent event) throws Exception {
        if (MainController.getInstance().loginUser(usernameField.getText(), passwordField.getText())) {
            System.out.println("User logged in.");
            sceneController.activate("Home");
        }
        else
            System.out.println("Incorrect username/password.");
    }

    @FXML
    protected void quitAction(ActionEvent event)    {
        Platform.exit();
    }

    @FXML
    void initialize() {
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'LoginScreen.fxml'.";

    }}


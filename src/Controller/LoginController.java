package Controller;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
    //References to our objects and fields create in SceneBuilder.
    @FXML private TextField UsernameField;
    @FXML private TextField PasswordField;

    SceneController sceneController;

    public void init(SceneController sc)  {
        sceneController = sc;
    }

    //Methods to be called by events in GUI
    @FXML
    protected void loginAction(ActionEvent event) throws Exception    {
        System.out.println("User logged in.");
        sceneController.activate("Home");
    }

    @FXML
    protected void quitAction(ActionEvent event)    {
        Platform.exit();
    }
}


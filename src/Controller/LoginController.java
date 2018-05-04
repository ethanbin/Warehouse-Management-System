package Controller;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController {
    //References to our objects and fields create in SceneBuilder.
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;

    private SceneController sceneController;
    Image userImage = new Image("file:user.png");
    Image passwordImage = new Image("file:lock.png");

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
            usernameField.clear();
            passwordField.clear();
            usernameField.setPromptText("INCORRECT USERNAME/PASSWORD");
            passwordField.setPromptText("INCORRECT USERNAME/PASSWORD");
            usernameField.setStyle("-fx-prompt-text-fill: rgba(255, 25, 29, 0.65)");
            passwordField.setStyle("-fx-prompt-text-fill: rgba(255, 25, 29, 0.65)");
    }

    @FXML
    protected void quitAction(ActionEvent event)    {
        Platform.exit();
    }

    @FXML
    void initialize() {
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'LoginScreen.fxml'.";

        usernameField.setStyle("-fx-effect: dropshadow(two-pass-box , rgba(0, 0, 0, 0.11), 10, 0.0 , 4, 5)");
        passwordField.setStyle("-fx-effect: dropshadow(two-pass-box , rgba(0, 0, 0, 0.11), 10, 0.0 , 4, 5)");

        ImageView userImageView = new ImageView(userImage);
        userImageView.setFitWidth(45);
        userImageView.setFitHeight(45);
        usernameLabel.setGraphic(userImageView);
        usernameLabel.setStyle("-fx-effect: dropshadow(two-pass-box , rgba(0, 0, 0, 0.11), 10, 0.0 , 4, 5)");

        ImageView passwordImageView = new ImageView(passwordImage);
        passwordImageView.setFitWidth(45);
        passwordImageView.setFitHeight(45);
        passwordLabel.setGraphic(passwordImageView);
        passwordLabel.setStyle("-fx-effect: dropshadow(two-pass-box , rgba(0, 0, 0, 0.11), 10, 0.0 , 4, 5)");
    }}


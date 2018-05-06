package Controller;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LoginController {
    //References to our objects and fields create in SceneBuilder.
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;


    Image userImage = new Image("img/user.png");
    Image passwordImage = new Image("img/lock.png");

    //Methods to be called by events in GUI
    @FXML
    protected void loginAction(ActionEvent event) throws Exception {
        if (MainController.getInstance().loginUser(usernameField.getText(), passwordField.getText())) {
            System.out.println("User logged in.");
            SceneController.getInstance().activate("Home");
        }
        else
            System.out.println("Incorrect username/password.");
            loginAlertStyle();
    }

    //Log in by hitting the enter key.
    @FXML
    protected void enterKeyLogin(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER)    {
            if (MainController.getInstance().loginUser(usernameField.getText(), passwordField.getText())) {
                System.out.println("User logged in.");
                SceneController.getInstance().activate("Home");
            }
            else
                System.out.println("Incorrect username/password.");
                loginAlertStyle();
        }
    }

    private void loginAlertStyle() {
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

        usernameField.setStyle("-fx-effect: dropshadow(two-pass-box , rgba(0, 0, 0, 0.20), 10, 0.0 , 4, 5)");
        passwordField.setStyle("-fx-effect: dropshadow(two-pass-box , rgba(0, 0, 0, 0.20), 10, 0.0 , 4, 5)");

        //Username login img.
        ImageView userImageView = new ImageView(userImage);
        userImageView.setFitWidth(45);
        userImageView.setFitHeight(45);
        usernameLabel.setGraphic(userImageView);
        usernameLabel.setStyle("-fx-effect: dropshadow(two-pass-box , rgba(0, 0, 0, 0.20), 10, 0.0 , 4, 5)");

        //Password login img.
        ImageView passwordImageView = new ImageView(passwordImage);
        passwordImageView.setFitWidth(45);
        passwordImageView.setFitHeight(45);
        passwordLabel.setGraphic(passwordImageView);
        passwordLabel.setStyle("-fx-effect: dropshadow(two-pass-box , rgba(0, 0, 0, 0.20), 10, 0.0 , 4, 5)");
    }}


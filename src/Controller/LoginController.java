package Controller;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller for LoginScreen.fxml
 */
public class LoginController {
    //References to our objects and fields create in SceneBuilder.
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;


    private Image userImage = new Image("file:res/img/user.png");
    private Image passwordImage = new Image("file:res/img/lock.png");

    /**
     * Attempt to log in the user by verifying that an account exists with the matching username
     * and password. If successful, get the logged in user's info and enter the home page.
     * Otherwise call {@link LoginController#loginAlertStyle()}.
     */
    @FXML
    protected void loginAction() {
        if (MainController.getInstance().loginUser(usernameField.getText(), passwordField.getText())) {
            System.out.println("User logged in.");
            SceneController.activate("Home");
            MainController.getInstance().refreshProductsPage();
        }
        else
            System.out.println("Incorrect username/password.");
            loginAlertStyle();
    }

    /**
     * If the ENTER key was pressed, call {@link LoginController#loginAction()}.
     * @param event KeyEvent used to determine what key was pressed
     */
    @FXML
    protected void enterKeyLogin(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER)
            loginAction();

    }

    /**
     * Clear the username and password text fields and set their prompt texts to an
     * incorrect-credentials warning in red.
     */
    protected void loginAlertStyle() {
        usernameField.clear();
        passwordField.clear();

        usernameField.setPromptText("INCORRECT USERNAME/PASSWORD");
        passwordField.setPromptText("INCORRECT USERNAME/PASSWORD");

        usernameField.setStyle("-fx-prompt-text-fill: rgba(255, 25, 29, 0.65)");
        passwordField.setStyle("-fx-prompt-text-fill: rgba(255, 25, 29, 0.65)");

    }

    /**
     * Exit the application.
     * @param event ActionEvent
     */
    @FXML
    protected void quitAction(ActionEvent event)    {
        Platform.exit();
    }

    /**
     * Initialize the LoginController when its FXML is loaded and style it.
     */
    @FXML
    void initialize() {
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'LoginScreen.fxml'.";

        usernameField.setStyle("-fx-effect: dropshadow(two-pass-box , rgba(0, 0, 0, 0.20), 10, 0.0 , 4, 5)");
        passwordField.setStyle("-fx-effect: dropshadow(two-pass-box , rgba(0, 0, 0, 0.20), 10, 0.0 , 4, 5)");

        //Username login img.
        ImageView userImageView = new ImageView(userImage);
        userImageView.setFitWidth(32);
        userImageView.setFitHeight(32);
        usernameLabel.setGraphic(userImageView);
        usernameLabel.setStyle("-fx-effect: dropshadow(two-pass-box , rgba(0, 0, 0, 0.20), 10, 0.0 , 4, 5)");

        //Password login img.
        ImageView passwordImageView = new ImageView(passwordImage);
        passwordImageView.setFitWidth(32);
        passwordImageView.setFitHeight(32);
        passwordLabel.setGraphic(passwordImageView);
        passwordLabel.setStyle("-fx-effect: dropshadow(two-pass-box , rgba(0, 0, 0, 0.20), 10, 0.0 , 4, 5)");
    }}


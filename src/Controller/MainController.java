package Controller;

import Exceptions.ErrorHandler;
import View.View;
import javafx.application.Application;

/**
 * This class acts as the interface between the GUI view and the data models.
 */
public class MainController {

    private static MainController instance = null;
    private final String SETTINGS_FILE_NAME = "settings";

    private MainController(){

    }

    /**
     * Following singleton pattern, this method will return the static instance of the
     * MainController class. If no instance yet exists, one will be created using the
     * MainController constructor, saved as a static variable, and returned.
     * @return MainController's static property instance, of type MainController
     */
    public static MainController getInstance(){
        if (instance == null)
            instance = new MainController();
        return instance;
    }

    public String getSettingsFileName() {
        return SETTINGS_FILE_NAME;
    }

    public static void main(String[] args) {
        DataController.getInstance();
        Application.launch(View.class,args);
    }
}
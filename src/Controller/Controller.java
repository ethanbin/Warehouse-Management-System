package Controller;

import java.sql.*;

import View.View;
import javafx.application.Application;

/**
 * This class acts as the interface between the GUI view and the data models.
 */
public class Controller {

    private static Controller instance = null;
    private final String SETTINGS_FILE_NAME = "settings";

    private Controller(){

    }

    public static Controller getInstance(){
        if (instance == null)
            instance = new Controller();
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
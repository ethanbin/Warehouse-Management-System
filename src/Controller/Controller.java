package Controller;

import java.sql.*;

import Exceptions.DataControllerException;
import View.View;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * This class acts as the interface between the GUI view and the data models.
 */
public class Controller {

    public static void main(String[] args) {
        DataController.getInstance();
        Application.launch(View.class,args);
    }
}
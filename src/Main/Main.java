package Main;

import View.View;
import javafx.application.Application;

/**
 * The entry point for the program.
 * Calls Application.Launch.
 * @see Application
 */
public class Main {
    public static void main(String[] args) {
        Application.launch(View.class,args);
    }
}

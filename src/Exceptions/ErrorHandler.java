package Exceptions;

import javafx.scene.control.Alert;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Class of static methods for handling errors and exceptions by alerting the
 * user or logging error info.
 */
public class ErrorHandler {
    private final static String LOG_LOCATION = "error_log.txt";

    private ErrorHandler(Exception e){ }

    /**
     * Record an exception in the error log file and exit application.
     * @param e exception to record in error log
     */
    public static void logCriticalError(Exception e){
        logError(e);
        System.exit(1);
    }

    /**
     * Record an exception in the error log file.
     * @param e exception to record in error log
     */
    public static void logError(Exception e){
        System.err.println(e);
        try (FileWriter writer = new FileWriter(LOG_LOCATION, true)){
            writer.write(String.format("%s%n", e.toString()));
        }
        catch (IOException IOE){
            System.err.println("Could not open log for writing.");
        }

    }

    /**
     * Record a string in the error log file.
     * @param errorMessage string message to record in error log
     */
    public static void logError(String errorMessage){
        System.err.println(errorMessage);
        try (FileWriter writer = new FileWriter(LOG_LOCATION, true)){
            writer.write(String.format("%s%n", errorMessage));
        }
        catch (IOException IOE){
            System.err.println("Could not open log for writing.");
        }

    }

    /**
     * Freeze the application and show a simple dialog box alerting the user of an error.
     * @param title title of dialog box
     * @param content text content of dialog box
     * @param header header of dialog box's content, or null if no header desired
     * @see Alert
     */
    //Pass 'null' for header if you do not want a header
    public static void errorDialog(String title, String content, String header )    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}

package Exceptions;

import javafx.scene.control.Alert;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ErrorHandler {
    private final static String LOG_LOCATION = "error_log.txt";
    private static List<Alert> dialogHold = new ArrayList<Alert>();

    private ErrorHandler(Exception e){ }

    public static void logCriticalError(Exception e){
        logError(e);
        System.exit(1);
    }

    public static void logError(Exception e){
        System.err.println(e);
        try (FileWriter writer = new FileWriter(LOG_LOCATION, true)){
            writer.write(String.format("%s%n", e.toString()));
        }
        catch (IOException IOE){
            System.err.println("Could not open log for writing.");
        }

    }

    public static void logError(String errorMessage){
        System.err.println(errorMessage);
        try (FileWriter writer = new FileWriter(LOG_LOCATION, true)){
            writer.write(String.format("%s%n", errorMessage));
        }
        catch (IOException IOE){
            System.err.println("Could not open log for writing.");
        }

    }

    //Pass 'null' for header if you do not want a header
    public static void errorDialog(String title, String content, String header )    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}

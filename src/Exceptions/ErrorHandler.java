package Exceptions;

import java.io.FileWriter;
import java.io.IOException;

public class ErrorHandler {
    private final static String LOG_LOCATION = "error_log.txt";

    private ErrorHandler(Exception e){ }

    public static void logCriticalError(Exception e){
        System.err.println(e);
        try (FileWriter writer = new FileWriter(LOG_LOCATION, true)){
            writer.write(String.format("%s%n", e.toString()));
        }
        catch (IOException IOE){
            System.err.println("Could not open log for writing.");
        }
        System.exit(1);
    }
}

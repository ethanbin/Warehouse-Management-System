package Exceptions;

import java.io.FileWriter;
import java.io.IOException;

public class ErrorLogger {
    private final static String LOG_LOCATION = "error_log.txt";
    private final static String BACKUP_LOG_LOCATION = "backup_error_log.txt";

    private ErrorLogger(Exception e){ }

    public static void logCriticalEError(Exception e){
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

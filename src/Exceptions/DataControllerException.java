package Exceptions;

/**
 * <P>An exception that provides information on a DataController errors.
 *
 * <P>Each <code>DataControllerException</code> provides information on the source of the error.
 */
public class DataControllerException extends Exception{
    // static publicly defined error codes
    /**
     * Static index for error message when the database fails.
     */
    public final static int	DATABASE_FAILED = 0;

    /**
     * Static index for error message when a prepared statement fails to prepare.
     */
    public final static int	PREPARED_STATEMENTS_FAILED = 1;

    /**
     * Static index for error message when a 0 or 1 is expected to act as a boolean value
     * but another value was received.
     */
    public final static int	EXPECTED_INTEGER_AS_BOOLEAN = 2;

    /**
     * Static publicly defined exception messages
     */
    public final static String[]  MESSAGE = {
            "The database failed to open for read/write",
            "The SQL statements failed to prepare",
            "A boolean value of 0 or 1 was expected, but a different number was received"
    } ;

    /**
     * The index of the current error message for the cause of this exception.
     */
    protected int errorCode;  // have this exception carry a errorCode as well as

    /**
     * Constructor that takes a string message and int errorCode
     * @param message string message
     * @param errorCode error code corresponding to one of the final error codes,
     *                  such as {@link DataControllerException#DATABASE_FAILED}
     */
    public DataControllerException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Return {@link DataControllerException#errorCode}.
     * @return errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Overridden toString which prints the message given in the constructor
     * and the error message for the given error code.
     * @return string describing reason for this exception
     */
    @Override
    public String toString() {
        //return "DataControllerException [errorCode=" + errorCode + ", toString()=" + super.toString() + " " + MESSAGE[errorCode] + "]\n";
        return getMessage() + " " + MESSAGE[errorCode];
    }
}

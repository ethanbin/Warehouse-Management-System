package Exceptions;

/**
 * <P>An exception that provides information on a DataController initialization
 * error or other errors.
 *
 * <P>Each <code>DataControllerException</code> provides information on the source of the error.
 */
public class DataControllerException extends Exception{
    // static publicly defined error codes
    public final static int	DATABASE_FAILED = 0;
    public final static int	PREPARED_STATEMENTS_FAILED = 1;
    public final static int	EXPECTED_BOOLEAN_AS_INTEGER = 2;

    /**
     * Static publicly defined exception messages
     */
    public final static String[]  MESSAGE = {
            "The database failed to open for read/write",
            "The SQL statements failed to prepare",
            "A boolean value of 0 or 1 was expected, but a different number was received"
    } ;

    protected int code;  // have this exception carry a code as well as

    // Constructors ...
    private  DataControllerException() {}

    // with thrower  message
    public DataControllerException(String m) {
        super(m);
    }

    // with thrower message and code
    public DataControllerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        //return "DataControllerException [code=" + code + ", toString()=" + super.toString() + " " + MESSAGE[code] + "]\n";
        return getMessage() + " " + MESSAGE[code];
    }
}

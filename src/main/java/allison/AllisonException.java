package allison;

/**
 * Represents an exception specific to the Allison application,
 * such as invalid commands or malformed input.
 */
public class AllisonException extends Exception {

    /**
     * Constructs an AllisonException with a generic invalid command message.
     */
    public AllisonException() {
        super("Error: Invalid command");
    }

    /**
     * Constructs an AllisonException with a description of the error
     * and the correct usage syntax.
     *
     * @param description A description of what went wrong.
     * @param correctSyntax The correct command syntax to show the user.
     */
    public AllisonException(String description, String correctSyntax) {
        super("Error: " + description + ". Correct Usage: " + correctSyntax);
    }
}
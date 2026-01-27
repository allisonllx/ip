public class AllisonException extends Exception {
    public AllisonException() {
        super("Error: Invalid command");
    }

    public AllisonException(String description, String correctSyntax) {
        super("Error: " + description + ". Correct Usage: " + correctSyntax);
    }
}

package allison;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses user input strings into executable commands and extracts
 * relevant arguments required by the application.
 */
public class Parser {

    /**
     * Constructs a Parser instance.
     */
    public Parser() {
    }

    /**
     * Returns the command type represented by the given user input.
     * Performs validation on command format and required arguments.
     *
     * @param command Full user input string.
     * @return Parsed Command enum.
     * @throws AllisonException If the command is invalid or malformed.
     */
    public Command parseCommand(String command) throws AllisonException {
        String trimmedCommand = command.trim();
        String[] parts = trimmedCommand.split(" ", 2);
        String keyword = parts[0].toLowerCase();
        switch (keyword) {
        case "bye":
            return Command.BYE;
        case "list":
            return Command.LIST;
        case "todo":
            if (parts.length < 2) {
                throw new AllisonException("Missing description in todo", "todo <description>");
            }
            return Command.TODO;
        case "deadline":
            if (!trimmedCommand.contains("/by")) {
                throw new AllisonException("Missing /by in deadline", "deadline <task> /by <time>");
            }
            if (parts.length < 2) {
                throw new AllisonException("Incomplete arguments", "deadline <task> /by <time>");
            }
            if (parts[1].startsWith("/by")) {
                throw new AllisonException("Missing description in deadline", "deadline <task> /by <time>");
            }
            if (parts[1].split("/by").length < 2) {
                throw new AllisonException("Missing due date in deadline", "deadline <task> /by <time>");
            }
            return Command.DEADLINE;
        case "event":
            if (!trimmedCommand.contains("/from") || !trimmedCommand.contains("/to")) {
                throw new AllisonException("Missing /from or /to in event", "event <desc> /from <start> /to <end>");
            }
            if (parts.length < 2) {
                throw new AllisonException("Incomplete arguments", "event <desc> /from <start> /to <end>");
            }
            if (parts[1].startsWith("/from")) {
                throw new AllisonException("Missing description in event", "event <desc> /from <start> /to <end>");
            }
            String[] fromSplitParts = parts[1].split("/from", 2);
            if (fromSplitParts[1].startsWith("/to")) {
                throw new AllisonException("Missing start date/time in event", "event <desc> /from <start> /to <end>");
            }
            String[] toSplitParts = fromSplitParts[1].split("/to", 2);
            if (toSplitParts[1].isEmpty()) {
                throw new AllisonException("Missing enc date/time in event", "event <desc> /from <start> /to <end>");
            }
            return Command.EVENT;
        case "mark":
            if (parts.length < 2) {
                throw new AllisonException("Missing task number", "mark <task number>");
            }
            try {
                int num = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw new AllisonException("Invalid input after 'mark'", "mark <task number>");
            }
            return Command.MARK;
        case "unmark":
            if (parts.length < 2) {
                throw new AllisonException("Missing task number", "unmark <task number>");
            }
            try {
                int num = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw new AllisonException("Invalid input after 'unmark'", "unmark <task number>");
            }
            return Command.UNMARK;
        case "delete":
            if (parts.length < 2) {
                throw new AllisonException("Missing task number", "delete <task number>");
            }
            try {
                int num = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw new AllisonException("Invalid input after 'delete'", "delete <task number>");
            }
            return Command.DELETE;
        default:
            throw new AllisonException();
        }
    }

    /**
     * Returns the task number specified in the given command.
     *
     * @param command Full user input string.
     * @return Parsed task number.
     */
    public int parseTaskNum(String command) {
        String trimmedCommand = command.trim();
        String[] parts = trimmedCommand.split(" ", 2);
        return Integer.parseInt(parts[1]);
    }

    /**
     * Returns the description of a todo task.
     *
     * @param command Full user input string.
     * @return Todo description.
     */
    public String parseTodoDesc(String command) {
        String trimmedCommand = command.trim();
        String[] parts = trimmedCommand.split(" ", 2);
        return parts[1].trim();
    }

    /**
     * Returns the description portion of a deadline task.
     *
     * @param command Full user input string.
     * @return Deadline task description.
     */
    public String parseDeadlineDesc(String command) {
        String trimmedCommand = command.trim();
        String[] parts = trimmedCommand.split(" ", 2);
        String[] bySplitParts = parts[1].split("/by");
        return bySplitParts[0].trim();
    }

    /**
     * Returns the arguments associated with a deadline task.
     *
     * @param command Full user input string.
     * @return List containing the deadline date/time.
     */
    public ArrayList<String> parseDeadlineArgs(String command) {
        String trimmedCommand = command.trim();
        String[] parts = trimmedCommand.split(" ", 2);
        String[] bySplitParts = parts[1].split("/by");
        return new ArrayList<>(List.of(bySplitParts[1].trim()));
    }

    /**
     * Returns the description portion of an event task.
     *
     * @param command Full user input string.
     * @return Event task description.
     */
    public String parseEventDesc(String command) {
        String trimmedCommand = command.trim();
        String[] parts = trimmedCommand.split(" ", 2);
        String[] fromSplitParts = parts[1].split("/from");
        return fromSplitParts[0].trim();
    }

    /**
     * Returns the arguments associated with an event task.
     *
     * @param command Full user input string.
     * @return List containing event start and end date/time.
     */
    public ArrayList<String> parseEventArgs(String command) {
        String trimmedCommand = command.trim();
        String[] parts = trimmedCommand.split(" ", 2);
        String[] fromSplitParts = parts[1].split("/from");
        String[] toSplitParts = fromSplitParts[1].split("/to");
        return new ArrayList<>(List.of(toSplitParts[0].trim(), toSplitParts[1].trim()));
    }
}

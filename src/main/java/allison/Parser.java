package allison;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses user input strings into executable commands and extracts
 * relevant arguments required by the application.
 */
public class Parser {
    private static final String BYE_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String DELETE_COMMAND = "delete";
    private static final String FIND_COMMAND = "find";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String BY_KEYWORD = "/by";
    private static final String FROM_KEYWORD = "/from";
    private static final String TO_KEYWORD = "/to";

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
        case BYE_COMMAND:
            return Command.BYE;
        case LIST_COMMAND:
            return Command.LIST;
        case TODO_COMMAND:
            if (parts.length < 2) {
                throw new AllisonException("Missing description in todo", "todo <description>");
            }
            return Command.TODO;
        case DEADLINE_COMMAND:
            if (!trimmedCommand.contains(BY_KEYWORD)) {
                throw new AllisonException("Missing /by in deadline", "deadline <task> /by <time>");
            }
            if (parts.length < 2) {
                throw new AllisonException("Incomplete arguments", "deadline <task> /by <time>");
            }
            if (parts[1].startsWith(BY_KEYWORD)) {
                throw new AllisonException("Missing description in deadline", "deadline <task> /by <time>");
            }
            if (parts[1].split(BY_KEYWORD).length < 2) {
                throw new AllisonException("Missing due date in deadline", "deadline <task> /by <time>");
            }
            return Command.DEADLINE;
        case EVENT_COMMAND:
            if (!trimmedCommand.contains(FROM_KEYWORD) || !trimmedCommand.contains(TO_KEYWORD)) {
                throw new AllisonException("Missing /from or /to in event", "event <desc> /from <start> /to <end>");
            }
            if (parts.length < 2) {
                throw new AllisonException("Incomplete arguments", "event <desc> /from <start> /to <end>");
            }
            if (parts[1].startsWith(FROM_KEYWORD)) {
                throw new AllisonException("Missing description in event", "event <desc> /from <start> /to <end>");
            }
            String[] fromSplitParts = parts[1].split(FROM_KEYWORD, 2);
            if (fromSplitParts[1].startsWith(TO_KEYWORD)) {
                throw new AllisonException("Missing start date/time in event", "event <desc> /from <start> /to <end>");
            }
            String[] toSplitParts = fromSplitParts[1].split(TO_KEYWORD, 2);
            if (toSplitParts[1].isEmpty()) {
                throw new AllisonException("Missing enc date/time in event", "event <desc> /from <start> /to <end>");
            }
            return Command.EVENT;
        case MARK_COMMAND:
            if (parts.length < 2) {
                throw new AllisonException("Missing task number", "mark <task number>");
            }
            try {
                int num = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw new AllisonException("Invalid input after 'mark'", "mark <task number>");
            }
            return Command.MARK;
        case UNMARK_COMMAND:
            if (parts.length < 2) {
                throw new AllisonException("Missing task number", "unmark <task number>");
            }
            try {
                int num = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw new AllisonException("Invalid input after 'unmark'", "unmark <task number>");
            }
            return Command.UNMARK;
        case DELETE_COMMAND:
            if (parts.length < 2) {
                throw new AllisonException("Missing task number", "delete <task number>");
            }
            try {
                int num = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw new AllisonException("Invalid input after 'delete'", "delete <task number>");
            }
            return Command.DELETE;
        case FIND_COMMAND:
            if (parts.length < 2) {
                throw new AllisonException("Missing keyword", "find <keyword>");
            }
            return Command.FIND;
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

        assert parts[0].trim().equals(MARK_COMMAND)
                || parts[0].trim().equals(UNMARK_COMMAND)
                || parts[0].trim().equals(DELETE_COMMAND);
        assert parts.length > 1 && parts[1].matches("\\d+");

        String taskNum = parts[1];
        return Integer.parseInt(taskNum);
    }

    public String parseFindKeyword(String command) {
        String trimmedCommand = command.trim();
        String[] parts = trimmedCommand.split(" ", 2);

        assert parts[0].trim().equals(FIND_COMMAND);
        assert parts.length > 1;

        String keyword = parts[1];
        return keyword.trim();
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

        assert parts[0].trim().equals(TODO_COMMAND);
        assert parts.length > 1;

        String description = parts[1];
        return description.trim();
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

        assert parts[0].trim().equals(DEADLINE_COMMAND);
        assert parts[0].trim().contains(BY_KEYWORD);
        assert parts.length > 1;
        String[] bySplitParts = parts[1].split(BY_KEYWORD);

        String description = bySplitParts[0];
        return description.trim();
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

        assert parts[0].trim().equals(DEADLINE_COMMAND);
        assert parts.length > 1;
        assert parts[1].trim().contains(BY_KEYWORD);
        String[] bySplitParts = parts[1].split(BY_KEYWORD);

        String byArgs = bySplitParts[1].trim();
        return new ArrayList<>(List.of(byArgs));
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

        assert parts[0].trim().equals(EVENT_COMMAND);
        assert parts.length > 1;
        assert parts[1].trim().contains(FROM_KEYWORD);
        String[] fromSplitParts = parts[1].split(FROM_KEYWORD);

        String description = fromSplitParts[0];
        return description.trim();
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

        assert parts[0].trim().equals(EVENT_COMMAND);
        assert parts.length > 1;
        assert parts[1].trim().contains(FROM_KEYWORD);
        assert parts[1].trim().contains(TO_KEYWORD);

        String[] fromSplitParts = parts[1].split(FROM_KEYWORD);
        String[] toSplitParts = fromSplitParts[1].split(TO_KEYWORD);

        String fromArgs = toSplitParts[0].trim();
        String toArgs = toSplitParts[1].trim();
        return new ArrayList<>(List.of(fromArgs, toArgs));
    }
}

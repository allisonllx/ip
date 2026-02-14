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
    private static final String HELP_COMMAND = "help";
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
        case HELP_COMMAND:
            return Command.HELP;
        case TODO_COMMAND:
            validateHasArguments(parts, "Missing description in todo", "todo <description>");
            return Command.TODO;
        case DEADLINE_COMMAND:
            validateDeadlineCommand(trimmedCommand, parts);
            return Command.DEADLINE;
        case EVENT_COMMAND:
            validateEventCommand(trimmedCommand, parts);
            return Command.EVENT;
        case MARK_COMMAND:
            validateTaskNumCommand(parts, MARK_COMMAND);
            return Command.MARK;
        case UNMARK_COMMAND:
            validateTaskNumCommand(parts, UNMARK_COMMAND);
            return Command.UNMARK;
        case DELETE_COMMAND:
            validateTaskNumCommand(parts, DELETE_COMMAND);
            return Command.DELETE;
        case FIND_COMMAND:
            validateHasArguments(parts, "Missing keyword", "find <keyword>");
            return Command.FIND;
        default:
            throw new AllisonException();
        }
    }

    /**
     * Validates that the command parts contain arguments beyond the keyword.
     *
     * @param parts Split command parts.
     * @param errorMessage Error message if arguments are missing.
     * @param correctUsage Correct usage string shown to the user.
     * @throws AllisonException If arguments are missing.
     */
    private void validateHasArguments(String[] parts, String errorMessage,
            String correctUsage) throws AllisonException {
        if (parts.length < 2) {
            throw new AllisonException(errorMessage, correctUsage);
        }
    }

    /**
     * Validates that a task-number command (mark, unmark, delete) has a valid numeric argument.
     *
     * @param parts Split command parts.
     * @param commandName The command name (e.g. "mark", "unmark", "delete").
     * @throws AllisonException If the task number is missing or not a valid integer.
     */
    private void validateTaskNumCommand(String[] parts, String commandName) throws AllisonException {
        String usage = commandName + " <task number>";
        if (parts.length < 2) {
            throw new AllisonException("Missing task number", usage);
        }
        try {
            Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new AllisonException("Invalid input after '" + commandName + "'", usage);
        }
    }

    /**
     * Validates that a deadline command has a description and a /by date.
     *
     * @param trimmedCommand The full trimmed command string.
     * @param parts Split command parts.
     * @throws AllisonException If the deadline command is malformed.
     */
    private void validateDeadlineCommand(String trimmedCommand, String[] parts) throws AllisonException {
        String usage = "deadline <task> /by <time>";
        if (!trimmedCommand.contains(BY_KEYWORD)) {
            throw new AllisonException("Missing /by in deadline", usage);
        }
        if (parts.length < 2) {
            throw new AllisonException("Incomplete arguments", usage);
        }
        if (parts[1].startsWith(BY_KEYWORD)) {
            throw new AllisonException("Missing description in deadline", usage);
        }
        if (parts[1].split(BY_KEYWORD).length < 2) {
            throw new AllisonException("Missing due date in deadline", usage);
        }
    }

    /**
     * Validates that an event command has a description, /from start time, and /to end time.
     *
     * @param trimmedCommand The full trimmed command string.
     * @param parts Split command parts.
     * @throws AllisonException If the event command is malformed.
     */
    private void validateEventCommand(String trimmedCommand, String[] parts) throws AllisonException {
        String usage = "event <desc> /from <start> /to <end>";
        if (!trimmedCommand.contains(FROM_KEYWORD) || !trimmedCommand.contains(TO_KEYWORD)) {
            throw new AllisonException("Missing /from or /to in event", usage);
        }
        if (parts.length < 2) {
            throw new AllisonException("Incomplete arguments", usage);
        }
        if (parts[1].startsWith(FROM_KEYWORD)) {
            throw new AllisonException("Missing description in event", usage);
        }
        String[] fromSplitParts = parts[1].split(FROM_KEYWORD, 2);
        if (fromSplitParts[1].startsWith(TO_KEYWORD)) {
            throw new AllisonException("Missing start date/time in event", usage);
        }
        String[] toSplitParts = fromSplitParts[1].split(TO_KEYWORD, 2);
        if (toSplitParts[1].isEmpty()) {
            throw new AllisonException("Missing end date/time in event", usage);
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

    /**
     * Returns the keyword to search for from a find command.
     *
     * @param command Full user input string.
     * @return The search keyword.
     */
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
        assert parts.length > 1 && parts[1].contains(BY_KEYWORD);
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

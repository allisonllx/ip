package allison;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    private final Parser parser = new Parser();

    // ==================== parseCommand tests ====================

    @Test
    public void parseCommand_bye_returnsByeCommand() throws AllisonException {
        assertEquals(Command.BYE, parser.parseCommand("bye"));
    }

    @Test
    public void parseCommand_list_returnsListCommand() throws AllisonException {
        assertEquals(Command.LIST, parser.parseCommand("list"));
    }

    @Test
    public void parseCommand_help_returnsHelpCommand() throws AllisonException {
        assertEquals(Command.HELP, parser.parseCommand("help"));
    }

    @Test
    public void parseCommand_validTodo_returnsTodoCommand() throws AllisonException {
        assertEquals(Command.TODO, parser.parseCommand("todo read book"));
    }

    @Test
    public void parseCommand_todoMissingDescription_exceptionThrown() {
        AllisonException exception = assertThrows(AllisonException.class, () -> {
            parser.parseCommand("todo");
        });
        assertEquals("Error: Missing description in todo. Correct Usage: todo <description>",
                exception.getMessage());
    }

    @Test
    public void parseCommand_validMark_returnsMarkCommand() throws AllisonException {
        assertEquals(Command.MARK, parser.parseCommand("mark 1"));
    }

    @Test
    public void parseCommand_markMissingNumber_exceptionThrown() {
        AllisonException exception = assertThrows(AllisonException.class, () -> {
            parser.parseCommand("mark");
        });
        assertEquals("Error: Missing task number. Correct Usage: mark <task number>",
                exception.getMessage());
    }

    @Test
    public void parseCommand_markInvalidNumber_exceptionThrown() {
        AllisonException exception = assertThrows(AllisonException.class, () -> {
            parser.parseCommand("mark abc");
        });
        assertEquals("Error: Invalid input after 'mark'. Correct Usage: mark <task number>",
                exception.getMessage());
    }

    @Test
    public void parseCommand_validUnmark_returnsUnmarkCommand() throws AllisonException {
        assertEquals(Command.UNMARK, parser.parseCommand("unmark 1"));
    }

    @Test
    public void parseCommand_unmarkMissingNumber_exceptionThrown() {
        assertThrows(AllisonException.class, () -> parser.parseCommand("unmark"));
    }

    @Test
    public void parseCommand_validDelete_returnsDeleteCommand() throws AllisonException {
        assertEquals(Command.DELETE, parser.parseCommand("delete 1"));
    }

    @Test
    public void parseCommand_deleteMissingNumber_exceptionThrown() {
        assertThrows(AllisonException.class, () -> parser.parseCommand("delete"));
    }

    @Test
    public void parseCommand_deleteInvalidNumber_exceptionThrown() {
        assertThrows(AllisonException.class, () -> parser.parseCommand("delete xyz"));
    }

    @Test
    public void parseCommand_validFind_returnsFindCommand() throws AllisonException {
        assertEquals(Command.FIND, parser.parseCommand("find book"));
    }

    @Test
    public void parseCommand_findMissingKeyword_exceptionThrown() {
        assertThrows(AllisonException.class, () -> parser.parseCommand("find"));
    }

    @Test
    public void parseCommand_validDeadline_returnsDeadlineCommand() throws AllisonException {
        assertEquals(Command.DEADLINE, parser.parseCommand("deadline read /by 2024-01-01T10:00"));
    }

    @Test
    public void parseCommand_deadlineMissingBy_exceptionThrown() {
        assertThrows(AllisonException.class, () -> parser.parseCommand("deadline read"));
    }

    @Test
    public void parseCommand_deadlineMissingDescription_exceptionThrown() {
        assertThrows(AllisonException.class, () -> parser.parseCommand("deadline /by 2024-01-01T10:00"));
    }

    @Test
    public void parseCommand_validEvent_returnsEventCommand() throws AllisonException {
        assertEquals(Command.EVENT, parser.parseCommand("event meeting /from 2024-01-01T10:00 /to 2024-01-01T12:00"));
    }

    @Test
    public void parseCommand_eventMissingFrom_exceptionThrown() {
        assertThrows(AllisonException.class, () ->
                parser.parseCommand("event meeting /to 2024-01-01T12:00"));
    }

    @Test
    public void parseCommand_eventMissingTo_exceptionThrown() {
        assertThrows(AllisonException.class, () ->
                parser.parseCommand("event meeting /from 2024-01-01T10:00"));
    }

    @Test
    public void parseCommand_eventMissingDescription_exceptionThrown() {
        assertThrows(AllisonException.class, () ->
                parser.parseCommand("event /from 2024-01-01T10:00 /to 2024-01-01T12:00"));
    }

    @Test
    public void parseCommand_invalidCommand_exceptionThrown() {
        assertThrows(AllisonException.class, () -> parser.parseCommand("invalid"));
    }

    @Test
    public void parseCommand_emptyInput_exceptionThrown() {
        assertThrows(AllisonException.class, () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_caseInsensitive_success() throws AllisonException {
        assertEquals(Command.BYE, parser.parseCommand("BYE"));
        assertEquals(Command.LIST, parser.parseCommand("LIST"));
    }

    // ==================== parseTaskNum tests ====================

    @Test
    public void parseTaskNum_validMarkCommand_success() {
        assertEquals(2, parser.parseTaskNum("mark 2"));
    }

    @Test
    public void parseTaskNum_validUnmarkCommand_success() {
        assertEquals(5, parser.parseTaskNum("unmark 5"));
    }

    @Test
    public void parseTaskNum_validDeleteCommand_success() {
        assertEquals(1, parser.parseTaskNum("delete 1"));
    }

    // ==================== parseTodoDesc tests ====================

    @Test
    public void parseTodoDesc_validTodo_success() {
        assertEquals("read book", parser.parseTodoDesc("todo read book"));
    }

    @Test
    public void parseTodoDesc_validTodoWithExtraSpace_success() {
        assertEquals("read book", parser.parseTodoDesc("todo   read book   "));
    }

    // ==================== parseFindKeyword tests ====================

    @Test
    public void parseFindKeyword_validFind_success() {
        assertEquals("book", parser.parseFindKeyword("find book"));
    }

    @Test
    public void parseFindKeyword_withExtraSpaces_success() {
        assertEquals("read book", parser.parseFindKeyword("find  read book "));
    }

    // ==================== parseDeadlineDesc and parseDeadlineArgs tests ====================

    @Test
    public void parseDeadlineDesc_validDeadline_success() {
        assertEquals("read book", parser.parseDeadlineDesc("deadline read book /by 2024-01-01T10:00"));
    }

    @Test
    public void parseDeadlineArgs_validDeadline_success() {
        ArrayList<String> args = parser.parseDeadlineArgs("deadline read book /by 2024-01-01T10:00");
        assertEquals(1, args.size());
        assertEquals("2024-01-01T10:00", args.get(0));
    }

    // ==================== parseEventDesc and parseEventArgs tests ====================

    @Test
    public void parseEventDesc_validEvent_success() {
        assertEquals("project meeting",
                parser.parseEventDesc("event project meeting /from 2024-01-01T10:00 /to 2024-01-01T12:00"));
    }

    @Test
    public void parseEventArgs_validEvent_success() {
        ArrayList<String> args = parser.parseEventArgs(
                "event project meeting /from 2024-01-01T10:00 /to 2024-01-01T12:00");
        assertEquals(2, args.size());
        assertEquals("2024-01-01T10:00", args.get(0));
        assertEquals("2024-01-01T12:00", args.get(1));
    }
}

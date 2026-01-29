package allison;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    Parser parser = new Parser();

    @Test
    public void parseTaskNum_validTodo_success() {
        assertEquals(2, parser.parseTaskNum("mark 2"));
    }

    @Test
    public void parseTodoDesc_validTodo_success() {
        assertEquals("read book", parser.parseTodoDesc("todo read book"));
    }

    @Test
    public void parseTodoDesc_validTodoWithExtraSpace_success() {
        assertEquals("read book", parser.parseTodoDesc("todo   read book   "));
    }
}

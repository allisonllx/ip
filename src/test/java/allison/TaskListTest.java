package allison;

import allison.task.Task;
import allison.task.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        ArrayList<Task> tasks = new ArrayList<>(
                List.of(new Todo("read book"), new Todo("complete homework"))
        );
        taskList = new TaskList(tasks);
    }

    @Test
    public void addTask_addsToList_success() {
        taskList.addTask(new Todo("new task"));
        assertEquals(3, taskList.getNumTasks());
    }

    @Test
    public void getNumTasks_returnsCorrectSize() {
        assertEquals(2, taskList.getNumTasks());
    }

    @Test
    public void removeTask_validTaskNum_success() throws AllisonException {
        Task removed = taskList.removeTask(2);
        assertEquals(new Todo("complete homework"), removed);
        assertEquals(1, taskList.getNumTasks());
    }

    @Test
    public void removeTask_firstTask_success() throws AllisonException {
        Task removed = taskList.removeTask(1);
        assertEquals(new Todo("read book"), removed);
        assertEquals(1, taskList.getNumTasks());
    }

    @Test
    public void removeTask_taskNumZero_exceptionThrown() {
        AllisonException exception = assertThrows(AllisonException.class, () -> {
            taskList.removeTask(0);
        });
        assertEquals("Error: Task number out of range. Correct Usage: delete <task number>",
                exception.getMessage());
    }

    @Test
    public void removeTask_taskNumNegative_exceptionThrown() {
        AllisonException exception = assertThrows(AllisonException.class, () -> {
            taskList.removeTask(-1);
        });
        assertEquals("Error: Task number out of range. Correct Usage: delete <task number>",
                exception.getMessage());
    }

    @Test
    public void removeTask_taskNumExceedsSize_exceptionThrown() {
        AllisonException exception = assertThrows(AllisonException.class, () -> {
            taskList.removeTask(3);
        });
        assertEquals("Error: Task number out of range. Correct Usage: delete <task number>",
                exception.getMessage());
    }

    @Test
    public void markTask_validTaskNum_success() throws AllisonException {
        Task marked = taskList.markTask(1);
        assertEquals("[T][X] read book", marked.toString());
    }

    @Test
    public void markTask_taskNumZero_exceptionThrown() {
        AllisonException exception = assertThrows(AllisonException.class, () -> {
            taskList.markTask(0);
        });
        assertEquals("Error: Task number out of range. Correct Usage: mark <task number>",
                exception.getMessage());
    }

    @Test
    public void markTask_taskNumExceedsSize_exceptionThrown() {
        AllisonException exception = assertThrows(AllisonException.class, () -> {
            taskList.markTask(5);
        });
        assertEquals("Error: Task number out of range. Correct Usage: mark <task number>",
                exception.getMessage());
    }

    @Test
    public void unmarkTask_validTaskNum_success() throws AllisonException {
        taskList.markTask(1);
        Task unmarked = taskList.unmarkTask(1);
        assertEquals("[T][ ] read book", unmarked.toString());
    }

    @Test
    public void unmarkTask_taskNumZero_exceptionThrown() {
        AllisonException exception = assertThrows(AllisonException.class, () -> {
            taskList.unmarkTask(0);
        });
        assertEquals("Error: Task number out of range. Correct Usage: unmark <task number>",
                exception.getMessage());
    }

    @Test
    public void listTasks_multipleTasks_formattedCorrectly() {
        String result = taskList.listTasks().toString();
        assertEquals("1. [T][ ] read book\n2. [T][ ] complete homework", result);
    }

    @Test
    public void listTasks_emptyList_returnsEmptyString() {
        TaskList emptyList = new TaskList(new ArrayList<>());
        assertEquals("", emptyList.listTasks().toString());
    }

    @Test
    public void findTasks_matchingKeyword_returnsMatches() {
        ArrayList<Task> results = taskList.findTasks("book");
        assertEquals(1, results.size());
        assertEquals(new Todo("read book"), results.get(0));
    }

    @Test
    public void findTasks_caseInsensitive_returnsMatches() {
        ArrayList<Task> results = taskList.findTasks("BOOK");
        assertEquals(1, results.size());
    }

    @Test
    public void findTasks_noMatches_returnsEmptyList() {
        ArrayList<Task> results = taskList.findTasks("nonexistent");
        assertTrue(results.isEmpty());
    }

    @Test
    public void findTasks_partialMatch_returnsMatches() {
        ArrayList<Task> results = taskList.findTasks("complete");
        assertEquals(1, results.size());
        assertEquals(new Todo("complete homework"), results.get(0));
    }
}

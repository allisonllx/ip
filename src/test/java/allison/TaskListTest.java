package allison;

import allison.task.Task;
import allison.task.Todo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {
    ArrayList<Task> tasks = new ArrayList<>(
            List.of(new Todo("read book"), new Todo("complete homework"))
    );

    @Test
    public void removeTask_validTaskNum_success() throws AllisonException {
        TaskList taskList = new TaskList(tasks);
        assertEquals(new Todo("complete homework"), taskList.removeTask(2));
    }

    @Test
    public void removeTask_taskNumOutOfBounds_exceptionThrown() throws AllisonException {
        TaskList taskList = new TaskList(tasks);
        AllisonException exception = assertThrows(AllisonException.class, () -> {
            taskList.removeTask(-1);
        });
        assertEquals("Error: taskNum out of range. Correct Usage: mark <task number>",
                exception.getMessage());
    }
}

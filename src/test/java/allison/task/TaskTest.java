package allison.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {

    // ==================== Task base class tests ====================

    @Test
    public void task_newTask_isNotDone() {
        Task task = new Todo("test");
        assertEquals("[ ]", "[" + task.getStatusIcon() + "]");
    }

    @Test
    public void task_markAsDone_statusIconIsX() {
        Task task = new Todo("test");
        task.markAsDone();
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void task_markAsUndone_statusIconIsSpace() {
        Task task = new Todo("test");
        task.markAsDone();
        task.markAsUndone();
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void task_setIsDone_updatesStatus() {
        Task task = new Todo("test");
        task.setIsDone(true);
        assertEquals("X", task.getStatusIcon());
        task.setIsDone(false);
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void task_getDescription_returnsDescription() {
        Task task = new Todo("read book");
        assertEquals("read book", task.getDescription());
    }

    @Test
    public void task_equals_sameTaskReturnsTrue() {
        Task task1 = new Todo("read book");
        Task task2 = new Todo("read book");
        assertEquals(task1, task2);
    }

    @Test
    public void task_equals_differentDescriptionReturnsFalse() {
        Task task1 = new Todo("read book");
        Task task2 = new Todo("write code");
        assertNotEquals(task1, task2);
    }

    @Test
    public void task_equals_differentDoneStatusReturnsFalse() {
        Task task1 = new Todo("read book");
        Task task2 = new Todo("read book");
        task2.markAsDone();
        assertNotEquals(task1, task2);
    }

    @Test
    public void task_equals_nullReturnsFalse() {
        Task task = new Todo("read book");
        assertNotEquals(null, task);
    }

    @Test
    public void task_hashCode_equalObjectsHaveSameHashCode() {
        Task task1 = new Todo("read book");
        Task task2 = new Todo("read book");
        assertEquals(task1.hashCode(), task2.hashCode());
    }

    // ==================== Todo tests ====================

    @Test
    public void todo_toString_formattedCorrectly() {
        Todo todo = new Todo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void todo_toStringDone_formattedCorrectly() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertEquals("[T][X] read book", todo.toString());
    }

    @Test
    public void todo_toFileString_formattedCorrectly() {
        Todo todo = new Todo("read book");
        assertEquals("T|0|read book", todo.toFileString());
    }

    @Test
    public void todo_toFileStringDone_formattedCorrectly() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertEquals("T|1|read book", todo.toFileString());
    }

    // ==================== Deadline tests ====================

    @Test
    public void deadline_toString_containsDescription() {
        LocalDateTime dueDate = LocalDateTime.of(2024, 6, 15, 14, 30);
        Deadline deadline = new Deadline("submit report", dueDate);
        String result = deadline.toString();
        assertTrue(result.startsWith("[D][ ] submit report (by:"));
    }

    @Test
    public void deadline_toFileString_formattedCorrectly() {
        LocalDateTime dueDate = LocalDateTime.of(2024, 6, 15, 14, 30);
        Deadline deadline = new Deadline("submit report", dueDate);
        assertEquals("D|0|submit report|2024-06-15T14:30", deadline.toFileString());
    }

    @Test
    public void deadline_toFileStringDone_formattedCorrectly() {
        LocalDateTime dueDate = LocalDateTime.of(2024, 6, 15, 14, 30);
        Deadline deadline = new Deadline("submit report", dueDate);
        deadline.markAsDone();
        assertEquals("D|1|submit report|2024-06-15T14:30", deadline.toFileString());
    }

    // ==================== Event tests ====================

    @Test
    public void event_toString_containsDescription() {
        LocalDateTime start = LocalDateTime.of(2024, 6, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 15, 12, 0);
        Event event = new Event("meeting", start, end);
        String result = event.toString();
        assertTrue(result.startsWith("[E][ ] meeting (from:"));
        assertTrue(result.contains("to:"));
    }

    @Test
    public void event_toFileString_formattedCorrectly() {
        LocalDateTime start = LocalDateTime.of(2024, 6, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 15, 12, 0);
        Event event = new Event("meeting", start, end);
        assertEquals("E|0|meeting|2024-06-15T10:00|2024-06-15T12:00", event.toFileString());
    }

    @Test
    public void event_toFileStringDone_formattedCorrectly() {
        LocalDateTime start = LocalDateTime.of(2024, 6, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 15, 12, 0);
        Event event = new Event("meeting", start, end);
        event.markAsDone();
        assertEquals("E|1|meeting|2024-06-15T10:00|2024-06-15T12:00", event.toFileString());
    }

    @Test
    public void event_toFileString_prefixIsE_notD() {
        LocalDateTime start = LocalDateTime.of(2024, 6, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 15, 12, 0);
        Event event = new Event("meeting", start, end);
        assertTrue(event.toFileString().startsWith("E|"));
        assertFalse(event.toFileString().startsWith("D|"));
    }
}

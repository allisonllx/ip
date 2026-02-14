package allison;

import allison.task.Deadline;
import allison.task.Event;
import allison.task.Task;
import allison.task.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {

    // ==================== parseTask tests ====================

    @Test
    public void parseTask_validTodo_success() {
        Storage storage = new Storage("data/test.txt");
        Task task = storage.parseTask("T|0|read book");
        assertEquals("[T][ ] read book", task.toString());
    }

    @Test
    public void parseTask_validTodoDone_success() {
        Storage storage = new Storage("data/test.txt");
        Task task = storage.parseTask("T|1|read book");
        assertEquals("[T][X] read book", task.toString());
    }

    @Test
    public void parseTask_validDeadline_success() {
        Storage storage = new Storage("data/test.txt");
        Task task = storage.parseTask("D|0|read book|2024-01-15T10:00");
        assertTrue(task instanceof Deadline);
        assertTrue(task.toString().contains("[D]"));
        assertTrue(task.toString().contains("read book"));
    }

    @Test
    public void parseTask_validEvent_success() {
        Storage storage = new Storage("data/test.txt");
        Task task = storage.parseTask("E|0|meeting|2024-01-15T10:00|2024-01-15T12:00");
        assertTrue(task instanceof Event);
        assertTrue(task.toString().contains("[E]"));
        assertTrue(task.toString().contains("meeting"));
    }

    @Test
    public void parseTask_invalidType_returnsNull() {
        Storage storage = new Storage("data/test.txt");
        Task task = storage.parseTask("X|0|invalid task");
        assertNull(task);
    }

    @Test
    public void parseTask_invalidDeadlineDate_returnsNull() {
        Storage storage = new Storage("data/test.txt");
        Task task = storage.parseTask("D|0|read book|not-a-date");
        assertNull(task);
    }

    @Test
    public void parseTask_invalidEventDate_returnsNull() {
        Storage storage = new Storage("data/test.txt");
        Task task = storage.parseTask("E|0|meeting|not-a-date|also-not-a-date");
        assertNull(task);
    }

    // ==================== load and save round-trip tests ====================

    @Test
    public void saveAndLoad_todoTasks_roundTrip(@TempDir Path tempDir) {
        String filePath = tempDir.resolve("test.txt").toString();
        Storage storage = new Storage(filePath);

        ArrayList<Task> tasksToSave = new ArrayList<>(List.of(
                new Todo("read book"),
                new Todo("write code")
        ));
        tasksToSave.get(0).setIsDone(true);

        storage.saveTasks(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(2, loadedTasks.size());
        assertEquals("[T][X] read book", loadedTasks.get(0).toString());
        assertEquals("[T][ ] write code", loadedTasks.get(1).toString());
    }

    @Test
    public void saveAndLoad_deadlineTasks_roundTrip(@TempDir Path tempDir) {
        String filePath = tempDir.resolve("test.txt").toString();
        Storage storage = new Storage(filePath);

        LocalDateTime dueDate = LocalDateTime.of(2024, 1, 15, 10, 0);
        ArrayList<Task> tasksToSave = new ArrayList<>(List.of(
                new Deadline("submit report", dueDate)
        ));

        storage.saveTasks(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(1, loadedTasks.size());
        assertTrue(loadedTasks.get(0) instanceof Deadline);
        assertTrue(loadedTasks.get(0).toString().contains("submit report"));
    }

    @Test
    public void saveAndLoad_eventTasks_roundTrip(@TempDir Path tempDir) {
        String filePath = tempDir.resolve("test.txt").toString();
        Storage storage = new Storage(filePath);

        LocalDateTime start = LocalDateTime.of(2024, 1, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 1, 15, 12, 0);
        ArrayList<Task> tasksToSave = new ArrayList<>(List.of(
                new Event("team meeting", start, end)
        ));

        storage.saveTasks(tasksToSave);
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(1, loadedTasks.size());
        assertTrue(loadedTasks.get(0) instanceof Event);
        assertTrue(loadedTasks.get(0).toString().contains("team meeting"));
    }

    @Test
    public void load_fileDoesNotExist_returnsEmptyList(@TempDir Path tempDir) {
        String filePath = tempDir.resolve("nonexistent.txt").toString();
        Storage storage = new Storage(filePath);
        ArrayList<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void load_emptyFile_returnsEmptyList(@TempDir Path tempDir) throws IOException {
        File file = tempDir.resolve("empty.txt").toFile();
        file.createNewFile();
        Storage storage = new Storage(file.getAbsolutePath());
        ArrayList<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void save_createsParentDirectory(@TempDir Path tempDir) {
        String filePath = tempDir.resolve("subdir/test.txt").toString();
        Storage storage = new Storage(filePath);
        storage.saveTasks(new ArrayList<>(List.of(new Todo("test"))));
        assertTrue(new File(filePath).exists());
    }

    // ==================== toFileString format tests ====================

    @Test
    public void parseTask_todoFileString_matchesTodo() {
        Storage storage = new Storage("data/test.txt");
        Todo todo = new Todo("read book");
        Task parsed = storage.parseTask(todo.toFileString());
        assertEquals(todo.toString(), parsed.toString());
    }

    @Test
    public void parseTask_deadlineFileString_matchesDeadline() {
        Storage storage = new Storage("data/test.txt");
        LocalDateTime dueDate = LocalDateTime.of(2024, 6, 15, 14, 30);
        Deadline deadline = new Deadline("submit report", dueDate);
        Task parsed = storage.parseTask(deadline.toFileString());
        assertEquals(deadline.toString(), parsed.toString());
    }

    @Test
    public void parseTask_eventFileString_matchesEvent() {
        Storage storage = new Storage("data/test.txt");
        LocalDateTime start = LocalDateTime.of(2024, 6, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 15, 12, 0);
        Event event = new Event("meeting", start, end);
        Task parsed = storage.parseTask(event.toFileString());
        assertEquals(event.toString(), parsed.toString());
    }
}

package allison;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

import allison.task.Task;
import allison.task.Todo;
import allison.task.Deadline;
import allison.task.Event;

/**
 * Handles persistent storage of tasks by loading from and saving to a file.
 */
public class Storage {
    private static final String TODO_SHORTFORM = "T";
    private static final String DEADLINE_SHORTFORM = "D";
    private static final String EVENT_SHORTFORM = "E";
    private static final String FIELD_DELIMITER = "\\|";
    private static final String DONE_VALUE = "1";

    private final String filePath;

    /**
     * Constructs a Storage instance that reads from and writes to the specified file path.
     *
     * @param filePath File path used for task storage.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns a list of tasks loaded from the storage file.
     * Creates the storage directory if it does not exist.
     *
     * @return List of tasks loaded from file.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        ensureDirectoryExists(file);

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    Task task = parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
        return tasks;
    }

    /**
     * Saves the given list of tasks to the storage file.
     * Overwrites any existing file content.
     *
     * @param tasks List of tasks to be saved.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        File file = new File(this.filePath);
        ensureDirectoryExists(file);

        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(task.toFileString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Failed to save tasks");
        }
    }

    /**
     * Returns a Task reconstructed from a single line in the storage file.
     *
     * @param line Line read from the storage file.
     * @return Parsed Task object, or null if parsing fails.
     */
    public Task parseTask(String line) {
        String[] parts = line.split(FIELD_DELIMITER);
        String type = parts[0];
        boolean isDone = parts[1].equals(DONE_VALUE);
        String description = parts[2];

        Task task;
        switch (type) {
        case TODO_SHORTFORM:
            task = new Todo(description);
            break;
        case DEADLINE_SHORTFORM:
            task = parseDeadlineFromFile(parts, description);
            if (task == null) {
                return null;
            }
            break;
        case EVENT_SHORTFORM:
            task = parseEventFromFile(parts, description);
            if (task == null) {
                return null;
            }
            break;
        default:
            return null;
        }
        task.setIsDone(isDone);
        return task;
    }

    /**
     * Parses a Deadline task from file data parts.
     *
     * @param parts The split fields from the storage line.
     * @param description The task description.
     * @return A Deadline task, or null if the date format is invalid.
     */
    private Task parseDeadlineFromFile(String[] parts, String description) {
        try {
            LocalDateTime dueDateTime = LocalDateTime.parse(parts[3]);
            return new Deadline(description, dueDateTime);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date/time format. Use yyyy-MM-ddTHH:mm");
            return null;
        }
    }

    /**
     * Parses an Event task from file data parts.
     *
     * @param parts The split fields from the storage line.
     * @param description The task description.
     * @return An Event task, or null if the date format is invalid.
     */
    private Task parseEventFromFile(String[] parts, String description) {
        try {
            LocalDateTime start = LocalDateTime.parse(parts[3]);
            LocalDateTime end = LocalDateTime.parse(parts[4]);
            return new Event(description, start, end);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date/time format. Use yyyy-MM-ddTHH:mm");
            return null;
        }
    }

    /**
     * Ensures the parent directory of the given file exists, creating it if necessary.
     *
     * @param file The file whose parent directory should be ensured.
     */
    private void ensureDirectoryExists(File file) {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }
}

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
        File f = new File(filePath);
        File parentDir = f.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        if (!f.exists()) {
            return tasks;
        }

        try {
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                if (!line.isEmpty()) {
                    Task task = parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
            return tasks;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            return tasks;
        }
    }

    /**
     * Saves the given list of tasks to the storage file.
     * Overwrites any existing file content.
     *
     * @param tasks List of tasks to be saved.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        File f = new File(this.filePath);
        File parentDir = f.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try {
            FileWriter fw = new FileWriter(filePath);

            for (Task task : tasks) {
                fw.write(task.toFileString() + System.lineSeparator());
            }
            fw.close();
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
        String[] parts = line.split("\\|");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                try {
                    LocalDateTime dueDate = LocalDateTime.parse(parts[3]);
                    task = new Deadline(description, dueDate);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date/time format. Use yyyy-MM-ddTHH:mm");
                    return null;
                }
                break;
            case "E":
                try {
                    LocalDateTime start = LocalDateTime.parse(parts[3]);
                    LocalDateTime end = LocalDateTime.parse(parts[4]);
                    task = new Event(description, start, end);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date/time format. Use yyyy-MM-ddTHH:mm");
                    return null;
                }
                break;
            default:
                return null;
        }
        task.setIsDone(isDone);
        return task;
    }
}

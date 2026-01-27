import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        File f = new File(filePath);
        Scanner s = new Scanner(f);

        if (!f.exists()) {
            return tasks;
        }

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
    }

    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        for (Task task: tasks) {
            fw.write(task.toFileString() + "\n");
        }
        fw.close();
    }

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
                String dueDate = parts[3];
                task = new Deadline(description, dueDate);
                break;
            case "E":
                String start = parts[3];
                String end = parts[4];
                task = new Event(description, start, end);
                break;
            default:
                return null;
        }
        task.setIsDone(isDone);
        return task;
    }
}

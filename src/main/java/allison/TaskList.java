package allison;

import java.util.ArrayList;
import allison.task.Task;

public class TaskList {
    protected ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task, int taskNum) {
        this.tasks.remove(taskNum - 1);
    }

    public void markTask(int taskNum) {
        this.tasks.get(taskNum - 1).markAsDone();
    }

    public void unmarkTask(int taskNum) {
        this.tasks.get(taskNum - 1).markAsUndone();
    }
}
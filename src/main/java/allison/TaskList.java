package allison;

import java.util.ArrayList;
import allison.task.Task;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public int getNumTasks() {
        return this.tasks.size();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Task removeTask(int taskNum) {
        Task task = this.tasks.get(taskNum - 1);
        this.tasks.remove(taskNum - 1);
        return task;
    }

    public Task markTask(int taskNum) {
        this.tasks.get(taskNum - 1).markAsDone();
        return this.tasks.get(taskNum - 1);
    }

    public Task unmarkTask(int taskNum) {
        this.tasks.get(taskNum - 1).markAsUndone();
        return this.tasks.get(taskNum - 1);
    }

    public StringBuilder listTasks() {
        StringBuilder tasksStr = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            if (this.tasks.get(i) != null) {
                tasksStr.append(i + 1).append(". ").append(this.tasks.get(i));
                if (i < this.tasks.size() - 1) {
                    tasksStr.append("\n");
                }
            }
        }
        return tasksStr;
    }
}
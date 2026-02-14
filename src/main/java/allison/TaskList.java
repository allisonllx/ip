package allison;

import java.util.ArrayList;
import allison.task.Task;

/**
 * Represents a list of tasks and provides operations to manage them.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks Initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int getNumTasks() {
        return this.tasks.size();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task Task to be added.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified task number.
     *
     * @param taskNum Task number to remove (1-based index).
     * @return Removed task.
     * @throws AllisonException If the task number is out of range.
     */
    public Task removeTask(int taskNum) throws AllisonException {
        validateTaskNum(taskNum, "delete <task number>");
        Task task = this.tasks.get(taskNum - 1);
        this.tasks.remove(taskNum - 1);
        return task;
    }

    /**
     * Marks the task at the specified task number as done.
     *
     * @param taskNum Task number to mark as done (1-based index).
     * @return Updated task.
     * @throws AllisonException If the task number is out of range.
     */
    public Task markTask(int taskNum) throws AllisonException {
        validateTaskNum(taskNum, "mark <task number>");
        this.tasks.get(taskNum - 1).markAsDone();
        return this.tasks.get(taskNum - 1);
    }

    /**
     * Marks the task at the specified task number as not done.
     *
     * @param taskNum Task number to unmark (1-based index).
     * @return Updated task.
     * @throws AllisonException If the task number is out of range.
     */
    public Task unmarkTask(int taskNum) throws AllisonException {
        validateTaskNum(taskNum, "unmark <task number>");
        this.tasks.get(taskNum - 1).markAsUndone();
        return this.tasks.get(taskNum - 1);
    }

    /**
     * Validates that a task number is within the valid range (1-based).
     *
     * @param taskNum The task number to validate.
     * @param commandUsage The correct usage string shown in error messages.
     * @throws AllisonException If the task number is out of range.
     */
    private void validateTaskNum(int taskNum, String commandUsage) throws AllisonException {
        if (taskNum > getNumTasks() || taskNum < 1) {
            throw new AllisonException("Task number out of range", commandUsage);
        }
    }

    /**
     * Returns a formatted list of all tasks with their indices.
     *
     * @return StringBuilder containing the task list.
     */
    public StringBuilder listTasks() {
        StringBuilder tasksStr = new StringBuilder();
        int numTasks = getNumTasks();
        for (int i = 0; i < numTasks; i++) {
            Task task = this.tasks.get(i);
            if (task != null) {
                tasksStr.append(i + 1).append(". ").append(task);

                // Add new line for each task as formatting (except the last task)
                if (i < numTasks - 1) {
                    tasksStr.append("\n");
                }
            }
        }
        return tasksStr;
    }

    /**
     * Returns a list of tasks whose descriptions contain the given keyword (case-insensitive).
     *
     * @param keyword The keyword to search for.
     * @return A list of matching tasks.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchedTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Task task : this.tasks) {
            if (task != null) {
                boolean taskContainsKeyword = task.getDescription().toLowerCase().contains(lowerKeyword);
                if (taskContainsKeyword) {
                    matchedTasks.add(task);
                }
            }
        }

        return matchedTasks;
    }
}
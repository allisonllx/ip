package allison;

import java.util.Scanner;
import allison.task.Task;

/**
 * Handles all user-facing messages and formatting for the Allison application.
 * Responsible for generating responses shown to the user.
 */
public class Ui {
    private final Scanner scanner = new Scanner(System.in);
    private final String SEPARATOR = "_".repeat(60);

    /**
     * Returns the welcome message shown when the application starts.
     *
     * @return Welcome message string
     */
    public String welcomeMessage() {
        return SEPARATOR + "\n" + "Hello! I'm Allison.\nWhat can I do for you?" + "\n" + SEPARATOR;
    }

    /**
     * Returns the farewell message shown when the application exits.
     *
     * @return Exit message string
     */
    public String exitMessage() {
        return SEPARATOR + "\n" + "Bye. Hope to see you again soon!" + "\n" + SEPARATOR;
    }

    /**
     * Formats an error message from an exception.
     *
     * @param e Exception containing the error message
     * @return Formatted error message
     */
    public String errorMessage(Exception e) {
        return SEPARATOR + "\n" + e.getMessage() + "\n" + SEPARATOR;
    }

    /**
     * Formats a custom error message.
     *
     * @param text Error message text
     * @return Formatted error message
     */
    public String errorMessage(String text) {
        return SEPARATOR + "\n" + text + "\n" + SEPARATOR;
    }

    /**
     * Returns a formatted list of all tasks.
     *
     * @param tasks TaskList containing all current tasks
     * @return Formatted task list message
     */
    public String listTasks(TaskList tasks) {
        return SEPARATOR + "\n" + "Here are the tasks in your list:"
                + "\n" + tasks.listTasks() + "\n" + SEPARATOR;
    }

    /**
     * Returns a confirmation message after adding a task.
     *
     * @param task The task that was added
     * @param numTasks Total number of tasks after addition
     * @return Formatted add-task message
     */
    public String addTask(Task task, int numTasks) {
        return SEPARATOR + "\n" + "Got it. I've added this task:\n" + task.toString() + "\n"
                + "Now you have " + numTasks + " tasks in the list." + "\n" + SEPARATOR;
    }

    /**
     * Returns a confirmation message after marking a task as done.
     *
     * @param task The task that was marked
     * @return Formatted mark-task message
     */
    public String markTask(Task task) {
        return SEPARATOR + "\n" + "Nice! I've marked this task as done:\n"
                + task.toString() + "\n" + SEPARATOR;
    }

    /**
     * Returns a confirmation message after unmarking a task.
     *
     * @param task The task that was unmarked
     * @return Formatted unmark-task message
     */
    public String unmarkTask(Task task) {
        return SEPARATOR + "\n" + "OK, I've marked this task as not done yet:\n"
                + task.toString() + "\n" + SEPARATOR;
    }

    /**
     * Returns a confirmation message after deleting a task.
     *
     * @param task The task that was removed
     * @param numTasks Total number of tasks after deletion
     * @return Formatted delete-task message
     */
    public String deleteTask(Task task, int numTasks) {
        return SEPARATOR + "\n" + "Noted. I've removed this task:\n" + task.toString() + "\n"
                + "Now you have " + numTasks + " tasks in the list." + "\n" + SEPARATOR;
    }
}
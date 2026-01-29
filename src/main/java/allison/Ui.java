package allison;

import java.util.Scanner;
import allison.task.Task;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);
    private final String SEPARATOR = "_".repeat(60);

    public String welcomeMessage() {
        return SEPARATOR + "\n" + "Hello! I'm Allison.\nWhat can I do for you?" + "\n" + SEPARATOR;
    }

    public String exitMessage() {
        return SEPARATOR + "\n" + "Bye. Hope to see you again soon!" + "\n" + SEPARATOR;
    }

    public String errorMessage(Exception e) {
        return SEPARATOR + "\n" + e.getMessage() + "\n" + SEPARATOR;
    }

    public String errorMessage(String text) {
        return SEPARATOR + "\n" + text + "\n" + SEPARATOR;
    }

    public String listTasks(TaskList tasks) {
        return SEPARATOR + "\n" + "Here are the tasks in your list:"
                + "\n" + tasks.listTasks() + "\n" + SEPARATOR;
    }

    public String addTask(Task task, int numTasks) {
        return SEPARATOR + "\n" + "Got it. I've added this task:\n" + task.toString() + "\n"
                + "Now you have " + numTasks + " tasks in the list." + "\n" + SEPARATOR;
    }

    public String markTask(Task task) {
        return SEPARATOR + "\n" + "Nice! I've marked this task as done:\n"
                + task.toString() + "\n" + SEPARATOR;
    }

    public String unmarkTask(Task task) {
        return SEPARATOR + "\n" + "OK, I've marked this task as not done yet:\n"
                + task.toString() + "\n" + SEPARATOR;
    }

    public String deleteTask(Task task, int numTasks) {
        return SEPARATOR + "\n" + "Noted. I've removed this task:\n" + task.toString() + "\n"
                + "Now you have " + numTasks + " tasks in the list." + "\n" + SEPARATOR;
    }
}
package allison;

import java.util.ArrayList;
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

    public String listTasks(ArrayList<Task> tasks) {
        StringBuilder tasksStr = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i) != null) {
                tasksStr.append(i + 1).append(". ").append(tasks.get(i));
            }
        }
        return SEPARATOR + "\n" + "Here are the tasks in your list:"
                + "\n" + tasksStr + "\n" + SEPARATOR;
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

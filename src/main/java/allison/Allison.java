package allison;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import allison.task.Task;
import allison.task.Todo;
import allison.task.Deadline;
import allison.task.Event;

/**
 * Represents the main entry point and controller of the Allison task management application.
 * This class coordinates user input, task operations, storage, and UI output.
 */
public class Allison {
    private static final String FILE_PATH = "data/allison.txt";
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private Parser parser;

    /**
     * Creates a new Allison application instance and initializes storage,
     * task list, and UI components.
     */
    public Allison() {
        this.storage = new Storage(FILE_PATH);
        this.ui = new Ui();
        this.taskList = new TaskList(this.storage.load());
        this.parser = new Parser();
    }

    /**
     * Returns the welcome message shown to the user when the application starts.
     *
     * @return Welcome message string.
     */
    public String greetUser() {
        return ui.welcomeMessage();
    }

    /**
     * Returns the exit message shown to the user when the application terminates.
     *
     * @return Exit message string.
     */
    public String exitUser() {
        return ui.exitMessage();
    }

    /**
     * Returns an error message based on the provided input string.
     *
     * @param text Input that caused the error.
     * @return Error message string.
     */
    public String showError(String text) {
        return ui.errorMessage(text);
    }

    /**
     * Returns an error message based on the provided exception.
     *
     * @param e Exception that occurred.
     * @return Error message string.
     */
    public String showError(Exception e) {
        return ui.errorMessage(e);
    }

    /**
     * Returns a formatted list of all tasks currently stored in the task list.
     *
     * @return String representation of the task list.
     */
    public String listTasks() {
        return ui.listTasks(this.taskList);
    }

    /**
     * Marks the specified task as done.
     *
     * @param taskNum Task number to mark.
     * @return Confirmation message string.
     * @throws AllisonException If the task number is invalid.
     */
    public String markTask(int taskNum) throws AllisonException {
        Task task = taskList.markTask(taskNum);
        return ui.markTask(task);
    }

    /**
     * Marks the specified task as not done.
     *
     * @param taskNum Task number to unmark.
     * @return Confirmation message string.
     * @throws AllisonException If the task number is invalid.
     */
    public String unmarkTask(int taskNum) throws AllisonException {
        Task task = taskList.unmarkTask(taskNum);
        return ui.unmarkTask(task);
    }

    /**
     * Deletes the specified task from the task list.
     *
     * @param taskNum Task number to delete.
     * @return Confirmation message string.
     * @throws AllisonException If the task number is invalid.
     */
    public String deleteTask(int taskNum) throws AllisonException {
        Task task = taskList.removeTask(taskNum);
        return ui.deleteTask(task, taskList.getNumTasks());
    }

    /**
     * Finds and returns tasks whose descriptions contain the given keyword.
     *
     * @param keyword The search keyword.
     * @return Formatted list of matching tasks.
     */
    public String findTask(String keyword) {
        ArrayList<Task> tasks = taskList.findTasks(keyword);
        return ui.findTask(tasks);
    }

    /**
     * Returns the help message listing all available commands.
     *
     * @return Help message string.
     */
    public String provideHelp() {
        return ui.provideHelp();
    }

    /**
     * Adds a todo task with the given description.
     *
     * @param desc Description of the todo task.
     * @return Confirmation message string.
     */
    public String addTodo(String desc) {
        Todo todo = new Todo(desc);
        taskList.addTask(todo);
        return ui.addTask(todo, taskList.getNumTasks());
    }

    /**
     * Adds a deadline task with the given description and deadline arguments.
     *
     * @param desc Description of the deadline task.
     * @param args List containing deadline date-time arguments.
     * @return Confirmation or error message string.
     */
    public String addDeadline(String desc, ArrayList<String> args) {
        try {
            LocalDateTime dueDate = LocalDateTime.parse(args.get(0));
            Deadline deadline = new Deadline(desc, dueDate);
            taskList.addTask(deadline);
            return ui.addTask(deadline, taskList.getNumTasks());
        } catch (DateTimeParseException e) {
            return ui.errorMessage(e);
        }
    }

    /**
     * Adds an event task with the given description and event time arguments.
     *
     * @param desc Description of the event task.
     * @param args List containing start and end date-time arguments.
     * @return Confirmation or error message string.
     */
    public String addEvent(String desc, ArrayList<String> args) {
        try {
            LocalDateTime start = LocalDateTime.parse(args.get(0));
            LocalDateTime end = LocalDateTime.parse(args.get(1));
            Event event = new Event(desc, start, end);
            taskList.addTask(event);
            return ui.addTask(event, taskList.getNumTasks());
        } catch (DateTimeParseException e) {
            return ui.errorMessage(e);
        }
    }

    /**
     * Saves all current tasks to persistent storage.
     */
    public void saveTasks() {
        storage.saveTasks(taskList.getTasks());
    }


    /**
     * Processes the user's input and returns the appropriate response.
     * Parses the command, executes the corresponding action, and saves tasks.
     *
     * @param input The raw user input string.
     * @return The response message to display to the user.
     */
    public String getResponse(String input) {
        String botMessage;
        try {
            Command command = parser.parseCommand(input);

            switch (command) {
            case BYE:
                botMessage = exitUser();
                break;
            case LIST:
                botMessage = listTasks();
                break;
            case HELP:
                botMessage = provideHelp();
                break;
            case MARK:
                int markTaskNum = parser.parseTaskNum(input);
                botMessage = markTask(markTaskNum);
                break;
            case UNMARK:
                int unmarkTaskNum = parser.parseTaskNum(input);
                botMessage = unmarkTask(unmarkTaskNum);
                break;
            case DELETE:
                int deleteTaskNum = parser.parseTaskNum(input);
                botMessage = deleteTask(deleteTaskNum);
                break;
            case FIND:
                String keyword = parser.parseFindKeyword(input);
                botMessage = findTask(keyword);
                break;
            case TODO:
                String todoDesc = parser.parseTodoDesc(input);
                botMessage = addTodo(todoDesc);
                break;
            case DEADLINE:
                String deadlineDesc = parser.parseDeadlineDesc(input);
                ArrayList<String> deadlineArgs = parser.parseDeadlineArgs(input);
                botMessage = addDeadline(deadlineDesc, deadlineArgs);
                break;
            case EVENT:
                String eventDesc = parser.parseEventDesc(input);
                ArrayList<String> eventArgs = parser.parseEventArgs(input);
                botMessage = addEvent(eventDesc, eventArgs);
                break;
            default:
                botMessage = showError(input);
            }
        } catch (Exception e) {
            botMessage = showError(e);
        }
        return botMessage;
    }

    /**
     * Runs the Allison application in CLI mode, reading user input
     * and printing responses until the user types "bye".
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Allison allison = new Allison();
        Scanner sc = new Scanner(System.in);

        System.out.println(allison.greetUser());
        boolean isRunning = true;
        while (isRunning) {
            String input = sc.nextLine();
            String response = allison.getResponse(input);
            System.out.println(response);
            allison.saveTasks();

            if (input.trim().equalsIgnoreCase("bye")) {
                isRunning = false;
            }
        }
        sc.close();
    }
}
package allison;

import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.time.LocalDateTime;
import allison.task.Task;
import allison.task.Todo;
import allison.task.Deadline;
import allison.task.Event;

public class Allison {
    private static final String FILE_PATH = "data/allison.txt";
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    public Allison() {
        this.storage = new Storage(FILE_PATH);
        this.ui = new Ui();
        this.taskList = new TaskList(this.storage.load());
    }

    public String greetUser() {
        return ui.welcomeMessage();
    }

    public String exitUser() {
        return ui.exitMessage();
    }

    public String showError(String text) {
        return ui.errorMessage(text);
    }

    public String showError(Exception e) {
        return ui.errorMessage(e);
    }

    public String listTasks() {
        return ui.listTasks(this.taskList);
    }

    public String markTask(int taskNum) throws AllisonException {
        Task task = taskList.markTask(taskNum);
        return ui.markTask(task);
    }

    public String unmarkTask(int taskNum) throws AllisonException {
        Task task = taskList.unmarkTask(taskNum);
        return ui.unmarkTask(task);
    }

    public String deleteTask(int taskNum) throws AllisonException {
        Task task = taskList.removeTask(taskNum);
        return ui.deleteTask(task, taskList.getNumTasks());
    }

    public String findTask(String keyword) {
        ArrayList<Task> tasks = taskList.findTasks(keyword);
        return ui.findTask(tasks);
    }

    public String addTodo(String desc) {
        Todo todo = new Todo(desc);
        taskList.addTask(todo);
        return ui.addTask(todo, taskList.getNumTasks());
    }

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

    public void saveTasks() {
        storage.saveTasks(taskList.getTasks());
    }

    public static void main(String[] args) throws AllisonException, FileNotFoundException {
        Allison allison = new Allison();
        Parser parser = new Parser();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println(allison.greetUser());

        while (running) {
            String text = sc.nextLine();
            String botMessage;
            try {
                Command command = parser.parseCommand(text);

                switch (command) {
                case BYE:
                    botMessage = allison.exitUser();
                    running = false;
                    return;
                case LIST:
                    botMessage = allison.listTasks();
                    break;
                case MARK:
                    int markTaskNum = parser.parseTaskNum(text);
                    botMessage = allison.markTask(markTaskNum);
                    break;
                case UNMARK:
                    int unmarkTaskNum = parser.parseTaskNum(text);
                    botMessage = allison.unmarkTask(unmarkTaskNum);
                    break;
                case DELETE:
                    int deleteTaskNum = parser.parseTaskNum(text);
                    botMessage = allison.deleteTask(deleteTaskNum);
                    break;
                case FIND:
                    String keyword = parser.parseFindKeyword(text);
                    botMessage = allison.findTask(keyword);
                    break;
                case TODO:
                    String todoDesc = parser.parseTodoDesc(text);
                    botMessage = allison.addTodo(todoDesc);
                    break;
                case DEADLINE:
                    String deadlineDesc = parser.parseDeadlineDesc(text);
                    ArrayList<String> deadlineArgs = parser.parseDeadlineArgs(text);
                    botMessage = allison.addDeadline(deadlineDesc, deadlineArgs);
                    break;
                case EVENT:
                    String eventDesc = parser.parseEventDesc(text);
                    ArrayList<String> eventArgs = parser.parseEventArgs(text);
                    botMessage = allison.addEvent(eventDesc, eventArgs);
                    break;
                default:
                    botMessage = allison.showError(text);
                }
            } catch (Exception e) {
                botMessage = allison.showError(e);
            }

            System.out.println(botMessage);
            allison.saveTasks();
        }

        sc.close();
    }
}
package allison;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import allison.task.Task;
import allison.task.Todo;
import allison.task.Deadline;
import allison.task.Event;
import allison.Storage;

public class Allison {
    public static void main(String[] args) throws AllisonException, FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        Storage storage = new Storage("./data/allison.txt");
        ArrayList<Task> list = new ArrayList<>();;
        boolean running = true;

        System.out.println("_".repeat(60));
        System.out.println("Hello! I'm Allison.\nWhat can I do for you?");
        System.out.println("_".repeat(60));

        String text = "";
        while (running) { // loop until user types "bye"
            text = sc.nextLine();
            System.out.println("_".repeat(60));

            if (text.trim().equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                running = false;
            } else if (text.trim().equalsIgnoreCase("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != null) {
                        System.out.println(i+1 + ". " + list.get(i));
                    }
                }
            } else if (text.toLowerCase().startsWith("mark")) {
                String[] parts = text.split(" ", 2);
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]); // convert string to int
                        System.out.println("Nice! I've marked this task as done:");
                        list.get(taskNumber - 1).markAsDone();
                        System.out.println(list.get(taskNumber - 1));
                    } catch (NumberFormatException e) {
                        throw new AllisonException("Invalid number after 'mark'", "mark <task number>");
                    }
                } else {
                    throw new AllisonException("Missing task number", "mark <task number>");
                    }
            } else if (text.toLowerCase().startsWith("unmark")) {
                String[] parts = text.split(" ", 2);
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]); // convert string to int
                        System.out.println("OK, I've marked this task as not done yet:");
                        list.get(taskNumber - 1).markAsUndone();
                        System.out.println(list.get(taskNumber - 1));
                    } catch (NumberFormatException e) {
                        throw new AllisonException("Invalid number after 'unmark'", "unmark <task number>");
                    }
                } else {
                    throw new AllisonException("Missing task number", "unmark <task number>");
                }
            } else if (text.toLowerCase().startsWith("delete")) {
                String[] parts = text.split(" ", 2);
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]); // convert string to int
                        System.out.println("Noted. I've removed this task:");
                        System.out.println(list.get(taskNumber - 1));
                        list.remove(taskNumber - 1);
                        System.out.println("Now you have " + list.size() + " tasks in the list.");
                    } catch (NumberFormatException e) {
                        throw new AllisonException("Invalid number after 'delete'", "delete <task number>");
                    }
                } else {
                    throw new AllisonException("Missing task number", "delete <task number>");
                }
            } else if (text.toLowerCase().startsWith("todo")) {
                String[] parts = text.split(" ", 2);
                Todo todo = null;
                if (parts.length == 2) {
                    String taskDesc = parts[1].trim();
                    todo = new Todo(taskDesc);
                } else {
                    throw new AllisonException("Missing description in todo", "todo <description>");
                }
                list.add(todo);

                System.out.println("Got it. I've added this task:");
                System.out.println(todo);
                System.out.println("Now you have " + list.size() + " tasks in the list.");
            } else if (text.toLowerCase().startsWith("deadline")) {
                if (!text.contains("/by")) {
                    throw new AllisonException("Missing /by in deadline", "deadline <task> /by <time>");
                }

                String[] parts = text.split("/by", 2);
                Deadline deadline = null;
                if (parts.length == 2) {
                    String description = parts[0]
                            .replaceFirst("deadline", "")
                            .trim();
                    if (description.isEmpty()) {
                        throw new AllisonException("Missing description in deadline", "deadline <task> /by <time>");
                    }
                    String by = parts[1].trim();
                    deadline = new Deadline(description, by);
                } else {
                    throw new AllisonException("Missing due date in deadline", "deadline <task> /by <time>");
                }
                list.add(deadline);

                System.out.println("Got it. I've added this task:");
                System.out.println(deadline);
                System.out.println("Now you have " + list.size() + " tasks in the list.");
            } else if (text.toLowerCase().startsWith("event")) {
                if (!text.contains("/from") || !text.contains("/to")) {
                    throw new AllisonException("Missing /from or /to in event", "event <desc> /from <start> /to <end>");
                }

                String withoutEvent = text.substring(5).trim(); // remove "event "
                String[] firstSplit = withoutEvent.split("/from", 2);

                String description = firstSplit[0].trim();
                if (description.isEmpty()) {
                    throw new AllisonException("Missing description in deadline", "deadline <task> /by <time>");
                }
                String[] timeSplit = firstSplit[1].split("/to", 2);

                String from = timeSplit[0].trim();
                String to = timeSplit[1].trim();
                Event event = new Event(description, from, to);
                list.add(event);

                System.out.println("Got it. I've added this task:");
                System.out.println(event);
                System.out.println("Now you have " + list.size() + " tasks in the list.");
            } else {
                throw new AllisonException();
            }

            System.out.println("_".repeat(60));
        }

        sc.close();
    }
}
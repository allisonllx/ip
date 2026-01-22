import java.util.Scanner;

public class Allison {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] list = new Task[100];
        int counter = 0;

        System.out.println("_".repeat(60));
        System.out.println("Hello! I'm Allison.\nWhat can I do for you?");
        System.out.println("_".repeat(60));

        String text = "";
        while (!text.trim().equalsIgnoreCase("bye")) { // loop until user types "bye"
//            System.out.print("> "); // prompt for input
            text = sc.nextLine();

            if (text.trim().equalsIgnoreCase("bye")) {
                System.out.println("_".repeat(60));
                System.out.println("Bye. Hope to see you again soon!");
                System.out.print("_".repeat(60));
            } else if (text.trim().equalsIgnoreCase("list")) {
                System.out.println("_".repeat(60));
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < list.length; i++) {
                    if (list[i] != null) {
                        System.out.println(i+1 + ". " + list[i]);
                    }
                }
                System.out.println("_".repeat(60));
            } else if (text.toLowerCase().startsWith("mark")) {
                String[] parts = text.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]); // convert string to int
                        System.out.println("_".repeat(60));
                        System.out.println("Nice! I've marked this task as done:");
                        list[taskNumber - 1].markAsDone();
                        System.out.println(list[taskNumber - 1]);
                        System.out.println("_".repeat(60));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number after 'mark'.");
                    }
                } else {
                    System.out.println("Usage: mark <task number>");
                    }
            } else if (text.toLowerCase().startsWith("unmark")) {
                String[] parts = text.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]); // convert string to int
                        System.out.println("_".repeat(60));
                        System.out.println("OK, I've marked this task as not done yet:");
                        list[taskNumber - 1].markAsUndone();
                        System.out.println(list[taskNumber - 1]);
                        System.out.println("_".repeat(60));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number after 'unmark'.");
                    }
                } else {
                    System.out.println("Usage: unmark <task number>");
                }
            } else if (text.toLowerCase().startsWith("todo")) {
                String[] parts = text.split(" ", 2);
                Todo todo = null;
                if (parts.length == 2) {
                    String taskDesc = parts[1].trim();
                    todo = new Todo(taskDesc);
                } else {
                    System.out.println("Usage: todo <description>");
                    return;
                }
                list[counter++] = todo;

                System.out.println("_".repeat(60));
                System.out.println("Got it. I've added this task:");
                System.out.println(todo);
                System.out.println("Now you have " + counter + " tasks in the list.");
                System.out.println("_".repeat(60));
            } else if (text.toLowerCase().startsWith("deadline")) {
                if (!text.contains("/by")) {
                    System.out.println("Usage: deadline <task> /by <time>");
                    return;
                }

                String[] parts = text.split("/by", 2);
                Deadline deadline = null;
                if (parts.length == 2) {
                    String description = parts[0]
                            .replaceFirst("deadline", "")
                            .trim();
                    String by = parts[1].trim();
                    deadline = new Deadline(description, by);
                } else {
                    System.out.println("Usage: deadline <task> /by <time>");
                    return;
                }
                list[counter++] = deadline;

                System.out.println("_".repeat(60));
                System.out.println("Got it. I've added this task:");
                System.out.println(deadline);
                System.out.println("Now you have " + counter + " tasks in the list.");
                System.out.println("_".repeat(60));
            } else if (text.toLowerCase().startsWith("event")) {
                if (!text.contains("/from") || !text.contains("/to")) {
                    System.out.println("Usage: event <desc> /from <start> /to <end>");
                    return;
                }

                String withoutEvent = text.substring(5).trim(); // remove "event "
                String[] firstSplit = withoutEvent.split("/from", 2);

                String description = firstSplit[0].trim();
                String[] timeSplit = firstSplit[1].split("/to", 2);

                String from = timeSplit[0].trim();
                String to = timeSplit[1].trim();
                Event event = new Event(description, from, to);
                list[counter++] = event;

                System.out.println("_".repeat(60));
                System.out.println("Got it. I've added this task:");
                System.out.println(event);
                System.out.println("Now you have " + counter + " tasks in the list.");
                System.out.println("_".repeat(60));
            } else {
                System.out.println("_".repeat(60));
                System.out.println("Invalid command");
                System.out.println("_".repeat(60));
            }
        }

        sc.close();
    }
}

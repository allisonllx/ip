import java.util.Scanner;

public class Allison {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] list = new Task[100];
        int counter = 0;

        System.out.println("-".repeat(60));
        System.out.println("Hello! I'm Allison.\nWhat can I do for you?");
        System.out.println("-".repeat(60));

        String text = "";
        while (!text.equalsIgnoreCase("bye")) { // loop until user types "bye"
            System.out.print("> "); // prompt for input
            text = sc.nextLine();

            if (text.trim().equalsIgnoreCase("bye")) {
                System.out.println("-".repeat(60));
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("-".repeat(60));
            } else if (text.trim().equalsIgnoreCase("list")) {
                System.out.println("-".repeat(60));
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < list.length; i++) {
                    if (list[i] != null) {
                        System.out.println(i+1 + ". " + list[i]);
                    }
                }
            } else if (text.toLowerCase().startsWith("mark")) {
                String[] parts = text.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]); // convert string to int
                        System.out.println("Nice! I've marked this task as done:");
                        list[taskNumber - 1].markAsDone();
                        System.out.println(list[taskNumber - 1]);
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
                        System.out.println("OK, I've marked this task as not done yet:");
                        list[taskNumber - 1].markAsUndone();
                        System.out.println(list[taskNumber - 1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number after 'unmark'.");
                    }
                } else {
                    System.out.println("Usage: unmark <task number>");
                }
            } else {
                list[counter++] = new Task(text);
                System.out.println("-".repeat(60));
                System.out.println("added: " + text);
                System.out.println("-".repeat(60));
            }
        }

        sc.close();
    }
}

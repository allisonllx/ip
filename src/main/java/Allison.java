import java.util.Scanner;

public class Allison {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] list = new String[100];
        int counter = 0;

        System.out.println("-".repeat(60));
        System.out.println("Hello! I'm Allison.\nWhat can I do for you?");
        System.out.println("-".repeat(60));

        String text = "";
        while (!text.equalsIgnoreCase("bye")) { // loop until user types "bye"
            System.out.print("> "); // prompt for input
            text = sc.nextLine();

            if (text.equalsIgnoreCase("bye")) {
                System.out.println("-".repeat(60));
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("-".repeat(60));
            } else if (text.equalsIgnoreCase("list")) {
                System.out.println("-".repeat(60));
                for (int i = 0; i < list.length; i++) {
                    if (list[i] != null) {
                        System.out.println(i+1 + ". " + list[i]);
                    }
                }
            } else {
                list[counter++] = text;
                System.out.println("-".repeat(60));
                System.out.println("added: " + text);
                System.out.println("-".repeat(60));
            }
        }

        sc.close();
    }
}

import java.util.Scanner;

public class Allison {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

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
            } else {
                System.out.println("-".repeat(60));
                System.out.println(text);
                System.out.println("-".repeat(60));
            }
        }

        sc.close();
    }
}

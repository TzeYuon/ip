import java.util.Scanner;

/**
 * Provides a simple command-line interface for CBT.
 */
public class CBT {
    /**
     * Displays a welcome message, echoes each command, and exits on {@code bye}.
     *
     * @param args command-line arguments, which are not used by this program
     */
    public static void main(String[] args) {
        String banner = "  ____ ____ _____\n"
                + " / ___| __ )_   _|\n"
                + "| |   |  _ \\ | |\n"
                + "| |___| |_) || |\n"
                + " \\____|____/ |_|\n";

        String[] Array = new String[100];
        int index = 0;
        System.out.println("____________________________________________________________");
        System.out.println(banner);
        System.out.println("Hello! I'm CBT.");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println("____________________________________________________________");
            if (command.equals("list")) {
                for (int j = 0; j < index; j++) {
                    System.out.println(j + 1 + ". " + Array[j]);
                }
            }
            else if (command.equals("bye")) {
                break;
            }
            else {
                Array[index++] = command;
                System.out.println("added: " + command);
            }
            System.out.println("____________________________________________________________");
        }

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}

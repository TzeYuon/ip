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

        Task[] tasks = new Task[100];
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
                System.out.println("Here are the tasks in your list:");
                for (int j = 0; j < index; j++) {
                    System.out.println(j + 1 + "." + tasks[j]);
                }
            }
            else if (command.startsWith("mark ")) {
                String taskNumber = command.substring("mark ".length()).trim();
                try {
                    int taskIndex = Integer.parseInt(taskNumber) - 1;
                    if (taskIndex < 0 || taskIndex >= index) {
                        System.out.println("Please enter a task number from the list.");
                    } else {
                        tasks[taskIndex].markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks[taskIndex]);
                    }
                } catch (NumberFormatException exception) {
                    System.out.println("Please enter a task number from the list.");
                }
            }
            else if (command.startsWith("unmark ")) {
                String taskNumber = command.substring("unmark ".length()).trim();
                try {
                    int taskIndex = Integer.parseInt(taskNumber) - 1;
                    if (taskIndex < 0 || taskIndex >= index) {
                        System.out.println("Please enter a task number from the list.");
                    } else {
                        tasks[taskIndex].markAsNotDone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks[taskIndex]);
                    }
                } catch (NumberFormatException exception) {
                    System.out.println("Please enter a task number from the list.");
                }
            }
            else if (command.equals("bye")) {
                break;
            }
            else {
                tasks[index++] = new Task(command);
                System.out.println("added: " + command);
            }
            System.out.println("____________________________________________________________");
        }

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}

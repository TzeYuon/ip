import java.util.Scanner;
import java.util.ArrayList;

/**
 * Provides a command-line interface for managing todos, deadlines, and events.
 */
public class CBT {
    private static final String DIVIDER = "____________________________________________________________";
    public enum CommandWord {
        TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE, BYE, UNKNOWN
    }

    /**
     * Starts the application and processes commands until the user enters {@code bye}.
     *
     * @param args command-line arguments, which are not used by this program
     */
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        printWelcomeMessage();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            System.out.println(DIVIDER);
            if (command.equals("bye")) {
                break;
            }
            try {
                executeCommand(command, tasks);
            } catch (CbtException exception) {
                System.out.println(exception.getMessage());
            }
            System.out.println(DIVIDER);
        }

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }

    /** Prints the application's greeting. */
    private static void printWelcomeMessage() {
        String banner = "  ____ ____ _____\n"
                + " / ___| __ )_   _|\n"
                + "| |   |  _ \\ | |\n"
                + "| |___| |_) || |\n"
                + " \\____|____/ |_|\n";
        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println("Hello! I'm CBT.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);
    }

    /**
     * Executes one command and returns the possibly updated number of stored tasks.
     *
     * @param command user-entered command
     * @param tasks task storage
     * @throws CbtException if the command is invalid
     */
    private static void executeCommand(String command, ArrayList<Task> tasks) throws CbtException {
        String[] parts = command.split("\\s+", 2);
        String firstWord = parts[0].toUpperCase();
        String arguments = parts.length > 1 ? parts[1] : "";

        CommandWord commandWord;
        try {
            commandWord = CommandWord.valueOf(firstWord);
        } catch (IllegalArgumentException e) {
            commandWord = CommandWord.UNKNOWN;
        }

        switch (commandWord) {
            case LIST:
                printTaskList(tasks);
                break;
            case MARK:
                markTask(tasks, arguments, true);
                break;
            case UNMARK:
                markTask(tasks, arguments, false);
                break;
            case TODO:
                addTodo(tasks, arguments);
                break;
            case DEADLINE:
                addDeadline(tasks, arguments.trim());
                break;
            case EVENT:
                addEvent(tasks, arguments.trim());
                break;
            case DELETE:
                deleteTask(tasks, arguments.trim());
                break;
            case UNKNOWN:
                throw new CbtException("I don't understand that command. Try todo, deadline, event, list, mark, unmark, delete or bye.");
        }
    }

    /** Prints all stored tasks in their list format. */
    private static void printTaskList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int index = 0; index < tasks.size(); index++) {
            System.out.println(index + 1 + "." + tasks.get(index));
        }
    }

    /** Adds a todo when its description is present. */
    private static void addTodo(ArrayList<Task> tasks, String description) throws CbtException {
        if (description.isBlank()) {
            throw new CbtException("The description of a todo cannot be empty. Use: todo DESCRIPTION");
        }
        addTask(tasks, new Todo(description));
    }

    /** Parses and adds a deadline in the form {@code description /by time}. */
    private static void addDeadline(ArrayList<Task> tasks, String details) throws CbtException {
        int byMarker = details.indexOf(" /by ");
        if (byMarker <= 0 || details.substring(byMarker + " /by ".length()).isBlank()) {
            throw new CbtException("Use: deadline DESCRIPTION /by DATE_OR_TIME");
        }
        String description = details.substring(0, byMarker).trim();
        String by = details.substring(byMarker + " /by ".length()).trim();
        addTask(tasks, new Deadline(description, by));
    }

    /** Parses and adds an event in the form {@code description /from start /to end}. */
    private static void addEvent(ArrayList<Task> tasks, String details) throws CbtException {
        int fromMarker = details.indexOf(" /from ");
        int toMarker = details.indexOf(" /to ");
        if (fromMarker <= 0 || toMarker <= fromMarker + " /from ".length()
                || details.substring(fromMarker + " /from ".length(), toMarker).isBlank()
                || details.substring(toMarker + " /to ".length()).isBlank()) {
            throw new CbtException("Use: event DESCRIPTION /from START /to END");
        }
        String description = details.substring(0, fromMarker).trim();
        String start = details.substring(fromMarker + " /from ".length(), toMarker).trim();
        String end = details.substring(toMarker + " /to ".length()).trim();
        addTask(tasks, new Event(description, start, end));
    }

    /** Stores a task and prints the standard confirmation message. */
    private static void addTask(ArrayList<Task> tasks, Task task) {
        tasks.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        String taskWord = tasks.size() == 1 ? "task" : "tasks";
        System.out.println("Now you have " + tasks.size() + " " + taskWord + " in the list.");
    }

    /** Deletes a task's completion and prints the confirmation message */
    private static void deleteTask(ArrayList<Task> tasks, String taskNumber) throws CbtException {
        int taskIndex = getTaskIndex(taskNumber, tasks.size());
        Task removedTask = tasks.remove(taskIndex);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        int newTaskCount = tasks.size();
        String taskWord = tasks.size() == 1 ? "task" : "tasks";
        System.out.println("Now you have " + newTaskCount + " " + taskWord + " in the list.");
    }

    /** Changes a task's completion state after validating its displayed number. */
    private static void markTask(ArrayList<Task> tasks, String taskNumber, boolean isDone) throws CbtException {
        int taskIndex = getTaskIndex(taskNumber, tasks.size());
        if (isDone) {
            tasks.get(taskIndex).markAsDone();
            System.out.println("Nice! I've marked this task as done:");
        } else {
            tasks.get(taskIndex).markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println("  " + tasks.get(taskIndex));
    }

    /** Converts a valid one-based task number to an array index. */
    private static int getTaskIndex(String taskNumber, int taskCount) throws CbtException {
        try {
            int taskIndex = Integer.parseInt(taskNumber.trim()) - 1;
            if (taskIndex >= 0 && taskIndex < taskCount) {
                return taskIndex;
            }
        } catch (NumberFormatException exception) {
            throw new CbtException("Please enter a valid whole number, e.g. mark 1, unmark 1.");
        }
        throw new CbtException("Please enter a valid task number from the list, e.g. mark 1, unmark 1.");
    }
}

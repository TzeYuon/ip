import java.util.Scanner;

/**
 * Provides a command-line interface for managing todos, deadlines, and events.
 */
public class CBT {
    private static final String DIVIDER = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    /**
     * Starts the application and processes commands until the user enters {@code bye}.
     *
     * @param args command-line arguments, which are not used by this program
     */
    public static void main(String[] args) {
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        printWelcomeMessage();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            System.out.println(DIVIDER);
            if (command.equals("bye")) {
                break;
            }
            try {
                taskCount = executeCommand(command, tasks, taskCount);
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
     * @param taskCount number of tasks stored in {@code tasks}
     * @return the updated number of stored tasks
     * @throws CbtException if the command is invalid
     */
    private static int executeCommand(String command, Task[] tasks, int taskCount) throws CbtException {
        if (command.equals("list")) {
            printTaskList(tasks, taskCount);
            return taskCount;
        } else if (command.equals("mark") || command.startsWith("mark ")) {
            markTask(tasks, taskCount, command.substring("mark".length()), true);
            return taskCount;
        } else if (command.equals("unmark") || command.startsWith("unmark ")) {
            markTask(tasks, taskCount, command.substring("unmark".length()), false);
            return taskCount;
        } else if (command.equals("todo") || command.startsWith("todo ")) {
            return addTodo(tasks, taskCount, command.substring("todo".length()).trim());
        } else if (command.equals("deadline") || command.startsWith("deadline ")) {
            return addDeadline(tasks, taskCount, command.substring("deadline".length()).trim());
        } else if (command.equals("event") || command.startsWith("event ")) {
            return addEvent(tasks, taskCount, command.substring("event".length()).trim());
        }
        throw new CbtException("I don't understand that command. Try todo, deadline, event, list, mark, unmark or bye.");
    }

    /** Prints all stored tasks in their list format. */
    private static void printTaskList(Task[] tasks, int taskCount) {
        System.out.println("Here are the tasks in your list:");
        for (int index = 0; index < taskCount; index++) {
            System.out.println(index + 1 + "." + tasks[index]);
        }
    }

    /** Adds a todo when its description is present. */
    private static int addTodo(Task[] tasks, int taskCount, String description) throws CbtException {
        if (description.isBlank()) {
            throw new CbtException("The description of a todo cannot be empty. Use: todo DESCRIPTION");
        }
        return addTask(tasks, taskCount, new Todo(description));
    }

    /** Parses and adds a deadline in the form {@code description /by time}. */
    private static int addDeadline(Task[] tasks, int taskCount, String details) throws CbtException {
        int byMarker = details.indexOf(" /by ");
        if (byMarker <= 0 || details.substring(byMarker + " /by ".length()).isBlank()) {
            throw new CbtException("Use: deadline DESCRIPTION /by DATE_OR_TIME");
        }
        String description = details.substring(0, byMarker).trim();
        String by = details.substring(byMarker + " /by ".length()).trim();
        return addTask(tasks, taskCount, new Deadline(description, by));
    }

    /** Parses and adds an event in the form {@code description /from start /to end}. */
    private static int addEvent(Task[] tasks, int taskCount, String details) throws CbtException {
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
        return addTask(tasks, taskCount, new Event(description, start, end));
    }

    /** Stores a task and prints the standard confirmation message. */
    private static int addTask(Task[] tasks, int taskCount, Task task) throws CbtException {
        if (taskCount == MAX_TASKS) {
            throw new CbtException("Your task list is full. Please remove a task before adding another one.");
        }
        tasks[taskCount] = task;
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        int newTaskCount = taskCount + 1;
        String taskWord = newTaskCount == 1 ? "task" : "tasks";
        System.out.println("Now you have " + newTaskCount + " " + taskWord + " in the list.");
        return taskCount + 1;
    }

    /** Changes a task's completion state after validating its displayed number. */
    private static void markTask(Task[] tasks, int taskCount, String taskNumber, boolean isDone) throws CbtException {
        int taskIndex = getTaskIndex(taskNumber, taskCount);
        if (isDone) {
            tasks[taskIndex].markAsDone();
            System.out.println("Nice! I've marked this task as done:");
        } else {
            tasks[taskIndex].markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println("  " + tasks[taskIndex]);
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

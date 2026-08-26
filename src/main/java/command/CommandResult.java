package command;

/**
 * Contains the outcome of executing a command.
 *
 * @param message message to display to the user
 * @param taskListChanged whether the task list must be saved to the file
 * @param exit whether the application should stop
 */
public record CommandResult(
        String message,
        boolean taskListChanged,
        boolean exit
) {
}
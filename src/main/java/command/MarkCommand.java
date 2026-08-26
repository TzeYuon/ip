package command;

import exception.CbtException;
import task.Task;
import task.TaskList;

/** Marks a task as complete. */
public class MarkCommand implements Command {
    private final String taskNumber;

    public MarkCommand(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public CommandResult execute(TaskList taskList) throws CbtException {
        Task task = taskList.markTask(toIndex(taskNumber));
        String message = "Nice! I've marked this task as done:\n"
                + "  " + task;
        return new CommandResult(message, true, false);
    }

    /** Checks and converts a valid String input to its zero-based index. */
    static int toIndex(String value) throws CbtException {
        try {
            int index = Integer.parseInt(value.trim()) - 1;
            if (index >= 0) {
                return index;
            }
        } catch (NumberFormatException exception) { }
        throw new CbtException("Please enter a task number from the list, e.g. mark 1.");
    }
}

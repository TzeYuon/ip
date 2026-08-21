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
    public void execute(TaskList taskList) throws CbtException {
        Task task = taskList.getTask(toIndex(taskNumber));
        task.markAsDone();
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

package command;

import exception.CbtException;
import task.Task;
import task.TaskList;

/** Marks a task as incomplete. */
public class UnmarkCommand implements Command {
    private final String taskNumber;

    public UnmarkCommand(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public CommandResult execute(TaskList taskList) throws CbtException {
        Task task = taskList.unmarkTask(MarkCommand.toIndex(taskNumber));
        String message = "OK, I've marked this task as not done yet:\n" + "  " + task;
        return new CommandResult(message, true, false);
    }
}

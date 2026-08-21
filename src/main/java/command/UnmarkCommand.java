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
    public void execute(TaskList taskList) throws CbtException {
        Task task = taskList.getTask(MarkCommand.toIndex(taskNumber));
        task.markAsNotDone();
    }
}

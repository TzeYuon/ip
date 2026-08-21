package command;

import exception.CbtException;
import task.Task;
import task.TaskList;

/** Removes one task from the list. */
public class DeleteCommand implements Command {
    private final String taskNumber;

    public DeleteCommand(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList taskList) throws CbtException {
        Task removedTask = taskList.deleteTask(MarkCommand.toIndex(taskNumber));
    }
}

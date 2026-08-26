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
    public CommandResult execute(TaskList tasks) throws CbtException {
        Task removedTask = tasks.deleteTask(MarkCommand.toIndex(taskNumber));
        String message = "Noted. I've removed this task:\n" +
                "  " + removedTask + "\n" + "Now you have " + tasks.getSize() + " task" +
                (tasks.getSize() == 1 ? "" : "s") + " in the list.";
        return new CommandResult(message, true, false);
    }
}

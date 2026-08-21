package command;

import exception.CbtException;
import task.Deadline;
import task.TaskList;

/** Adds a deadline task with user-provided deadline text. */
public class DeadlineCommand implements Command {
    private final String details;

    public DeadlineCommand(String details) {
        this.details = details;
    }

    @Override
    public void execute(TaskList taskList) throws CbtException {
        int marker = details.indexOf(" /by ");
        int byLength = " /by ".length();
        if (marker <= 0 || details.substring(marker + byLength).isBlank()) {
            throw new CbtException("Use: deadline DESCRIPTION /by DATE_OR_TIME");
        }
        String description = details.substring(0, marker).trim();
        String by = details.substring(marker + byLength).trim();
        Deadline task = new Deadline(description, by);
        taskList.addTask(task);
    }
}

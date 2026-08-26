package command;

import exception.CbtException;
import task.TaskList;

/** Represents one action the user can perform on a task list. */
public interface Command {
    /** Executes this action against the supplied task list. */
    CommandResult execute(TaskList taskList) throws CbtException;
}

package command;

import exception.CbtException;
import task.TaskList;

/** Represents one action the user can perform on a task list. */
public interface Command {
    /** Executes this action against the supplied task list. */
    void execute(TaskList taskList) throws CbtException;

    /** Returns whether successfully executing this command changes the task list. */
    default boolean changesTaskList() {
        return false;
    }
}

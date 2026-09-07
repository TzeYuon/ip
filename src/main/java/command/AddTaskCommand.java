package command;

import task.Task;
import task.TaskList;

/** Provides behavior shared by commands that add a task. */
abstract class AddTaskCommand implements Command {
    /**
     * Adds a task and returns the standard result for a successful addition.
     *
     * @param tasks task list to update.
     * @param task task to add.
     * @return result describing the added task.
     */
    protected CommandResult addTask(TaskList tasks, Task task) {
        tasks.addTask(task);
        String message = "Got it. I've added this task:\n"
                + "  " + task + "\n"
                + "Now you have " + tasks.getSize() + " task"
                + (tasks.getSize() == 1 ? "" : "s") + " in the list.";
        return new CommandResult(message, true, false);
    }
}

package command;

import exception.CbtException;
import task.TaskList;
import task.Todo;

/** Adds a todo task. */
public class TodoCommand implements Command {
    private final String description;

    /**
     * Creates a command for the supplied todo description.
     *
     * @param description description of the todo.
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    /**
     * Validates and adds a todo to the task list.
     *
     * @param tasks task list to update.
     * @return result describing the added todo.
     * @throws CbtException if the description is blank.
     */
    @Override
    public CommandResult execute(TaskList tasks) throws CbtException {
        if (description.isBlank()) {
            throw new CbtException("The description of a todo cannot be empty. Use: todo DESCRIPTION");
        }
        Todo task = new Todo(description);
        tasks.addTask(task);
        String message = "Got it. I've added this task:\n" + "  " + task + "\n"
                + "Now you have " + tasks.getSize() + " task"
                + (tasks.getSize() == 1 ? "" : "s") + " in the list.";
        return new CommandResult(message, true, false);
    }
}

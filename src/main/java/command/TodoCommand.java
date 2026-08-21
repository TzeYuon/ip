package command;

import exception.CbtException;
import task.TaskList;
import task.Todo;

/** Adds a todo task. */
public class TodoCommand implements Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList taskList) throws CbtException {
        if (description.isBlank()) {
            throw new CbtException("The description of a todo cannot be empty. Use: todo DESCRIPTION");
        }
        Todo task = new Todo(description);
        taskList.addTask(task);
    }
}

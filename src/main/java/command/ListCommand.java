package command;

import exception.CbtException;
import task.TaskList;

/** Displays every task in the list. */
public class ListCommand implements Command {
    /**
     * Prints every task in its current list order.
     *
     * @param taskList task list to display.
     * @return result indicating that no application state changed.
     * @throws CbtException if a task cannot be retrieved for display.
     */
    @Override
    public CommandResult execute(TaskList taskList) throws CbtException {
        System.out.println("Here are the tasks in your list:");
        taskList.printTasks();
        return new CommandResult("", false, false);
    }
}

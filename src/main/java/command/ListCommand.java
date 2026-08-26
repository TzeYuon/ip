package command;

import exception.CbtException;
import task.TaskList;

import java.util.List;

/** Displays every task in the list. */
public class ListCommand implements Command {
    @Override
    public CommandResult execute(TaskList taskList) throws CbtException {
        System.out.println("Here are the tasks in your list:");
        taskList.printTasks();
        return new CommandResult("", false, false);
    }
}

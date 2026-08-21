package command;

import exception.CbtException;
import task.TaskList;

/** Displays every task in the list. */
public class ListCommand implements Command {
    @Override
    public void execute(TaskList taskList) throws CbtException {
        System.out.println("Here are the tasks in your list:");
        for (int index = 0; index < taskList.getSize(); index++) {
            System.out.println(index + 1 + "." + taskList.getTask(index));
        }
    }
}

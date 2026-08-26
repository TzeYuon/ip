package command;

import task.TaskList;

/** Tells the application to end its command loop. */
public class ExitCommand implements Command{
    @Override
    public CommandResult execute(TaskList tasks) {
        return new CommandResult("Bye. Hope to see you again soon!", false, true);
    }
}

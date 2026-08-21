package command;

import exception.CbtException;
import task.Event;
import task.TaskList;

/** Adds an event task with user-provided start and end text. */
public class EventCommand implements Command {
    private final String details;

    public EventCommand(String details) {
        this.details = details;
    }

    @Override
    public void execute(TaskList taskList) throws CbtException {
        int fromMarker = details.indexOf(" /from ");
        int toMarker = details.indexOf(" /to ");
        int startLength = " /from ".length();
        int endLength = " /to ".length();
        if (fromMarker <= 0 || toMarker <= fromMarker + startLength
                || details.substring(fromMarker + startLength, toMarker).isBlank() || details.substring(toMarker + endLength).isBlank()) {
            throw new CbtException("Use: event DESCRIPTION /from START /to END");
        }
        Event task = new Event(details.substring(0, fromMarker).trim(),
                details.substring(fromMarker + startLength, toMarker).trim(), details.substring(toMarker + endLength).trim());
        taskList.addTask(task);
    }
}

package command;

import exception.CbtException;
import parser.Parser;
import task.Event;
import task.TaskList;

import java.time.LocalDateTime;

/** Adds an event task with parsed start and end dates and times. */
public class EventCommand implements Command {
    private final String details;

    public EventCommand(String details) {
        this.details = details;
    }

    @Override
    public CommandResult execute(TaskList tasks) throws CbtException {
        int fromMarker = details.indexOf(" /from ");
        int toMarker = details.indexOf(" /to ");
        int startLength = " /from ".length();
        int endLength = " /to ".length();
        if (fromMarker <= 0 || toMarker <= fromMarker + startLength) {
            throw new CbtException("Use: event DESCRIPTION /from START /to END");
        }
        String startString = details.substring(fromMarker + startLength, toMarker).trim();
        String endString = details.substring(toMarker + endLength).trim();
        if (startString.isBlank() || endString.isBlank()) {
            throw new CbtException("Use: event DESCRIPTION /from START /to END");
        }
        LocalDateTime startDate = Parser.parseLineToDate(startString);
        LocalDateTime endDate = Parser.parseLineToDate(endString);
        Event task = new Event(details.substring(0, fromMarker).trim(), startDate, endDate);
        tasks.addTask(task);
        String message = "Got it. I've added this task:\n" +
                "  " + task + "\n" + "Now you have " + tasks.getSize() + " task" +
                (tasks.getSize() == 1 ? "" : "s") + " in the list.";
        return new CommandResult(message, true, false);
    }
}

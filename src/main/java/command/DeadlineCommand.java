package command;

import exception.CbtException;
import task.Deadline;
import parser.Parser;
import task.TaskList;

import java.time.LocalDateTime;

/** Adds a deadline task with a parsed date and time. */
public class DeadlineCommand implements Command {
    private final String details;

    public DeadlineCommand(String details) {
        this.details = details;
    }

    @Override
    public CommandResult execute(TaskList tasks) throws CbtException {
        int marker = details.indexOf(" /by ");
        int byLength = " /by ".length();
        if (marker <= 0 || details.substring(marker + byLength).isBlank()) {
            throw new CbtException("Use: deadline DESCRIPTION /by DATE_OR_TIME");
        }
        String description = details.substring(0, marker).trim();
        String byString = details.substring(marker + byLength).trim();

        LocalDateTime byDate = Parser.parseLineToDate(byString);
        Deadline task = new Deadline(description, byDate);
        tasks.addTask(task);
        String message = "Got it. I've added this task:\n" + "  " + task + "\n" +
                "Now you have " + tasks.getSize() + " task" +
                (tasks.getSize() == 1 ? "" : "s") + " in the list.";
        return new CommandResult(message, true, false);
    }
}

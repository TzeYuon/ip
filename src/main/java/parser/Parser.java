package parser;

import command.Command;
import command.DeadlineCommand;
import command.DeleteCommand;
import command.EventCommand;
import command.ListCommand;
import command.MarkCommand;
import command.TodoCommand;
import command.UnmarkCommand;
import exception.CbtException;

/** Converts a raw user command into the command object that performs it. */
public class Parser {
    /** Parses one input line without changing the task list. */
    public Command parse(String fullCommand) throws CbtException {
        String[] parts = fullCommand.trim().split("\\s+", 2);
        String keyword = parts[0].toLowerCase();
        String arguments = parts.length == 2 ? parts[1] : "";
        return switch (keyword) {
        case "todo" -> new TodoCommand(arguments);
        case "deadline" -> new DeadlineCommand(arguments);
        case "event" -> new EventCommand(arguments);
        case "list" -> new ListCommand();
        case "mark" -> new MarkCommand(arguments);
        case "unmark" -> new UnmarkCommand(arguments);
        case "delete" -> new DeleteCommand(arguments);
        default -> throw new CbtException("I don't understand that command. Try todo, deadline, event, list, mark, unmark, delete, or bye.");
        };
    }
}

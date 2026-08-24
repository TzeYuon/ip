package parser;

import command.*;
import exception.CbtException;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

/** Converts a raw user command into the command object that performs it. */
public class Parser {
    /** Parses one input line without changing the task list. */
    public Command parseCommand(String fullCommand) throws CbtException {
        String[] parts = fullCommand.trim().split("\\s+", 2);
        String keyword = parts[0].toUpperCase();
        String arguments = parts.length == 2 ? parts[1].trim() : "";

        CommandWord commandWord;
        try {
            commandWord = CommandWord.valueOf(keyword);
        } catch (IllegalArgumentException e) {
            commandWord = CommandWord.UNKNOWN;
        }

        return switch (commandWord) {
        case CommandWord.TODO -> new TodoCommand(arguments);
        case CommandWord.DEADLINE -> new DeadlineCommand(arguments);
        case CommandWord.EVENT -> new EventCommand(arguments);
        case CommandWord.LIST -> new ListCommand();
        case CommandWord.MARK -> new MarkCommand(arguments);
        case CommandWord.UNMARK -> new UnmarkCommand(arguments);
        case CommandWord.DELETE -> new DeleteCommand(arguments);
        case CommandWord.UNKNOWN -> throw new CbtException("I don't understand that command. " +
                "Try todo, deadline, event, list, mark, unmark, delete, or bye.");
        default -> throw new CbtException("I don't understand that command. " +
                "Try todo, deadline, event, list, mark, unmark, delete, or bye.");
        };
    }

    /** Converts one stored task line to a task, or returns {@code null} for malformed data. */
    public Task parseLineToTask(String line) {
        String[] parts = line.split(" \\| ", -1);
        if (parts.length < 3) {
            return null;
        }
        String type = parts[0].toUpperCase();
        CommandWord commandWord;
        try {
            commandWord = CommandWord.valueOf(type);
        } catch (IllegalArgumentException e) {
            commandWord = CommandWord.UNKNOWN;
        }

        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (commandWord) {
            case CommandWord.TODO -> task = new Todo(description);
            case CommandWord.DEADLINE -> task = parts.length == 4 ? new Deadline(description, parts[3]) : null;
            case CommandWord.EVENT -> task = parts.length == 5 ? new Event(description, parts[3], parts[4]) : null;
            default -> task = null;
        }

        if (task != null) {
            task.restoreCompletionStatus(isDone);
        }
        return task;
    }
}

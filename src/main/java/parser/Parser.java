package parser;

import command.*;
import exception.CbtException;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

/** Converts a raw user command into the command object that performs it. */
public class Parser {
    private static final List<DateTimeFormatter> DATE_TIME_FORMATTERS = List.of(
            strictFormatter("d/M/uuuu HHmm"),   // 2/12/2019 1800
            strictFormatter("d-M-uuuu HHmm"),   // 2-12-2019 1800
            strictFormatter("uuuu-MM-dd HHmm"), // 2019-12-02 1800
            strictFormatter("d/M/uuuu HH:mm"),  // 2/12/2019 18:00
            strictFormatter("d-M-uuuu HH:mm"),  // 2-12-2019 18:00
            strictFormatter("uuuu-MM-dd HH:mm") // 2019-12-02 18:00
    );

    /** Supported formats for a date without a time. */
    private static final List<DateTimeFormatter> DATE_ONLY_FORMATTERS = List.of(
            strictFormatter("d/M/uuuu"),   // 2/12/2019
            strictFormatter("d-M-uuuu"),   // 2-12-2019
            strictFormatter("uuuu-MM-dd")  // 2019-12-02
    );
    private static final DateTimeFormatter DATE_PRINT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd uuuu");
    /** Parses one input line without changing the task list. */
    public static Command parseCommand(String fullCommand) throws CbtException {
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
        case CommandWord.LISTDATE -> new ListDateCommand(arguments);
        case CommandWord.LIST -> new ListCommand();
        case CommandWord.MARK -> new MarkCommand(arguments);
        case CommandWord.UNMARK -> new UnmarkCommand(arguments);
        case CommandWord.DELETE -> new DeleteCommand(arguments);
        case CommandWord.BYE -> new ExitCommand();
        case CommandWord.UNKNOWN -> throw new CbtException("I don't understand that command. " +
                "Try todo, deadline, event, date, list, listdate, mark, unmark, delete, or bye.");
        };
    }

    /** Converts one stored task line to a task, or returns {@code null} for malformed data. */
    public static Task parseLineToTask(String line) {
        String[] parts = line.split(" \\| ", -1);
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0].toUpperCase();
        boolean isDone = parts[1].equals("1");
        String description = parts[2].trim();

        CommandWord commandWord;
        try {
            commandWord = CommandWord.valueOf(type);
        } catch (IllegalArgumentException e) {
            return null;
        }

        Task task = null;
        switch (commandWord) {
            case CommandWord.TODO:
                for (int i = 3; i < parts.length; i ++) {
                    description += " | " + parts[i];
                }
                task = new Todo(description);
                break;
            case CommandWord.DEADLINE:
                for (int i = 3; i < parts.length - 1; i ++) {
                    description += " | " + parts[i];
                }
                try {
                    task = new Deadline(description, parseLineToDate(parts[parts.length - 1]));
                } catch(CbtException e) {
                    System.out.println("Invalid Date format for DEADLINE stored in CBT.txt");
                }
                break;
            case CommandWord.EVENT:
                for (int i = 3; i < parts.length - 2; i ++) {
                    description += " | " + parts[i];
                }
                try {
                    LocalDateTime startDate = parseLineToDate(parts[parts.length - 2]);
                    LocalDateTime endDate = parseLineToDate(parts[parts.length - 1]);
                    if (startDate.isAfter(endDate)) {
                        throw new CbtException("Event start date is after end date");
                    }
                    task = new Event(description, startDate, endDate);
                } catch(CbtException e) {
                    System.out.println("Invalid Date format for EVENT stored in CBT.txt");
                }
                break;
        }

        if (task != null) {
            task.restoreCompletionStatus(isDone);
        }
        return task;
    }

    /** Parses a supported date or date-time input, using midnight for a date-only input. */
    public static LocalDateTime parseLineToDate(String line) throws CbtException {
        line = line.trim();
        for (DateTimeFormatter formatter : DATE_TIME_FORMATTERS) {
            try {
                return LocalDateTime.parse(line, formatter);
            } catch (DateTimeParseException ignored) {
                // Try the next supported format.
            }
        }
        for (DateTimeFormatter formatter : DATE_ONLY_FORMATTERS) {
            try {
                LocalDate date = LocalDate.parse(line, formatter);
                return date.atTime(0, 0);
            } catch (DateTimeParseException ignored) {
                // Try the next supported format.
            }
        }
        throw new CbtException("Cannot recognize date/time! Example valid formats:\n"
                + "  - 2/12/2019 1800\n"
                + "  - 2-12-2019 1800\n"
                + "  - 2019-12-02 1800\n"
                + "  - 2/12/2019");
    }

    /** Parses a date without accepting a time component. */
    public static LocalDate parseDate(String line) throws CbtException {
        for (DateTimeFormatter formatter : DATE_ONLY_FORMATTERS) {
            try {
                return LocalDate.parse(line.trim(), formatter);
            } catch (DateTimeParseException ignored) {
                // Try the next supported format.
            }
        }
        throw new CbtException("Cannot recognize date! Use a date such as 2019-12-02.");
    }

    /** Formats a date for display. */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_PRINT_FORMATTER);
    }

    private static DateTimeFormatter strictFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
    }
}

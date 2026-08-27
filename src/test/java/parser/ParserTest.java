package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import command.ExitCommand;
import command.FindCommand;
import command.ListDateCommand;
import command.TodoCommand;
import exception.CbtException;
import task.Task;
import task.Todo;

/** Tests conversion of user and storage text into application objects. */
public class ParserTest {
    /** Verifies that known command words produce the expected command types. */
    @Test
    public void parseCommand_validCommands_correctCommandTypes() throws CbtException {
        assertInstanceOf(TodoCommand.class, Parser.parseCommand("todo read book"));
        assertInstanceOf(ListDateCommand.class, Parser.parseCommand("date 2019-12-02"));
        assertInstanceOf(ListDateCommand.class, Parser.parseCommand("listdate 2019-12-02"));
        assertInstanceOf(FindCommand.class, Parser.parseCommand("find book"));
        assertInstanceOf(ExitCommand.class, Parser.parseCommand("bye"));
    }

    /** Verifies that an unknown command word is rejected. */
    @Test
    public void parseCommand_unknownCommand_exceptionThrown() {
        assertThrows(CbtException.class, () -> Parser.parseCommand("dance"));
    }

    /** Verifies parsing of each supported date-time format. */
    @Test
    public void parseLineToDate_supportedDateTimeFormats_correctDateTime() throws CbtException {
        LocalDateTime expected = LocalDateTime.of(2019, 12, 2, 18, 0);

        assertEquals(expected, Parser.parseLineToDate("2/12/2019 1800"));
        assertEquals(expected, Parser.parseLineToDate("2-12-2019 18:00"));
        assertEquals(expected, Parser.parseLineToDate("2019-12-02 1800"));
    }

    /** Verifies that a date without a time is interpreted as midnight. */
    @Test
    public void parseLineToDate_dateWithoutTime_midnightUsed() throws CbtException {
        assertEquals(LocalDateTime.of(2019, 12, 2, 0, 0),
                Parser.parseLineToDate("2019-12-02"));
    }

    /** Verifies that malformed and impossible dates are rejected. */
    @Test
    public void parseLineToDate_invalidAndImpossibleDates_exceptionThrown() {
        assertThrows(CbtException.class, () -> Parser.parseLineToDate("not a date"));
        assertThrows(CbtException.class, () -> Parser.parseLineToDate("31/02/2025"));
    }

    /** Verifies that the date-only parser rejects input containing a time. */
    @Test
    public void parseDate_dateTimeProvided_exceptionThrown() {
        assertThrows(CbtException.class, () -> Parser.parseDate("2019-12-02 1800"));
    }

    /** Verifies reconstruction of a completed todo whose description contains a divider. */
    @Test
    public void parseLineToTask_completedTodoWithDivider_contentAndStatusRestored() {
        Task task = Parser.parseLineToTask("TODO | 1 | read | chapter one");

        assertInstanceOf(Todo.class, task);
        assertEquals("[T][X] read | chapter one", task.toString());
        assertEquals("TODO | 1 | read | chapter one", task.toFileFormat());
    }

    /** Verifies that malformed and unknown stored records return {@code null}. */
    @Test
    public void parseLineToTask_malformedOrUnknownRecord_nullReturned() {
        assertNull(Parser.parseLineToTask("missing fields"));
        assertNull(Parser.parseLineToTask("REMINDER | 0 | call Alex"));
    }

    /** Verifies that dates use the expected user-facing display format. */
    @Test
    public void formatDate_validDate_userFacingFormatReturned() {
        assertEquals("Dec 02 2019", Parser.formatDate(LocalDate.of(2019, 12, 2)));
    }
}

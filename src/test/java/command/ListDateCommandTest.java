package command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import exception.CbtException;
import task.Deadline;
import task.TaskList;

/** Tests filtering displayed tasks by date. */
public class ListDateCommandTest {
    /** Verifies that only tasks occurring on the requested date are printed. */
    @Test
    public void execute_matchingAndNonMatchingTasks_onlyMatchingTaskPrinted() throws CbtException {
        TaskList tasks = new TaskList();
        tasks.addTask(new Deadline("matching", LocalDateTime.of(2026, 8, 26, 12, 0)));
        tasks.addTask(new Deadline("different", LocalDateTime.of(2026, 8, 27, 12, 0)));
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        CommandResult result;
        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            result = new ListDateCommand("2026-08-26").execute(tasks);
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString(StandardCharsets.UTF_8);
        assertTrue(printed.contains("deadlines and events on Aug 26 2026"));
        assertTrue(printed.contains("matching"));
        assertFalse(printed.contains("different"));
        assertFalse(result.taskListChanged());
    }

    /** Verifies that blank and invalid dates are rejected. */
    @Test
    public void execute_blankOrInvalidDate_exceptionThrown() {
        TaskList tasks = new TaskList();

        assertThrows(CbtException.class, () -> new ListDateCommand(" ").execute(tasks));
        assertThrows(CbtException.class, () -> new ListDateCommand("31/02/2026").execute(tasks));
    }
}

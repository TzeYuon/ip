package command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import exception.CbtException;
import task.TaskList;
import task.Todo;

/** Tests finding tasks by keywords in their descriptions. */
public class FindCommandTest {
    /** Verifies that matching tasks are renumbered and the task list is unchanged. */
    @Test
    public void execute_matchingKeyword_onlyMatchingTasksPrinted() throws CbtException {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("read book"));
        tasks.addTask(new Todo("write report"));
        tasks.addTask(new Todo("return book"));
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        CommandResult result;
        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            result = new FindCommand("book").execute(tasks);
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString(StandardCharsets.UTF_8);
        assertTrue(printed.contains("Here are the matching tasks in your list:"));
        assertTrue(printed.contains("1.[T][ ] read book"));
        assertTrue(printed.contains("2.[T][ ] return book"));
        assertFalse(printed.contains("write report"));
        assertFalse(result.taskListChanged());
        assertFalse(result.exit());
    }

    /** Verifies that a blank keyword is rejected. */
    @Test
    public void execute_blankKeyword_exceptionThrown() {
        assertThrows(CbtException.class, () -> new FindCommand(" ").execute(new TaskList()));
    }
}

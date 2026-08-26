package command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import exception.CbtException;
import task.TaskList;
import task.Todo;

/** Tests listing all tasks without modifying them. */
public class ListCommandTest {
    @Test
    public void execute_multipleTasks_numberedTasksPrintedAndUnchangedResultReturned() throws CbtException {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        CommandResult result;
        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            result = new ListCommand().execute(tasks);
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString(StandardCharsets.UTF_8);
        assertTrue(printed.contains("Here are the tasks in your list:"));
        assertTrue(printed.contains("1.[T][ ] first"));
        assertTrue(printed.contains("2.[T][ ] second"));
        assertEquals(2, tasks.getSize());
        assertFalse(result.taskListChanged());
        assertFalse(result.exit());
    }
}

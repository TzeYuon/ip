package command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import exception.CbtException;
import task.TaskList;
import task.Todo;

/** Tests listing all tasks without modifying them. */
public class ListCommandTest {
    /** Verifies that listing returns numbered tasks without changing application state. */
    @Test
    public void execute_multipleTasks_numberedTasksAndUnchangedResultReturned() throws CbtException {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));

        CommandResult result = new ListCommand().execute(tasks);

        assertTrue(result.message().contains("Here are the tasks in your list:"));
        assertTrue(result.message().contains("1.[T][ ] first"));
        assertTrue(result.message().contains("2.[T][ ] second"));
        assertEquals(2, tasks.getSize());
        assertFalse(result.taskListChanged());
        assertFalse(result.exit());
    }
}

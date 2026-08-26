package command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import task.TaskList;

/** Tests the result used to terminate the application. */
public class ExitCommandTest {
    @Test
    public void execute_anyTaskList_exitResultReturnedWithoutMutation() {
        TaskList tasks = new TaskList();

        CommandResult result = new ExitCommand().execute(tasks);

        assertEquals("Bye. Hope to see you again soon!", result.message());
        assertFalse(result.taskListChanged());
        assertTrue(result.exit());
        assertEquals(0, tasks.getSize());
    }
}

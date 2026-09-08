package command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import exception.CbtException;
import task.TaskList;

/** Tests creation of todo tasks from commands. */
public class TodoCommandTest {
    /** Verifies that a valid description adds a todo and reports a state change. */
    @Test
    public void execute_validDescription_taskAddedAndChangedResultReturned() throws CbtException {
        TaskList tasks = new TaskList();

        CommandResult result = new TodoCommand("read book").execute(tasks);

        assertEquals(1, tasks.getSize());
        assertEquals("[T][ ] read book", tasks.getTask(0).toString());
        assertTrue(result.taskListChanged());
        assertFalse(result.isExit());
        assertTrue(result.message().contains("Now you have 1 task in the list."));
    }

    /** Verifies that a blank todo description is rejected without changing the list. */
    @Test
    public void execute_blankDescription_exceptionThrownAndListUnchanged() {
        TaskList tasks = new TaskList();

        assertThrows(CbtException.class, () -> new TodoCommand("   ").execute(tasks));
        assertEquals(0, tasks.getSize());
    }
}

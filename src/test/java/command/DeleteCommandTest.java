package command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import exception.CbtException;
import task.TaskList;
import task.Todo;

/** Tests deletion by the one-based task number entered by a user. */
public class DeleteCommandTest {
    /** Verifies that a valid one-based task number deletes and reports the selected task. */
    @Test
    public void execute_validTaskNumber_taskDeleted() throws CbtException {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("first"));
        tasks.addTask(new Todo("second"));

        CommandResult result = new DeleteCommand("1").execute(tasks);

        assertEquals(1, tasks.getSize());
        assertEquals("[T][ ] second", tasks.getTask(0).toString());
        assertTrue(result.taskListChanged());
        assertTrue(result.message().contains("[T][ ] first"));
    }

    /** Verifies that an out-of-range task number leaves the list unchanged. */
    @Test
    public void execute_invalidTaskNumber_exceptionThrownAndListUnchanged() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("keep me"));

        assertThrows(CbtException.class, () -> new DeleteCommand("2").execute(tasks));
        assertEquals(1, tasks.getSize());
    }
}

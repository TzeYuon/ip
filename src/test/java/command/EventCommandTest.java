package command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import exception.CbtException;
import task.TaskList;

/** Tests parsing and creation of event tasks. */
public class EventCommandTest {
    @Test
    public void execute_validDetails_eventAdded() throws CbtException {
        TaskList tasks = new TaskList();

        CommandResult result = new EventCommand(
                "meeting /from 2/12/2019 0900 /to 2/12/2019 1000").execute(tasks);

        assertEquals(1, tasks.getSize());
        assertEquals("EVENT | 0 | meeting | 02/12/2019 0900 | 02/12/2019 1000",
                tasks.getTask(0).toFileFormat());
        assertTrue(result.taskListChanged());
    }

    @Test
    public void execute_missingMarkersOrTimes_exceptionThrown() {
        TaskList tasks = new TaskList();

        assertThrows(CbtException.class, () -> new EventCommand("meeting").execute(tasks));
        assertThrows(CbtException.class,
                () -> new EventCommand("meeting /from 2/12/2019 /to ").execute(tasks));
    }

    @Test
    public void execute_startAfterEnd_exceptionThrownAndListUnchanged() {
        TaskList tasks = new TaskList();

        assertThrows(CbtException.class, () -> new EventCommand(
                "meeting /from 3/12/2019 1000 /to 2/12/2019 1000").execute(tasks));
        assertEquals(0, tasks.getSize());
    }
}

package command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import exception.CbtException;
import task.TaskList;
import task.Todo;

/** Tests task-number conversion and task completion. */
public class MarkCommandTest {
    @Test
    public void execute_validTaskNumber_taskMarked() throws CbtException {
        TaskList tasks = new TaskList();
        Todo todo = new Todo("read book");
        tasks.addTask(todo);

        CommandResult result = new MarkCommand("1").execute(tasks);

        assertTrue(todo.isDone());
        assertTrue(result.taskListChanged());
        assertFalse(result.exit());
        assertTrue(result.message().contains("[T][X] read book"));
    }

    @Test
    public void toIndex_validOneBasedNumber_zeroBasedIndexReturned() throws CbtException {
        assertEquals(0, MarkCommand.toIndex("1"));
        assertEquals(11, MarkCommand.toIndex(" 12 "));
    }

    @Test
    public void toIndex_zeroNegativeOrNonNumeric_exceptionThrown() {
        assertThrows(CbtException.class, () -> MarkCommand.toIndex("0"));
        assertThrows(CbtException.class, () -> MarkCommand.toIndex("-1"));
        assertThrows(CbtException.class, () -> MarkCommand.toIndex("first"));
    }

    @Test
    public void execute_taskNumberPastEnd_exceptionThrown() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("only task"));

        assertThrows(CbtException.class, () -> new MarkCommand("2").execute(tasks));
    }
}

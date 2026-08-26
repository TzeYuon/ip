package command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import exception.CbtException;
import task.TaskList;
import task.Todo;

/** Tests changing completed tasks back to incomplete. */
public class UnmarkCommandTest {
    @Test
    public void execute_completedTask_taskUnmarked() throws CbtException {
        TaskList tasks = new TaskList();
        Todo todo = new Todo("read book");
        todo.markAsDone();
        tasks.addTask(todo);

        CommandResult result = new UnmarkCommand("1").execute(tasks);

        assertFalse(todo.isDone());
        assertTrue(result.taskListChanged());
        assertTrue(result.message().contains("[T][ ] read book"));
    }

    @Test
    public void execute_invalidTaskNumber_exceptionThrown() {
        assertThrows(CbtException.class,
                () -> new UnmarkCommand("1").execute(new TaskList()));
    }
}

package command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import exception.CbtException;
import task.TaskList;
import task.Todo;

/** Tests finding tasks by keywords in their descriptions. */
public class FindCommandTest {
    /** Verifies that matching tasks are renumbered and the task list is unchanged. */
    @Test
    public void execute_matchingKeyword_onlyMatchingTasksReturned() throws CbtException {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("read book"));
        tasks.addTask(new Todo("write report"));
        tasks.addTask(new Todo("return book"));
        CommandResult result = new FindCommand("book").execute(tasks);

        assertTrue(result.message().contains("Here are the matching tasks in your list:"));
        assertTrue(result.message().contains("1.[T][ ] read book"));
        assertTrue(result.message().contains("2.[T][ ] return book"));
        assertFalse(result.message().contains("write report"));
        assertFalse(result.taskListChanged());
        assertFalse(result.isExit());
    }

    /** Verifies that a blank keyword is rejected. */
    @Test
    public void execute_blankKeyword_exceptionThrown() {
        assertThrows(CbtException.class, () -> new FindCommand(" ").execute(new TaskList()));
    }
}

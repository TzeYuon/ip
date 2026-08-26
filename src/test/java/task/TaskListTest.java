package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import exception.CbtException;

/** Tests task collection mutations, validation, and date filtering. */
public class TaskListTest {
    /** Verifies that tasks are appended and retain their insertion order. */
    @Test
    public void addTask_validTask_taskStoredAtEnd() throws CbtException {
        TaskList tasks = new TaskList();
        Todo first = new Todo("first");
        Todo second = new Todo("second");

        tasks.addTask(first);
        tasks.addTask(second);

        assertEquals(2, tasks.getSize());
        assertSame(first, tasks.getTask(0));
        assertSame(second, tasks.getTask(1));
    }

    /** Verifies that negative and past-the-end indexes are rejected. */
    @Test
    public void getTask_invalidIndexes_exceptionThrown() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("only task"));

        assertThrows(CbtException.class, () -> tasks.getTask(-1));
        assertThrows(CbtException.class, () -> tasks.getTask(1));
    }

    /** Verifies that deletion removes and returns the selected task. */
    @Test
    public void deleteTask_validIndex_taskRemovedAndReturned() throws CbtException {
        TaskList tasks = new TaskList();
        Todo removed = new Todo("remove me");
        Todo retained = new Todo("keep me");
        tasks.addTask(removed);
        tasks.addTask(retained);

        Task result = tasks.deleteTask(0);

        assertSame(removed, result);
        assertEquals(1, tasks.getSize());
        assertSame(retained, tasks.getTask(0));
    }

    /** Verifies that marking and unmarking update the selected task's completion state. */
    @Test
    public void markAndUnmarkTask_validIndex_completionStatusChanged() throws CbtException {
        TaskList tasks = new TaskList();
        Todo todo = new Todo("read book");
        tasks.addTask(todo);

        assertSame(todo, tasks.markTask(0));
        assertTrue(todo.isDone());

        assertSame(todo, tasks.unmarkTask(0));
        assertFalse(todo.isDone());
    }

    /** Verifies that date filtering returns only matching dated tasks in list order. */
    @Test
    public void findTasksOn_mixedTasks_onlyTasksOccurringOnDateReturned() throws CbtException {
        LocalDate targetDate = LocalDate.of(2026, 8, 26);
        Todo todo = new Todo("undated task");
        Deadline deadline = new Deadline("submit", targetDate.atTime(23, 59));
        Event spanningEvent = new Event("camp",
                targetDate.minusDays(1).atStartOfDay(),
                targetDate.plusDays(1).atStartOfDay());
        Deadline otherDeadline = new Deadline("later", targetDate.plusDays(1).atStartOfDay());
        TaskList tasks = new TaskList();
        tasks.addTask(todo);
        tasks.addTask(deadline);
        tasks.addTask(spanningEvent);
        tasks.addTask(otherDeadline);

        TaskList result = tasks.findTasksOn(targetDate);

        assertEquals(2, result.getSize());
        assertSame(deadline, result.getTask(0));
        assertSame(spanningEvent, result.getTask(1));
        assertEquals(4, tasks.getSize());
    }

    /** Verifies that date filtering returns an empty list when no tasks match. */
    @Test
    public void findTasksOn_noMatches_emptyTaskListReturned() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Deadline("submit",
                LocalDateTime.of(2026, 8, 27, 12, 0)));

        assertEquals(0, tasks.findTasksOn(LocalDate.of(2026, 8, 26)).getSize());
    }
}

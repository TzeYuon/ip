package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

/** Tests todo formatting, status, and lack of date information. */
public class TodoTest {
    /** Verifies that a todo can be constructed with a valid description. */
    @Test
    public void constructor_validString_todoCreated() {
        assertDoesNotThrow(() -> new Todo("wash the dishes"));
    }

    /** Verifies todo display and storage formats as its completion state changes. */
    @Test
    public void markAndUnmark_statusAndFormatsUpdated() {
        Todo todo = new Todo("wash the dishes");

        assertEquals("[T][ ] wash the dishes", todo.toString());
        assertEquals("TODO | 0 | wash the dishes", todo.toFileFormat());
        todo.markAsDone();
        assertEquals("[T][X] wash the dishes", todo.toString());
        assertEquals("TODO | 1 | wash the dishes", todo.toFileFormat());
        todo.markAsNotDone();
        assertFalse(todo.isDone());
    }

    /** Verifies that an undated todo never occurs on a particular date. */
    @Test
    public void occursOn_anyDate_falseReturned() {
        Todo todo = new Todo("wash the dishes");

        assertFalse(todo.occursOn(LocalDate.of(2026, 8, 26)));
    }
}

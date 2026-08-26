package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

/** Tests todo formatting, status, and lack of date information. */
public class TodoTest {
    @Test
    public void constructor_validString_todoCreated() {
        assertDoesNotThrow(() -> new Todo("wash the dishes"));
    }

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

    @Test
    public void occursOn_anyDate_falseReturned() {
        Todo todo = new Todo("wash the dishes");

        assertFalse(todo.occursOn(LocalDate.of(2026, 8, 26)));
    }
}

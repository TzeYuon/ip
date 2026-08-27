package task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/** Tests deadline formatting, completion state, and date matching. */
public class DeadlineTest {
    private static final LocalDateTime BY = LocalDateTime.of(2026, 8, 25, 10, 0);

    @Test
    public void constructor_validTime_deadlineCreated() {
        assertDoesNotThrow(() -> new Deadline("study", BY));
    }

    @Test
    public void occursOn_sameAndDifferentDates_correctBooleanReturned() {
        Deadline deadline = new Deadline("study", BY);

        assertTrue(deadline.occursOn(LocalDate.of(2026, 8, 25)));
        assertFalse(deadline.occursOn(LocalDate.of(2026, 8, 24)));
        assertFalse(deadline.occursOn(LocalDate.of(2026, 8, 26)));
    }

    @Test
    public void toFileFormat_incompleteThenComplete_correctStatusStored() {
        Deadline deadline = new Deadline("study", BY);

        assertEquals("DEADLINE | 0 | study | 25/08/2026 1000", deadline.toFileFormat());
        deadline.markAsDone();
        assertEquals("DEADLINE | 1 | study | 25/08/2026 1000", deadline.toFileFormat());
    }
}

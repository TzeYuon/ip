package task;

import exception.CbtException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests deadline formatting, completion state, and date matching. */
public class DeadlineTest {
    private static final LocalDateTime BY = LocalDateTime.of(2026, 8, 25, 10, 0);

    /** Verifies that a deadline can be constructed with a valid date and time. */
    @Test
    public void constructor_validTime_deadlineCreated() {
        assertDoesNotThrow(() -> new Deadline("study", BY));
    }

    /** Verifies that a deadline occurs only on its deadline date. */
    @Test
    public void occursOn_sameAndDifferentDates_correctBooleanReturned() {
        Deadline deadline = new Deadline("study", BY);

        assertTrue(deadline.occursOn(LocalDate.of(2026, 8, 25)));
        assertFalse(deadline.occursOn(LocalDate.of(2026, 8, 24)));
        assertFalse(deadline.occursOn(LocalDate.of(2026, 8, 26)));
    }

    /** Verifies serialization of incomplete and completed deadlines. */
    @Test
    public void toFileFormat_incompleteThenComplete_correctStatusStored() {
        Deadline deadline = new Deadline("study", BY);

        assertEquals("DEADLINE | 0 | study | 25/08/2026 1000", deadline.toFileFormat());
        deadline.markAsDone();
        assertEquals("DEADLINE | 1 | study | 25/08/2026 1000", deadline.toFileFormat());
    }
}

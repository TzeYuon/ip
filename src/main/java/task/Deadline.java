package task;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {

    private final LocalDateTime by;

    /**
     * Creates an incomplete deadline.
     *
     * @param description description of the deadline
     * @param by deadline date and time
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns this deadline in its user-facing format.
     *
     * @return formatted deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DATE_TIME_PRINT_FORMATTER) + ")";
    }

    @Override
    public String toFileFormat() {
        return "DEADLINE | " + (isDone ? "1" : "0") + " | " + description + " | "
                + by.format(DATE_TIME_WRITE_FORMATTER);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return by.toLocalDate().equals(date);
    }
}

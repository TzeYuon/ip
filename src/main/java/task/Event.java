package task;

import exception.CbtException;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a task with a start and end date and time.
 */
public class Event extends Task {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    /**
     * Creates an incomplete event.
     *
     * @param description description of the event
     * @param startDate start date and time
     * @param endDate end date and time
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) throws CbtException {
        super(description);
        if (startDate.isAfter(endDate)) {
            throw new CbtException("The event start date and time cannot be after its end date and time.");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns this event in its user-facing format.
     *
     * @return formatted event
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDate.format(DATE_TIME_PRINT_FORMATTER)
                + " to: " + endDate.format(DATE_TIME_PRINT_FORMATTER) + ")";
    }

    @Override
    public String toFileFormat() {
        return "EVENT | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | "
                + startDate.format(DATE_TIME_WRITE_FORMATTER) + " | " + endDate.format(DATE_TIME_WRITE_FORMATTER);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return !date.isBefore(startDate.toLocalDate()) && !date.isAfter(endDate.toLocalDate());
    }
}

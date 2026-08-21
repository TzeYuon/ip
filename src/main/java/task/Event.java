package task;
/**
 * Represents a task with start and end times expressed as user-entered text.
 */
public class Event extends Task {

    private final String startDate;
    private final String endDate;

    /**
     * Creates an incomplete event.
     *
     * @param description description of the event
     * @param startDate start time text
     * @param endDate end time text
     */
    public Event(String description, String startDate, String endDate) {
        super(description);
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
        return "[E]" + super.toString() + " (from: " + startDate + " to: " + endDate + ")";
    }
}

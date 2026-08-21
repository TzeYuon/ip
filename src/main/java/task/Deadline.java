package task;
/**
 * Represents a task with a deadline expressed as user-entered text.
 */
public class Deadline extends Task {

    private final String by;

    /**
     * Creates an incomplete deadline.
     *
     * @param description description of the deadline
     * @param by deadline text
     */
    public Deadline(String description, String by) {
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
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

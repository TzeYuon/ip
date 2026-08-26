package task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Represents a task and whether it has been completed.
 */
public abstract class Task {
    private final String description;
    private boolean isDone;
    protected static final DateTimeFormatter DATE_TIME_PRINT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd uuuu, h:mma");
    protected static final DateTimeFormatter DATE_TIME_WRITE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm");

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon used when displaying this task.
     * @return {@code "X"} when the task is done, otherwise a blank space
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns whether this task is completed.
     *
     * @return {@code true} if the task is completed
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns the task description without status or type formatting.
     *
     * @return task description
     */
    protected String getDescription() {
        return this.description;
    }
    /**
     * Marks task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks task as incomplete.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Restores this task's completion state without producing console output.
     *
     * @param isDone saved completion state
     */
    public void restoreCompletionStatus(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns this task in the format displayed to users.
     *
     * @return the task status icon followed by its description
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Serializes this task for persistent storage.
     *
     * @return task in the application's file format
     */
    public abstract String toFileFormat();

    /**
     * Returns whether this task occurs on the supplied date.
     *
     * @param date date to check
     * @return {@code false} by default for tasks without date information
     */
    public boolean occursOn(LocalDate date) {
        return false;
    }
}

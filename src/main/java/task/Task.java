package task;
/**
 * Represents a task and whether it has been completed.
 */
public abstract class Task {
    final String description;
    boolean isDone;

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
     * Marks task as completed.
     */
    public void markAsDone() {
        isDone = true;
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + toString());
    }

    /**
     * Marks task as incomplete.
     */
    public void markAsNotDone() {
        isDone = false;
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + toString());
    }

    /** Restores this task's completion state without producing console output. */
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

    public abstract String toFileFormat();
}

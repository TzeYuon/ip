package task;
/**
 * Represents a task without date or time information.
 */
public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    /**
     * Returns this todo in its user-facing format.
     *
     * @return formatted todo
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileFormat() {
        return "TODO | " + (isDone ? "1" : "0") + " | " + description;
    }
}

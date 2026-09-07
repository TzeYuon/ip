package task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.CbtException;

/** Stores the tasks for one CBT session. */
public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /** Creates an empty task list. */
    public TaskList() {
    }

    /** Creates a task list containing a copy of the supplied tasks. */
    private TaskList(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at a zero-based index after checking that it exists.
     *
     * @param index zero-based task index.
     * @return task at the specified index.
     * @throws CbtException if the index is outside the list.
     */
    public Task getTask(int index) throws CbtException {
        checkValidIndex(index);
        return tasks.get(index);
    }

    /**
     * Removes and returns the task at a zero-based index.
     *
     * @param index zero-based task index.
     * @return removed task.
     * @throws CbtException if the index is outside the list.
     */
    public Task deleteTask(int index) throws CbtException {
        checkValidIndex(index);
        return tasks.remove(index);
    }

    /**
     * Marks and returns the task at a zero-based index.
     *
     * @param index zero-based task index.
     * @return task after it has been marked as complete.
     * @throws CbtException if the index is outside the list.
     */
    public Task markTask(int index) throws CbtException {
        Task task = getTask(index);
        task.markAsDone();
        return task;
    }

    /**
     * Unmarks and returns the task at a zero-based index.
     *
     * @param index zero-based task index.
     * @return task after it has been marked as incomplete.
     * @throws CbtException if the index is outside the list.
     */
    public Task unmarkTask(int index) throws CbtException {
        Task task = getTask(index);
        task.markAsNotDone();
        return task;
    }

    /**
     * Finds deadlines and events that occur on a particular date.
     *
     * @param date date on which tasks must occur.
     * @return new task list containing all matching tasks.
     */
    public TaskList findTasksOn(LocalDate date) {
        List<Task> matchingTasks = tasks.stream()
                .filter(task -> task.occursOn(date))
                .toList();
        return new TaskList(matchingTasks);
    }

    /**
     * Finds tasks whose descriptions contain a keyword, ignoring letter case.
     *
     * @param keyword keyword that task descriptions must contain.
     * @return new task list containing all matching tasks in their original order.
     */
    public TaskList findTasksContaining(String keyword) {
        List<Task> matchingTasks = tasks.stream()
                .filter(task -> task.descriptionContains(keyword))
                .toList();
        return new TaskList(matchingTasks);
    }

    /**
     * Returns the number of stored tasks.
     *
     * @return task count.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Validates that an index refers to a task currently in the list.
     *
     * @param index zero-based task index to validate.
     * @throws CbtException if the index is outside the list.
     */
    private void checkValidIndex(int index) throws CbtException {
        if (index < 0 || index >= getSize()) {
            throw new CbtException("Please enter a task number from the list, e.g. mark 1.");
        }
    }

    /**
     * Returns each task with its one-based list number.
     *
     * @return formatted tasks, or an empty string if the list is empty.
     * @throws CbtException if a task cannot be retrieved for display.
     */
    public String formatTasks() throws CbtException {
        StringBuilder formattedTasks = new StringBuilder();
        for (int i = 0; i < getSize(); i++) {
            appendLine(formattedTasks, i + 1 + "." + getTask(i));
        }
        return formattedTasks.toString();
    }

    /**
     * Returns dated tasks occurring on a date with their original list numbers.
     *
     * @param date date on which returned tasks must occur.
     * @return formatted matching tasks, or an empty string if there are no matches.
     * @throws CbtException if a task cannot be retrieved for display.
     */
    public String formatTasksOnDate(LocalDate date) throws CbtException {
        StringBuilder formattedTasks = new StringBuilder();
        for (int i = 0; i < getSize(); i++) {
            Task task = getTask(i);
            if (task.occursOn(date)) {
                appendLine(formattedTasks, i + 1 + "." + task);
            }
        }
        return formattedTasks.toString();
    }

    /** Appends a line after any existing content without leaving a trailing line separator. */
    private void appendLine(StringBuilder text, String line) {
        if (!text.isEmpty()) {
            text.append(System.lineSeparator());
        }
        text.append(line);
    }
}

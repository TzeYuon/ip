package task;

import java.time.LocalDate;
import java.util.ArrayList;

import exception.CbtException;

/** Stores the tasks for one CBT session. */
public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

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
        TaskList returnList = new TaskList();
        for (Task task : tasks) {
            if (task.occursOn(date)) {
                returnList.addTask(task);
            }
        }
        return returnList;
    }

    /**
     * Finds tasks whose descriptions contain a keyword, ignoring letter case.
     *
     * @param keyword keyword that task descriptions must contain.
     * @return new task list containing all matching tasks in their original order.
     */
    public TaskList findTasksContaining(String keyword) {
        TaskList matchingTasks = new TaskList();
        for (Task task : tasks) {
            if (task.descriptionContains(keyword)) {
                matchingTasks.addTask(task);
            }
        }
        return matchingTasks;
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
        if (index < 0 || index >= tasks.size()) {
            throw new CbtException("Please enter a task number from the list, e.g. mark 1.");
        }
    }

    /**
     * Prints each task with its one-based list number.
     *
     * @throws CbtException if a task cannot be retrieved for display.
     */
    public void printTasks() throws CbtException {
        for (int i = 0; i < getSize(); i++) {
            System.out.println(i + 1 + "." + getTask(i));
        }
    }

    /**
     * Prints dated tasks occurring on a date with their original list numbers.
     *
     * @param date date on which printed tasks must occur.
     * @throws CbtException if a task cannot be retrieved for display.
     */
    public void printTasksOn(LocalDate date) throws CbtException {
        for (int i = 0; i < getSize(); i++) {
            Task task = getTask(i);
            if (task.occursOn(date)) {
                System.out.println(i + 1 + "." + task);
            }
        }
    }
}

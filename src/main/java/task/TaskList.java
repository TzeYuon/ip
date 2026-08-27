package task;

import java.time.LocalDate;
import java.util.ArrayList;

import exception.CbtException;

/** Stores the tasks for one CBT session. */
public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /** Adds a task to the end of the list. */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /** Returns the task at a zero-based index after checking that it exists. */
    public Task getTask(int index) throws CbtException {
        checkValidIndex(index);
        return tasks.get(index);
    }

    /** Removes and returns the task at a zero-based index. */
    public Task deleteTask(int index) throws CbtException {
        checkValidIndex(index);
        return tasks.remove(index);
    }

    public Task markTask(int index) throws CbtException {
        Task task = getTask(index);
        task.markAsDone();
        return task;
    }

    public Task unmarkTask(int index) throws CbtException {
        Task task = getTask(index);
        task.markAsNotDone();
        return task;
    }

    public TaskList findTasksOn(LocalDate date) {
        TaskList returnList = new TaskList();
        for (Task task : tasks) {
            if (task.occursOn(date)) {
                returnList.addTask(task);
            }
        }
        return returnList;
    }

<<<<<<< Updated upstream
    /** Returns the number of stored tasks. */
=======
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
>>>>>>> Stashed changes
    public int getSize() {
        return tasks.size();
    }

    private void checkValidIndex(int index) throws CbtException {
        if (index < 0 || index >= tasks.size()) {
            throw new CbtException("Please enter a task number from the list, e.g. mark 1.");
        }
    }

    public void printTasks() throws CbtException{
        for (int i = 0; i < getSize(); i++) {
            System.out.println(i + 1 + "." + getTask(i));
        }
    }
}

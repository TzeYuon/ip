package task;

import java.util.ArrayList;

import exception.CbtException;

/** Stores the tasks for one CBT session. */
public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /** Adds a task to the end of the list. */
    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list.");
    }

    /** Returns the task at a zero-based index after checking that it exists. */
    public Task getTask(int index) throws CbtException {
        checkValidIndex(index);
        return tasks.get(index);
    }

    /** Removes and returns the task at a zero-based index. */
    public Task deleteTask(int index) throws CbtException {
        checkValidIndex(index);
        Task removedTask = tasks.remove(index);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list.");
        return removedTask;
    }

    /** Returns the number of stored tasks. */
    public int getSize() {
        return tasks.size();
    }

    private void checkValidIndex(int index) throws CbtException {
        if (index < 0 || index >= tasks.size()) {
            throw new CbtException("Please enter a task number from the list, e.g. mark 1.");
        }
    }
}

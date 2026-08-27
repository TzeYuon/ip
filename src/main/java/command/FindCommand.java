package command;

import exception.CbtException;
import task.TaskList;

/** Displays tasks whose descriptions contain a keyword. */
public class FindCommand implements Command {
    private final String keyword;

    /**
     * Creates a command using the user-entered search keyword.
     *
     * @param keyword keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Prints tasks whose descriptions contain the search keyword.
     *
     * @param taskList task list to search.
     * @return result indicating that no application state changed.
     * @throws CbtException if the keyword is blank.
     */
    @Override
    public CommandResult execute(TaskList taskList) throws CbtException {
        if (keyword.isBlank()) {
            throw new CbtException("Use: find KEYWORD (for example, find book)");
        }

        System.out.println("Here are the matching tasks in your list:");
        taskList.findTasksContaining(keyword).printTasks();
        return new CommandResult("", false, false);
    }
}

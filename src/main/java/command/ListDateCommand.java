package command;

import exception.CbtException;
import parser.Parser;
import task.TaskList;

import java.time.LocalDate;

/** Displays deadlines and events occurring on one specified date. */
public class ListDateCommand implements Command {
    private final String dateText;

    /** Creates a command using the user-entered date. */
    public ListDateCommand(String dateText) {
        this.dateText = dateText;
    }

    @Override
    public void execute(TaskList taskList) throws CbtException {
        if (dateText.isBlank()) {
            throw new CbtException("Use: date DATE (for example, 2019-12-02)");
        }
        LocalDate date = Parser.parseDate(dateText);
        System.out.println("Here are the deadlines and events on " + Parser.formatDate(date) + ":");
        for (int index = 0; index < taskList.getSize(); index++) {
            if (taskList.getTask(index).occursOn(date)) {
                System.out.println(index + 1 + "." + taskList.getTask(index));
            }
        }
    }
}

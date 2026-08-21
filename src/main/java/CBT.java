import command.Command;
import exception.CbtException;
import parser.Parser;
import task.TaskList;
import ui.UI;

/** Starts CBT and coordinates the user interface, parser, and task list. */
public class CBT {
    /** Runs the command loop until the user enters {@code bye}. */
    public static void main(String[] args) {
        UI ui = new UI();
        Parser parser = new Parser();
        TaskList taskList = new TaskList();
        ui.showWelcome();
        while (ui.hasNextCommand()) {
            String input = ui.readCommand();
            ui.showLine();
            if (input.equals("bye")) {
                break;
            }
            try {
                Command command = parser.parse(input);
                command.execute(taskList);
            } catch (CbtException exception) {
                ui.showError(exception.getMessage());
            }
            ui.showLine();
        }
        ui.showGoodbye();
    }
}

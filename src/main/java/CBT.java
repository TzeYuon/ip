import command.Command;
import exception.CbtException;
import parser.Parser;
import task.TaskList;
import storage.Storage;
import ui.UI;

/** Starts CBT and coordinates the user interface, parser, and task list. */
public class CBT {
    private static final Storage STORAGE = new Storage("./data/CBT.txt");
    /** Runs the command loop until the user enters {@code bye}. */
    public static void main(String[] args) {
        UI ui = new UI();
        TaskList tasks;
        Parser parser = new Parser();
        tasks = STORAGE.loadTasks();
        ui.showWelcome();
        while (ui.hasNextCommand()) {
            String input = ui.readCommand();
            ui.showLine();
            if (input.equals("bye")) {
                break;
            }
            try {
                Command command = parser.parseCommand(input);
                command.execute(tasks);
                if (command.changesTaskList()) {
                    STORAGE.saveTasks(tasks);
                }
            } catch (CbtException exception) {
                ui.showError(exception.getMessage());
            }
            ui.showLine();
        }
        ui.showGoodbye();
    }
}

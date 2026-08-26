package cbt;
import command.Command;
import command.CommandResult;
import exception.CbtException;
import parser.Parser;
import task.TaskList;
import storage.Storage;
import ui.UI;

/** Starts CBT and coordinates the user interface, parser, and task list. */
public class CBT {
    private static final Storage STORAGE = new Storage("./data/CBT.txt");
    private final UI ui;
    private final Storage storage;
    private final TaskList tasks;

    public CBT(UI ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
        this.tasks = storage.loadTasks();
    }

    /** Runs the application until an exit command is received. */
    public void run() {
        ui.showWelcome();

        while (ui.hasNextCommand()) {
            String input = ui.readCommand();
            ui.showLine();

            try {
                Command command = Parser.parseCommand(input);
                CommandResult result = command.execute(tasks);

                ui.showResult(result);

                if (result.taskListChanged()) {
                    storage.saveTasks(tasks);
                }

                if (result.exit()) {
                    break;
                }
            } catch (CbtException exception) {
                ui.showError(exception.getMessage());
            }
            ui.showLine();
        }
    }

    public static void main(String[] args) {
        UI ui = new UI();
        Storage storage = new Storage("./data/CBT.txt");
        CBT cbt = new CBT(ui, storage);
        cbt.run();
    }
}

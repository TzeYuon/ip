package storage;

import exception.CbtException;
import parser.Parser;
import task.Task;
import task.TaskList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/** Loads tasks from and saves tasks to the application's data file. */
public class Storage {
    private final Path filePath;
    private final Parser parser;

    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
        this.parser = new Parser();
    }

    public TaskList loadTasks() {
        TaskList tasks = new TaskList();
        if (!Files.exists(filePath)) {
            return tasks;
        }
        try {
            for (String line : Files.readAllLines(filePath)) {
                Task task = parser.parseLineToTask(line);
                if (task != null) {
                    tasks.addLoadedTask(task);
                }
            }
        } catch (IOException exception) {
            System.out.println("Unable to load saved tasks: " + exception.getMessage());
        }
        return tasks;
    }

    public void saveTasks(TaskList tasks) {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            StringBuilder savedTasks = new StringBuilder();
            for (int i = 0; i < tasks.getSize(); i++) {
                savedTasks.append(tasks.getTask(i).toFileFormat()).append(System.lineSeparator());
            }
            Files.writeString(filePath, savedTasks.toString(), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException | CbtException exception) {
            System.out.println("Unable to save tasks: " + exception.getMessage());
        }
    }
}

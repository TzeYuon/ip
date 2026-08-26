package storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import exception.CbtException;
import task.Deadline;
import task.TaskList;
import task.Todo;

/** Tests persistence of task lists without touching the real application data. */
public class StorageTest {
    @TempDir
    Path temporaryDirectory;

    /** Verifies that loading a missing file returns an empty task list. */
    @Test
    public void loadTasks_missingFile_emptyListReturned() {
        Storage storage = new Storage(temporaryDirectory.resolve("missing.txt").toString());

        assertEquals(0, storage.loadTasks().getSize());
    }

    /** Verifies that multiple task types and completion states survive a save-load cycle. */
    @Test
    public void saveThenLoadTasks_multipleTaskTypes_contentAndStatusRestored()
            throws CbtException, IOException {
        Path dataFile = temporaryDirectory.resolve("nested").resolve("tasks.txt");
        Storage storage = new Storage(dataFile.toString());
        TaskList original = new TaskList();
        Todo todo = new Todo("read book");
        todo.markAsDone();
        original.addTask(todo);
        original.addTask(new Deadline("submit work",
                LocalDateTime.of(2026, 8, 26, 18, 0)));

        storage.saveTasks(original);
        TaskList loaded = storage.loadTasks();

        assertTrue(Files.exists(dataFile));
        assertEquals(2, loaded.getSize());
        assertEquals("TODO | 1 | read book", loaded.getTask(0).toFileFormat());
        assertEquals("DEADLINE | 0 | submit work | 26/08/2026 1800",
                loaded.getTask(1).toFileFormat());
    }

    /** Verifies that saving replaces existing file contents. */
    @Test
    public void saveTasks_existingFile_previousContentReplaced() throws IOException {
        Path dataFile = temporaryDirectory.resolve("tasks.txt");
        Files.writeString(dataFile, "old content");
        Storage storage = new Storage(dataFile.toString());
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("new task"));

        storage.saveTasks(tasks);
        String content = Files.readString(dataFile);

        assertTrue(content.contains("TODO | 0 | new task"));
        assertFalse(content.contains("old content"));
    }

    /** Verifies that malformed stored lines are skipped while valid lines are loaded. */
    @Test
    public void loadTasks_malformedLine_validLinesStillLoaded() throws IOException, CbtException {
        Path dataFile = temporaryDirectory.resolve("tasks.txt");
        Files.writeString(dataFile, "broken line" + System.lineSeparator()
                + "TODO | 0 | valid task" + System.lineSeparator());

        TaskList loaded = new Storage(dataFile.toString()).loadTasks();

        assertEquals(1, loaded.getSize());
        assertEquals("[T][ ] valid task", loaded.getTask(0).toString());
    }
}

package cbt;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import storage.Storage;
import ui.Ui;

/** Tests the application's command loop with isolated input, output, and storage. */
public class CbtTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    public void run_addTodoThenExit_responsePrintedAndTaskSaved() throws Exception {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Path dataFile = temporaryDirectory.resolve("tasks.txt");

        try {
            System.setIn(new ByteArrayInputStream(
                    "todo read book\nbye\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Cbt application = new Cbt(new Ui(), new Storage(dataFile.toString()));

            application.run();
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String consoleOutput = output.toString(StandardCharsets.UTF_8);
        assertTrue(consoleOutput.contains("I've added this task"));
        assertTrue(consoleOutput.contains("Bye. Hope to see you again soon!"));
        assertTrue(Files.readString(dataFile).contains("TODO | 0 | read book"));
    }

    @Test
    public void run_invalidCommand_errorPrintedAndNoDataFileCreated() {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Path dataFile = temporaryDirectory.resolve("tasks.txt");

        try {
            System.setIn(new ByteArrayInputStream("dance\nbye\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Cbt application = new Cbt(new Ui(), new Storage(dataFile.toString()));

            application.run();
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        assertTrue(output.toString(StandardCharsets.UTF_8).contains("I don't understand that command"));
        assertFalse(Files.exists(dataFile));
    }
}

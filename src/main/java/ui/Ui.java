package ui;

import command.CommandResult;

import java.util.Scanner;

/** Handles CBT's console input and common output formatting. */
public class UI {
    private static final String DIVIDER = "____________________________________________________________";
    private final Scanner scanner = new Scanner(System.in);

    /** Prints the application's greeting. */
    public void showWelcome() {
        String banner = "  ____ ____ _____\n"
                + " / ___| __ )_   _|\n"
                + "| |   |  _ \\ | |\n"
                + "| |___| |_) || |\n"
                + " \\____|____/ |_|\n";
        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println("Hello! I'm CBT.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);
    }

    /**
     * Returns whether another command is available from standard input.
     *
     * @return {@code true} if another input line is available
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Reads and trims the next command.
     *
     * @return next command entered by the user
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Prints a user-correctable error message.
     *
     * @param message error message to display
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /** Prints a horizontal divider. */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Prints the result message produced by a command.
     *
     * @param result command result to display
     */
    public void showResult(CommandResult result) {
        System.out.println(result.message());
    }
}

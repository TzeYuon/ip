package ui;

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

    /** Returns whether another command is available from standard input. */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /** Reads and trims the next command. */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /** Prints a user-correctable error message. */
    public void showError(String message) {
        System.out.println(message);
    }

    /** Prints a horizontal divider. */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /** Prints the application's farewell and final divider. */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }
}

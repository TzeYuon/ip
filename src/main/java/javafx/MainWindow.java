package javafx;

import java.util.Objects;

import cbt.Cbt;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/** Controls the main chatbot window. */
public class MainWindow extends AnchorPane {
    private final Image userImage = loadImage("/images/daUser.jpeg");
    private final Image cbtImage = loadImage("/images/daCbt.png");

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private Cbt cbt;

    /** Sets up behavior that depends on all FXML controls having been injected. */
    @FXML
    public void initialize() {
        dialogContainer.heightProperty().addListener(observable -> scrollPane.setVvalue(1.0));
    }

    /**
     * Supplies the chatbot that handles commands entered in this window.
     *
     * @param cbt chatbot instance to use.
     */
    public void setCbt(Cbt cbt) {
        this.cbt = Objects.requireNonNull(cbt);
    }

    /** Creates message bubbles for the user's input and CBT's response. */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        String response = cbt.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getCbtDialog(response, cbtImage));
        userInput.clear();

        exitIfRequested(input, Platform::exit);
    }

    /** Runs the supplied exit action when the input is a bye command. */
    static void exitIfRequested(String input, Runnable exitAction) {
        if (input.trim().equalsIgnoreCase("bye")) {
            exitAction.run();
        }
    }

    /** Loads an image resource, failing early if it is missing. */
    private Image loadImage(String path) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    }
}

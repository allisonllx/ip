package allison;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private static final int EXIT_DELAY_MS = 5000;

    private Allison allison;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image allisonImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Initializes the controller, binding the scroll pane to auto-scroll to new content.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Allison application instance and displays the greeting.
     *
     * @param allison The Allison application instance.
     */
    public void setAllison(Allison allison) {
        this.allison = allison;
        this.greetUser();
    }

    /**
     * Displays the initial greeting message from Allison in the dialog container.
     */
    private void greetUser() {
        String greeting = allison.greetUser();
        dialogContainer.getChildren().add(
                DialogBox.getAllisonDialog(greeting, allisonImage));
    }

    /**
     * Closes the application window after the specified delay in milliseconds.
     *
     * @param delayMs The delay in milliseconds before the window closes.
     */
    private void closeWindowAfterDelay(int delayMs) {
        new Thread(() -> {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.exit();
        }).start();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Allison's reply,
     * then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isEmpty()) {
            return;
        }
        String response = allison.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAllisonDialog(response, allisonImage)
        );
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            this.closeWindowAfterDelay(EXIT_DELAY_MS);
        }
    }
}

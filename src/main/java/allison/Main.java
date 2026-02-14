package allison;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Allison using FXML.
 */
public class Main extends Application {

    private Allison allison = new Allison();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Allison");
            fxmlLoader.<MainWindow>getController().setAllison(allison);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
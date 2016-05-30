package moviefinder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main class for the movie finder application.
 */
public class MovieFinder extends Application {

    /**
     * Loads and launches the main window.
     */
    @Override
    public final void start(final Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "moviefinder/MainView.fxml"));
        primaryStage.setTitle("Movie DB");
        primaryStage.getIcons().add(new Image("bird.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Main method for launching the movie finder. Initializes the JavaFX
     * launching process.
     *
     * @param args
     *            Command line arguments.
     */
    public static void main(final String[] args) {
        launch(args);
    }
}

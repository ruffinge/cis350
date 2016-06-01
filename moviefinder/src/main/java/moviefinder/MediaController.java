package moviefinder;


import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * A controller class for the media display.
 */
public class MediaController {

    /**
     * The {@link MainViewController} that this will belong to.
     */
    @FXML
    private MainViewController main;

    /**
     * A VBox to contain the movie information.
     */
    @FXML
    private VBox movieBox;

    /**
     * The image to be displayed.
     */
    @FXML
    private ImageView image;

    // TODO: Remove this hardcoded system. Use JavaFX functions instead.
    public void inClick() {
        int col, row, index;
        col = GridPane.getColumnIndex(movieBox);
        row = GridPane.getRowIndex(movieBox);
        index = row * 3 + col;
        main.clickImageInDiscovery(index);
    }

    /**
     * Produces a transition of scaling by growing and shrinking.
     */
    public final void growingShrinkingEffect() {
        movieBox.toFront();

        ScaleTransition scale = new ScaleTransition(Duration.millis(120),
                movieBox);
        scale.setByX(.2);
        scale.setByY(.2);
        scale.setCycleCount(2);
        scale.setAutoReverse(true);
        scale.autoReverseProperty();
        scale.play();
    }

    /**
     * Initialize the mediaController.
     *
     * @param mainController
     *            The main controller that this will belong to.
     */
    public final void initialize(final MainViewController mainController) {
        main = mainController;
    }
}

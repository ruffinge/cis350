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
    /** The scale factor to use for the transition effect. */
    private static final double SCALEFACTOR = 0.2;
    /** The transition duration, in milliseconds. */
    private static final double TRANSDURATION = 120;

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

    /**
     * Produce a transition effect.
     */
    public final void inClick() {
        // TODO: Remove this hardcoded system. Use JavaFX functions instead.
        int col, row;
        col = GridPane.getColumnIndex(movieBox);
        row = GridPane.getRowIndex(movieBox);
        ImageView imageView = (ImageView) movieBox.getChildren().get(0);
        main.clickImageInDiscovery(imageView.getImage());
    }

    /**
     * Produces a transition of scaling by growing and shrinking.
     */
    public final void growingShrinkingEffect() {
        movieBox.toFront();

        ScaleTransition scale =
                new ScaleTransition(Duration.millis(TRANSDURATION), movieBox);
        scale.setByX(SCALEFACTOR);
        scale.setByY(SCALEFACTOR);
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

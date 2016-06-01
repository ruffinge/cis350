package moviefinder;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * A container for a display of information about an item in TMDB.
 */
public class MediaObject {

    /**
     * An image to be displayed.
     */
    private ImageView image;

    /**
     * The title of the media.
     */
    @FXML
    private Text title;

    /**
     * A VBox for the media information.
     */
    private VBox mediaVBox;

    /**
     * Construct a new MediaObject.
     *
     * @param pImage
     *            The image for the media item.
     * @param pTitle
     *            The title for the media item.
     * @param mainController
     *            The controller for the main window.
     */
    public MediaObject(final Image pImage, final String pTitle,
            final MainViewController mainController) {
        try {
            FXMLLoader mediaLoader = new FXMLLoader(getClass().getClassLoader()
                    .getResource("view/MediaObject.fxml"));
            mediaVBox = mediaLoader.load();
            image = (ImageView) mediaVBox.getChildren().get(0);
            title = (Text) mediaVBox.getChildren().get(1);
            MediaController controller = mediaLoader.getController();
            controller.initialize(mainController);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pImage != null) {
            image.setImage(pImage);
        }
        title.setText(pTitle);
    }

    /**
     * Accessor for the mediaVBox.
     *
     * @return The mediaVBox.
     */
    public final VBox getVBox() {
        return mediaVBox;
    }
}

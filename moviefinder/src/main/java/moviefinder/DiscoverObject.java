package moviefinder;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DiscoverObject {

    private ImageView image;

    @FXML
    private Text title;

    private VBox discoverVBox;

    DiscoverObject(Image pImage, String pTitle) {
        try {
            discoverVBox = FXMLLoader.load(getClass().getClassLoader().getResource(
                    "moviefinder/discoverObject.fxml"));
            image = (ImageView) discoverVBox.getChildren().get(0);
            title = (Text) discoverVBox.getChildren().get(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (pImage != null) {
            image.setImage(pImage);
        }
        title.setText(pTitle);
    }

    public VBox getVBox() {
        VBox box = discoverVBox;
        return box;
    }

}

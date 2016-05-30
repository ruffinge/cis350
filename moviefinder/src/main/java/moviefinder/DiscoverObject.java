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

    private VBox Vdiscover;

    DiscoverObject(Image pImage, String pTitle) {
        try {
            Vdiscover = FXMLLoader.load(getClass().getClassLoader().getResource(
                    "moviefinder/discoverObject.fxml"));
            image = (ImageView) Vdiscover.getChildren().get(0);
            title = (Text) Vdiscover.getChildren().get(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (pImage != null) {
            image.setImage(pImage);
        }
        title.setText(pTitle);
    }

    public VBox getVBox() {
        VBox box = Vdiscover;
        return box;
    }

}

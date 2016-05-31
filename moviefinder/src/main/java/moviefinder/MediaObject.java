package moviefinder;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MediaObject {

    private ImageView image;

    @FXML
    private Text title;

    private VBox mediaVBox;

    MediaObject(Image pImage, String pTitle, MainViewController mainController) {
        try {
        	FXMLLoader mediaLoader = new FXMLLoader(getClass().getClassLoader()
        			.getResource("view/MediaObject.fxml"));
        	mediaVBox = mediaLoader.load();
            image = (ImageView) mediaVBox.getChildren().get(0);
            title = (Text) mediaVBox.getChildren().get(1);
            MediaController controller = mediaLoader.getController();
            controller.init(mainController);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pImage != null) {
            image.setImage(pImage);
        }
        title.setText(pTitle);
    }

    public VBox getVBox() {
        return mediaVBox;
    }

}

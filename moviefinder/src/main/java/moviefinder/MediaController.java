package moviefinder;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MediaController {

    @FXML
    MainViewController main;
    @FXML
    private VBox movieBox;
    @FXML
    private ImageView image;

    public void inClick() {
        int col, row, index;
        col = GridPane.getColumnIndex(movieBox);
        row = GridPane.getRowIndex(movieBox);
        index = row * 3 + col;
       main.clickImageInDiscovery(index);
       
    }

    public void growingShrinkingEffect() {

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

    public void initialize(MainViewController mainController) {
        main = mainController;
    }
}


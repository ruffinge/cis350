package moviefinder;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MediaController {

	@FXML MainViewController main;
	@FXML
	private VBox movieBox;
	@FXML
	private ImageView image;
	
	public void inClick() {
		int col, row;
		col = GridPane.getColumnIndex(movieBox);
		row = GridPane.getRowIndex(movieBox);
		
		main.clickImageInDiscovery(col, row);
	}

	public void growingShrinkingEffect() {

		movieBox.toFront();

		ScaleTransition scale = new ScaleTransition(Duration.millis(150), movieBox);
		scale.setByX(.2);
		scale.setByY(.2);
		scale.setCycleCount(2);
		scale.setAutoReverse(true);
		scale.autoReverseProperty();
		scale.play();
	}
	
	public void init(MainViewController pmain){
		main = pmain;
	}
}

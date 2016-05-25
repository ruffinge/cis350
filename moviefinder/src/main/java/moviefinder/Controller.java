package moviefinder;

import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.tv.TvSeries;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Controller {


	MovieDBcl cl = new MovieDBcl();
	@FXML
	private TextField searchField;
	@FXML
	private Labeled messageLabel;
	@FXML
	private TextArea myTextArea;
	@FXML 
	private ListView listView;
	@FXML
	public MenuButton menuButton;
	@FXML 
	private MenuItem myMenuItem;
	@FXML
	private ImageView imageView;
	@FXML
	private Text theTitle;
	@FXML
	private Text theDescription;
	

	@FXML
	private AnchorPane growingPane;
	@FXML
	private AnchorPane growingTabs;

	private Rectangle clipRect;
	private Rectangle clipRect2;

	private String userInput1;
	private String moviesList;
	private String showsList;
	
	public boolean isMovies;

	public void btnMenuChange() {

		if (menuButton.getText().equals("Movies")) {
			menuButton.setText("Shows");
			myMenuItem.setText("Movies");
			isMovies = false;
		}
		else {
			menuButton.setText("Movies");
			myMenuItem.setText("Shows");
			isMovies = true;
		}
	}
	public void searching() {
		userInput1 = searchField.getText();
		if(userInput1.equals("")){
			clipRect.setWidth(growingPane.getWidth());
			if (clipRect.heightProperty().get() != 0) 
				up();
		}
		else {
			if(menuButton.getText().equals("Movies")){
				moviesList = cl.SearchingMovies(userInput1).toString();
				ObservableList<MovieDb> movies = FXCollections.observableArrayList
						(cl.SearchingMovies(userInput1));
				listView.setItems(movies);
			}
			else {
				showsList = cl.SearchingShows(userInput1).toString();
				ObservableList<TvSeries> shows = FXCollections.observableArrayList
						(cl.SearchingShows(userInput1));	
				listView.setItems(shows);
			}
		}
		
	}
		
	@FXML
	public void search() {
		String query = searchField.getText();
		if (query.equals("")) {
			listView.getItems().clear();
			clipRect.setWidth(growingPane.getWidth());
			up();
		} else {
			ObservableList<Multi> results = FXCollections.observableArrayList(cl.Search(query));
			if (results.size() > 0) {
				listView.setItems(results);
			}
		}
	}

	public void getSelection() {
		
		Multi selectedItem = (Multi) listView.getSelectionModel().getSelectedItem();
		Image image = null;
		String title = null;
		String description = null;
		if(selectedItem != null){
			image = cl.getImage(selectedItem);
			title = cl.getTitle(selectedItem);
			theTitle.setText(title);
			description = cl.getDescription(selectedItem);
			theDescription.setText(description);
		}
		if(image != null){
			imageView.setImage(image);
			clipRect.setWidth(growingPane.getWidth());
			if (clipRect.heightProperty().get() == 0) 
				down();
		}
		//@TODO set wanted info here: title, description,...  
	}

	public void convertFormId(int id) {
		// take id and convert to movie/show that can call getTitle for list,
		// getImage, getTrailer, etc.. for right side
	}

	public String getCast() {
		// may have to be asking for a list?
		// send selectedMovie/Show
		return null;
	}

	public void addFavorites() {

	}

	public void rateIt() {

	}

	/* Testing new feature */
    
	public void up(){
		Timeline timelineUp = new Timeline();
		final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
		final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), growingPane.getHeight());
		final KeyValue kvUp4 = new KeyValue(growingPane.prefHeightProperty(), 0);
		final KeyValue kvUp3 = new KeyValue(growingPane.translateYProperty(),-growingPane.getHeight());
		final KeyValue kvUp7 = new KeyValue(growingTabs.prefHeightProperty(), 0);
		final KeyValue kvUp8 = new KeyValue(growingTabs.translateYProperty(),-growingTabs.getHeight());
		final KeyFrame kfUp = new KeyFrame(Duration.millis(1000), kvUp1, kvUp2, kvUp3, kvUp4,kvUp7,kvUp8);
		timelineUp.getKeyFrames().add(kfUp);
		timelineUp.play();
	}
	
	public void down(){
		
					Timeline timelineDown = new Timeline();
					final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), growingPane.getHeight());
					final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);
					final KeyValue kvDwn4 = new KeyValue(growingPane.prefHeightProperty(),growingPane.getHeight());
					final KeyValue kvDwn3 = new KeyValue(growingPane.translateYProperty(), 0);
					final KeyValue kvDwn7 = new KeyValue(growingTabs.prefHeightProperty(), 0);
					final KeyValue kvDwn8 = new KeyValue(growingTabs.translateYProperty(),0);
					final KeyFrame kfDwn = new KeyFrame(Duration.millis(1000),
							createBouncingEffect(growingPane.getHeight()), kvDwn1, kvDwn2, kvDwn3, kvDwn4,kvDwn7,kvDwn8);
					timelineDown.getKeyFrames().add(kfDwn);
					timelineDown.play();
	}
	
	private EventHandler<ActionEvent> createBouncingEffect(double height) {

		final Timeline timelineBounce = new Timeline();
		timelineBounce.setCycleCount(4);
		timelineBounce.setAutoReverse(true);
		final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), (height - 15));
		final KeyValue kv2 = new KeyValue(clipRect.translateYProperty(), 15);
		final KeyValue kv3 = new KeyValue(growingPane.translateYProperty(), -15);
		final KeyFrame kf1 = new KeyFrame(Duration.millis(100), kv1, kv2, kv3);
		timelineBounce.getKeyFrames().add(kf1);

		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				timelineBounce.play();
			}
		};
		return handler;
	}

	@FXML
	void initialize() {
		double widthInitial = 200;
		double heightInitial = 200;
		clipRect = new Rectangle();
		clipRect.setWidth(widthInitial);
		clipRect.setHeight(0);
		clipRect.translateYProperty().set(heightInitial);
		growingPane.setClip(clipRect);
		growingPane.translateYProperty().set(-heightInitial);
		growingPane.prefHeightProperty().set(0);
		
		growingTabs.translateYProperty().set(-heightInitial);
		System.out.println("Initialization");
	}

}
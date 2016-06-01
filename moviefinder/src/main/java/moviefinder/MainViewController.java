package moviefinder;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Multi;
import info.movito.themoviedbapi.model.tv.TvSeries;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * A controller for the Movie Finder main window.
 */
public final class MainViewController {

    /** The client for the database. */
    MovieDBClient cl = new MovieDBClient();

    /** The search text field. */
    @FXML
    private TextField searchField;

    /** The message label. */
    @FXML
    private Labeled messageLabel;

    /** The list of results. */
    @FXML
    private ListView resultsListView ;
    /** Container for an image. */
    @FXML
    private ImageView imageView;
    /** Title for a result. */
    @FXML
    private Text theTitle;
    /** Description for a result. */
    @FXML
    private Text theDescription;

    @FXML
    private AnchorPane growingPane;
    @FXML
    private AnchorPane growingTabs;
    @FXML
    private AnchorPane sideBar;
    @FXML
    private AnchorPane viewListPane;
    @FXML
    private BorderPane rightPane;
    @FXML
    private AnchorPane popUpPanel;
    @FXML
    private ImageView popUpImage;
    @FXML
    private Text popUpTitle;
    @FXML
    private Text popUpDescription;
    @FXML
    private CheckBox moviesCheckBox;
    @FXML
    private CheckBox seriesCheckBox;
    @FXML
    private CheckBox peopleCheckBox;
    @FXML
    private AnchorPane discoverPane;
    @FXML
    private Tab discoverTab;
    @FXML
    private GridPane discoverGrid;
    @FXML
    private TabPane tabPanel;
    @FXML
    private StackPane test;

    private AnchorPane discoverer;

    private Rectangle clipRect;
    private Rectangle clipRect2;

    private String userInput1;
    private String moviesList;
    private String showsList;

    private double widthInitial = 206;
    private double heightInitial = 300;
    private Boolean leftRigth;

    private List<MovieDb> discoverList;
    private List<Image> discoverImageList = new ArrayList<Image>();

    // discoverPane
    public boolean isMovies;
    
    /**
     * Hide the popup panel.
     */
    @FXML
    public void hidePopUpPanel(){
    	popUpPanel.toBack();
    	popUpPanel.setVisible(false);
    	tabPanel.toFront();
    	tabPanel.setVisible(true);
    }

    public void searching() {

        if (userInput1.equals("")) {
            clipRect.setWidth(growingPane.getWidth());
            if (clipRect.heightProperty().get() != 0) {
                up();
            }
        } else {

            if (moviesCheckBox.isSelected()) {
                ObservableList<MovieDb> movies = FXCollections
                        .observableArrayList(cl.searchMovies(userInput1));
                resultsListView.setItems(movies);
            }
            if (seriesCheckBox.isSelected()) {
                ObservableList<TvSeries> shows = FXCollections
                        .observableArrayList(cl.searchShows(userInput1));
                resultsListView.setItems(shows);
            }
        }
    }

    /**
     * Execute the search on using the current query.
     */
    @FXML
    public void search() {
        String query = searchField.getText();

        if (query.equals("")) {
        		resultsListView.getItems().clear();
            clipRect.setWidth(growingPane.getWidth());
            viewListPane.toBack();
            viewListPane.setVisible(false);
           
            up();
        } else {

        	viewListPane.toFront();
        	viewListPane.setVisible(true);        
            ObservableList<Multi> results;
            if (seriesCheckBox.isSelected()) {
                results = FXCollections.observableArrayList(cl.searchShows(
                        query));
                if (results.size() > 0) {
                    resultsListView.setItems(results);
                }
            } else if (moviesCheckBox.isSelected()) {
                results = FXCollections.observableArrayList(cl.searchMovies(
                        query));
                if (results.size() > 0) {
                    resultsListView.setItems(results);
                }
            } else {
                results = FXCollections.observableArrayList(cl.search(query));
                resultsListView.setItems(results);
            }
        }
    }

    /**
     * Display information for the selected search result.
     */
    public void getSelection() {
        Multi selectedItem = (Multi) resultsListView.getSelectionModel()
                .getSelectedItem();
        Image image = null;
        String title = null;
        String description = null;
        if (selectedItem != null) {
            image = cl.getImage(selectedItem);
            title = cl.getTitle(selectedItem);
            theTitle.setText(title);
            description = cl.getDescription(selectedItem);
            theDescription.setText(description);
        }
        if (image != null) {
            imageView.setImage(image);
            clipRect.setWidth(growingPane.getWidth());
            if (clipRect.heightProperty().get() == 0) {
                down();
            }
        }
        // TODO: set wanted info here: title, description,...
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

    public void up() {
        if (clipRect.heightProperty().get() == 0) {
            return;
        }
        Timeline timelineUp = new Timeline();
        final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
        final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(),
                growingPane.getHeight());

        final KeyValue kvUp3 = new KeyValue(growingPane.translateYProperty(),
                -growingPane.getHeight());
        final KeyValue kvUp4 = new KeyValue(growingPane.prefHeightProperty(),
                0);

        final KeyValue kvUp5 = new KeyValue(growingTabs.prefHeightProperty(),
                0);
        final KeyValue kvUp6 = new KeyValue(growingTabs.translateYProperty(),
                0);

        final KeyFrame kfUp = new KeyFrame(Duration.millis(1000), kvUp1, kvUp2,
                kvUp3, kvUp4, kvUp5, kvUp6);
        timelineUp.getKeyFrames().add(kfUp);
        timelineUp.play();
    }

    public void down() {
        if (clipRect.heightProperty().get() != 0) {
            return;
        }
        Timeline timelineDown = new Timeline();
        final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(),
                growingPane.getHeight());
        final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);

        final KeyValue kvDwn3 = new KeyValue(growingPane.translateYProperty(),
                0);
        final KeyValue kvDwn4 = new KeyValue(growingPane.prefHeightProperty(),
                growingPane.getHeight());

        final KeyValue kvDwn7 = new KeyValue(growingTabs.prefHeightProperty(),
                0);
        final KeyValue kvDwn8 = new KeyValue(growingTabs.translateYProperty(),
                heightInitial);

        final KeyFrame kfDwn = new KeyFrame(Duration.millis(1000), imageEffect(
                growingPane.getHeight()), kvDwn1, kvDwn2, kvDwn3, kvDwn4,
                kvDwn7, kvDwn8);
        timelineDown.getKeyFrames().add(kfDwn);
        timelineDown.play();
    }

    public void right() {
        Timeline timelineDown = new Timeline();
        final KeyValue kvright1 = new KeyValue(clipRect.widthProperty(),
                widthInitial);
        final KeyValue kvright2 = new KeyValue(clipRect.translateXProperty(),
                0);

        final KeyValue kvright3 = new KeyValue(rightPane.translateXProperty(),
                0);
        final KeyValue kvright4 = new KeyValue(rightPane.prefWidthProperty(),
                widthInitial);
        final KeyValue kvright7 = new KeyValue(sideBar.prefWidthProperty(), 0);
        final KeyValue kvright8 = new KeyValue(sideBar.translateXProperty(), 0);
        

        final KeyFrame kfDwn = new KeyFrame(Duration.millis(500), kvright1,
                kvright2, kvright3, kvright4, kvright7, kvright8);
        timelineDown.getKeyFrames().add(kfDwn);
        timelineDown.play();
    }

    public void left() {
        Timeline timelineDown = new Timeline();

        final KeyValue kvleft1 = new KeyValue(clipRect.widthProperty(),
                widthInitial);
        final KeyValue kvleft2 = new KeyValue(clipRect.translateXProperty(), 0);

        final KeyValue kvleft5 = new KeyValue(sideBar.prefWidthProperty(), 0);
        final KeyValue kvleft6 = new KeyValue(sideBar.translateXProperty(),
                -widthInitial);

        final KeyValue kvleft7 = new KeyValue(rightPane.prefWidthProperty(), 0);
        final KeyValue kvleft8 = new KeyValue(rightPane.translateXProperty(),
                -widthInitial);

        final KeyFrame kfDwn = new KeyFrame(Duration.millis(500), kvleft1,
                kvleft2, kvleft5, kvleft6, kvleft7, kvleft8);
        timelineDown.getKeyFrames().add(kfDwn);
        timelineDown.play();
    }

    public void xTranslate() {
        if (leftRigth) {
            right();
            leftRigth = false;
        } else {
            left();
            leftRigth = true;
        }
    }

    private EventHandler<ActionEvent> imageEffect(double height) {
        final Timeline timelineBounce = new Timeline();
        timelineBounce.setCycleCount(4);
        timelineBounce.setAutoReverse(true);
        final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), (height
                - 15));
        final KeyValue kv2 = new KeyValue(clipRect.translateYProperty(), 15);
        final KeyValue kv3 = new KeyValue(growingPane.translateYProperty(),
                -15);

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

    public void discoverLayout() {
        int n = 0;
        if (discoverList == null)
            discoverList = new ArrayList<MovieDb>();
        discoverList = cl.discoverMovies();
        ScrollPane temp = (ScrollPane) discoverTab.getContent();
        discoverGrid = (GridPane) temp.getContent();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                MovieDb containt = discoverList.get(n);
                Image tempImage = cl.getImage(containt);
                discoverImageList.add(tempImage);
                MediaObject ob = new MediaObject(tempImage, containt.getTitle(),
                        this);
                VBox box = ob.getVBox();
                discoverGrid.add(box, j, i);
                n++;
            }

        }
    }

    public void clickImageInDiscovery(int index) {
    	MovieDb movie = discoverList.get(index);
    	popUpDescription.setText(cl.getDescription(movie));
    	popUpTitle.setText(cl.getTitle(movie));
    	popUpImage.setImage(discoverImageList.get(index));
    	popUpPanel.toFront();
    	popUpPanel.setVisible(true);
    	tabPanel.toBack();
    	tabPanel.setVisible(false);			
	}
    
    /**
     * Initialize the controller. Called automatically by JavaFX internal code.
     */
    @FXML
    void initialize() {
        clipRect = new Rectangle();
        clipRect.setWidth(widthInitial);
        clipRect.setHeight(0);
        clipRect.translateYProperty().set(heightInitial);
        growingPane.setClip(clipRect);
        growingPane.translateYProperty().set(-heightInitial);
        sideBar.translateXProperty().set(-widthInitial);
        rightPane.translateXProperty().set(-widthInitial);
        leftRigth = true;
        popUpPanel.toBack();
        popUpPanel.setVisible(false);
        viewListPane.toBack();
        viewListPane.setVisible(false);
        discoverLayout();
    }
}

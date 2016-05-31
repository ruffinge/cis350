package moviefinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Multi;
import info.movito.themoviedbapi.model.tv.TvSeries;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MainViewController {

    MovieDBClient cl = new MovieDBClient();
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
    @FXML
    private AnchorPane sideBar;
    @FXML
    private AnchorPane midlePane;
    @FXML
    private BorderPane rightPane;
    @FXML
    private CheckBox moviesCheckBox;
    @FXML
    private CheckBox seriesCheckBox;
    @FXML
    private CheckBox peopleCheckBox;

    private AnchorPane discoverer;

    private Rectangle clipRect;
    private Rectangle clipRect2;

    private String userInput1;
    private String moviesList;
    private String showsList;

    private double widthInitial = 252;
    private double heightInitial = 300;
    private Boolean leftRigth;

    // discoverPane
    @FXML
    private AnchorPane discoverPane;

    @FXML
    private Tab discoverTab;

    @FXML
    private GridPane discoverGrid;

    public boolean isMovies;

    public void searching() {
        userInput1 = searchField.getText();
        if (userInput1.equals("")) {
            clipRect.setWidth(growingPane.getWidth());
            if (clipRect.heightProperty().get() != 0) {
                up();
            }
        } else {
            if (moviesCheckBox.isSelected()) {
                ObservableList<MovieDb> movies = FXCollections
                        .observableArrayList(cl.searchMovies(userInput1));
                listView.setItems(movies);
            }
            if (seriesCheckBox.isSelected()) {
                ObservableList<TvSeries> shows = FXCollections
                        .observableArrayList(cl.searchShows(userInput1));
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
            ObservableList<Multi> results;
            /*
             * if (peopleCheckBox.isSelected()){ ObservableList<Person>
             * resultsPeople =
             * FXCollections.observableArrayList(cl.SearchingPeople(query));
             * resultsPeople =
             * FXCollections.observableArrayList(cl.SearchingPeople(query));
             * if(resultsPeople.size()>0) { listView.setItems(resultsPeople); }
             */
            if (seriesCheckBox.isSelected()) {
                results = FXCollections.observableArrayList(cl.searchShows(
                        query));
                if (results.size() > 0) {
                    listView.setItems(results);
                }
            } else if (moviesCheckBox.isSelected()) {
                results = FXCollections.observableArrayList(cl.searchMovies(
                        query));
                if (results.size() > 0) {
                    listView.setItems(results);
                }
            } else {
                results = FXCollections.observableArrayList(cl.search(query));
                listView.setItems(results);
            }
        }
    }

    public void getSelection() {

        Multi selectedItem = (Multi) listView.getSelectionModel()
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
        // @TODO set wanted info here: title, description,...
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
                -growingPane.getHeight());

        final KeyFrame kfUp = new KeyFrame(Duration.millis(1000), kvUp1, kvUp2,
                kvUp3, kvUp4, kvUp5, kvUp6);
        timelineUp.getKeyFrames().add(kfUp);
        timelineUp.play();
    }

    public void down() {

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
                0);

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

        final KeyValue kvright5 = new KeyValue(midlePane.prefWidthProperty(),
                0);
        final KeyValue kvright6 = new KeyValue(midlePane.translateXProperty(),
                0);

        final KeyValue kvright7 = new KeyValue(sideBar.prefWidthProperty(), 0);
        final KeyValue kvright8 = new KeyValue(sideBar.translateXProperty(), 0);

        final KeyFrame kfDwn = new KeyFrame(Duration.millis(500), kvright1,
                kvright2, kvright3, kvright4, kvright5, kvright6, kvright7,
                kvright8);
        timelineDown.getKeyFrames().add(kfDwn);
        timelineDown.play();
    }

    public void left() {
        Timeline timelineDown = new Timeline();

        final KeyValue kvleft1 = new KeyValue(clipRect.widthProperty(),
                widthInitial);
        final KeyValue kvleft2 = new KeyValue(clipRect.translateXProperty(), 0);

        final KeyValue kvleft3 = new KeyValue(midlePane.prefWidthProperty(), 0);
        final KeyValue kvleft4 = new KeyValue(midlePane.translateXProperty(),
                -widthInitial);

        final KeyValue kvleft5 = new KeyValue(sideBar.prefWidthProperty(), 0);
        final KeyValue kvleft6 = new KeyValue(sideBar.translateXProperty(),
                -widthInitial);

        final KeyValue kvleft7 = new KeyValue(rightPane.prefWidthProperty(), 0);
        final KeyValue kvleft8 = new KeyValue(rightPane.translateXProperty(),
                -widthInitial);

        final KeyFrame kfDwn = new KeyFrame(Duration.millis(500), kvleft1,
                kvleft2, kvleft3, kvleft4, kvleft5, kvleft6, kvleft7, kvleft8);
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
        List<MovieDb> list = new ArrayList<MovieDb>();
        list = cl.discoverMovies();
        ScrollPane temp = (ScrollPane) discoverTab.getContent();
        discoverGrid = (GridPane) temp.getContent();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                MovieDb containt = list.get(n);
                Image image = cl.getImage(containt);
                MediaObject ob = new MediaObject(image, containt
                        .getTitle(),this);
                VBox box = ob.getVBox();
                discoverGrid.add(box, j, i);
                n++;
            }

        }
    }
    
    public void clickImageInDiscovery(int col, int row) {
		// TODO Auto-generated method stub
		
	}

    @FXML
    void initialize() {
        clipRect = new Rectangle();
        clipRect.setWidth(widthInitial);
        clipRect.setHeight(0);
        clipRect.translateYProperty().set(heightInitial);
        growingPane.setClip(clipRect);
        growingPane.translateYProperty().set(-heightInitial);
        growingPane.prefHeightProperty().set(0);
        growingTabs.translateYProperty().set(-(heightInitial + 20));
        sideBar.translateXProperty().set(-widthInitial);
        midlePane.translateXProperty().set(-widthInitial);
        rightPane.translateXProperty().set(-widthInitial);
        leftRigth = true;
        discoverLayout();
    }


}

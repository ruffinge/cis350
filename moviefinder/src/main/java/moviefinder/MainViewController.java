package moviefinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.controlsfx.control.Rating;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Multi;
import info.movito.themoviedbapi.model.Multi.MediaType;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;
import info.movito.themoviedbapi.model.tv.TvSeries;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

/**
 * A controller for the Movie Finder main window.
 */
public final class MainViewController {

    /** The client for the database. */
    private final MovieDBClient cl = new MovieDBClient();

    /** The search text field. */
    @FXML
    private TextField searchField;

    /** The message label. */
    @FXML
    private Labeled messageLabel;
    /** The list of favorites. */
    @FXML
    private ListView favoritesListView;
    /** The list of results. */
    @FXML
    private ListView resultsListView;
    /** Container for an image. */
    @FXML
    private ImageView imageView;
    /** Title for a result. */
    @FXML
    private Text theTitle;
    /** Description for a result. */
    @FXML
    private Text theDescription;
    /** Button for adding to favorites. */
    @FXML
    private Button addFavorites;
    /** The favorites list. */
    private ObservableList<Multi> favoritesObservable;
    /** The list of favorites. */
    private List<Multi> favoritesList = new ArrayList<Multi>();
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
    private Tab favoritesTab;
    @FXML
    private GridPane favoritesGrid;
    @FXML
    private Tab nowPlayingTab;
    @FXML
    private GridPane nowPlayingGrid;
    @FXML
    private Tab upComingTab;
    @FXML
    private GridPane upComingGrid;
    @FXML
    private TabPane tabPanel;
    @FXML
    private StackPane test;
    @FXML
    private AnchorPane webViewPane;
    @FXML
    private WebView webView;
    private WebEngine engine;
    @FXML
    private AnchorPane signInPane;
    @FXML
    private AnchorPane appPane;
    @FXML
    private Button mainSignInBtn;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passWordField;
    @FXML
    private Button loginBtn;
    @FXML
    private Text loginMessage;
    @FXML
    private Button popUpFavBtn;

    /** A rectangle used for clipping. */
    private Rectangle clipRect;
    /** Input from the user in the general-purpose box. */
    private String userInput1;
    /** The initial width to use. */
    private final double widthInitial = 206;
    /** The initial height to use. */
    private final double heightInitial = 300;
    /** Whether to use the left or right. */
    private Boolean leftRigth;

    /** List of discovered items. */
    private List<MovieDb> discoverList;
    /** List of images of discovered list. */
    private final List<Image> discoverImageList = new ArrayList<Image>();
    /** The currently selected item. */
    private Multi selectedMedia;
    /** The list of favorite items. */
    private List<Multi> favoriteList = new ArrayList<Multi>();
    /** An image cache. */
    private final HashMap<Image, Multi> cache = new HashMap<Image, Multi>();

    /** Whether the current search is for movies. */
    private boolean isMovies;
    @FXML
    private HBox dbRating;
    @FXML
    private HBox userRating;
    /** The ratings from the DB. */
    private Rating ratingDb;
    /** The user's personal rating. */
    private Rating ratingUser;
    /** The casting list. */
    @FXML
    private Text castingActors;

    @FXML
    private Text writer;
    @FXML
    private Text director;
    @FXML
    private Text soundPerson;
    @FXML
    private Text crewDr;
    @FXML
    private Text crewWrt;
    @FXML
    private Text crewSd;
    @FXML
    private Text castingT;

    /**
     * Hide the popup panel.
     */
    @FXML
    public void hidePopUpPanel() {
        popUpPanel.toBack();
        popUpPanel.setVisible(false);
        appPane.toFront();
        appPane.setVisible(true);
    }

    /**
     * An intermediate step used for producing the immediate search results.
     */
    public void searching() {
        if (userInput1.equals("")) {
            clipRect.setWidth(growingPane.getWidth());
            if (clipRect.heightProperty().get() != 0) {
                up();
            }
        } else {

            if (moviesCheckBox.isSelected()) {
                final ObservableList<MovieDb> movies = FXCollections
                        .observableArrayList(cl.searchMovies(userInput1));
                resultsListView.setItems(movies);
            }
            if (seriesCheckBox.isSelected()) {
                final ObservableList<TvSeries> shows = FXCollections
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
        final String query = searchField.getText();

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
        if (selectedMedia != null) {
            selectedMedia = null;
        }

        selectedMedia = (Multi) resultsListView.getSelectionModel()
                .getSelectedItem();

        Image image = null;
        String title = null;
        String description = null;
        if (selectedMedia != null) {
            image = cl.getImage(selectedMedia);
            title = cl.getTitle(selectedMedia);
            theTitle.setText(title);
            description = cl.getDescription(selectedMedia);
            theDescription.setText(description);
        }
        if (image != null) {
            cache.put(image, selectedMedia);
            imageView.setImage(image);
            populatePopUpPane(image);
            clipRect.setWidth(growingPane.getWidth());
            if (clipRect.heightProperty().get() == 0) {
                down();
            }
        }
        // TODO: set wanted info here: title, description,...
    }

    /**
     * Take an ID and convert it to movie/show that can call getTitle for list,
     * getImage, getTrailer, etc.. for right side
     *
     * @param id
     *            The id for the item.
     */
    public void convertFormId(final int id) {
        // TODO: Not yet implemented.
    }

    /**
     * Get the cast of the current item.
     *
     * @return The cast.
     */
    public String getCast() {
        // may have to be asking for a list?
        // send selectedMovie/Show

        // TODO: Not yet implemented.
        return null;
    }

    /**
     * Add the current item to favorites.
     */
    @FXML
    public void addToFavorites() {
        final Multi selected = selectedMedia;
        /*
         * favoritesList.add(selected); favoritesObservable = FXCollections
         * .observableArrayList(favoritesList);
         * favoritesListView.setItems(favoritesObservable);
         */
        if (!favoriteList.contains(selected)) {
            final boolean code = cl.addFavorite(selected);
            if (code) {
                favoriteList.clear();
                favoriteList = cl.getFavorites();
                if (favoriteList.contains(selectedMedia)) {
                    popUpFavBtn.setStyle("-fx-background-color: red");
                } else {
                    popUpFavBtn.setStyle("-fx-background-color: green");
                }
            }
        } else {
            final boolean code = cl.addFavorite(selected);
            if (code) {
                favoriteList.clear();
                favoriteList = cl.getFavorites();
                if (favoriteList.contains(selectedMedia)) {
                    popUpFavBtn.setStyle("-fx-background-color: red");
                } else {
                    popUpFavBtn.setStyle("-fx-background-color: green");
                }
            }
        }
        favoriteLayout();
    }

    /**
     * Rate an item.
     */
    public void rateIt() {
        // TODO: Not yet implemented.
    }

    /* Testing new feature */

    /**
     * Move up.
     */
    public void up() {
        if (clipRect.heightProperty().get() == 0) {
            return;
        }
        final Timeline timelineUp = new Timeline();
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

    /**
     * Move down.
     */
    public void down() {
        if (clipRect.heightProperty().get() != 0) {
            return;
        }
        final Timeline timelineDown = new Timeline();
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

    /**
     * Move right.
     */
    public void right() {
        final Timeline timelineDown = new Timeline();
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

    /**
     * Move left.
     */
    public void left() {
        final Timeline timelineDown = new Timeline();

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

    /**
     * Translate in the x direction.
     */
    public void xTranslate() {
        if (leftRigth) {
            right();
            leftRigth = false;
        } else {
            left();
            leftRigth = true;
        }
    }

    /**
     * Produce an effect for an image.
     *
     * @param height
     *            The height of the image.
     * @return The event handler for the generated effect.
     */
    private EventHandler<ActionEvent> imageEffect(final double height) {
        final int cycleCount = 4;
        final Timeline timelineBounce = new Timeline();
        timelineBounce.setCycleCount(cycleCount);
        timelineBounce.setAutoReverse(true);
        final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), (height
                - 15));
        final KeyValue kv2 = new KeyValue(clipRect.translateYProperty(), 15);
        final KeyValue kv3 = new KeyValue(growingPane.translateYProperty(),
                -15);

        final KeyFrame kf1 = new KeyFrame(Duration.millis(100), kv1, kv2, kv3);
        timelineBounce.getKeyFrames().add(kf1);

        final EventHandler<ActionEvent> handler =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent event) {
                        timelineBounce.play();
                    }
                };
        return handler;
    }

    /**
     * Lay out the discover area.
     */
    public void discoverLayout() {
        int n = 0;
        final int dWidth = 3;
        final int dHeight = 4;
        if (discoverList == null) {
            discoverList = new ArrayList<MovieDb>();
        }
        discoverList = cl.discoverMovies();
        final Iterator itr = discoverList.iterator();
        while (itr.hasNext()) {
            final Multi media = (Multi) itr.next();
            final Image tempImage = cl.getImage(media);
        }
        final ScrollPane temp = (ScrollPane) discoverTab.getContent();
        discoverGrid = (GridPane) temp.getContent();
        discoverGrid.getChildren().clear();
        for (int i = 0; i < dHeight; i++) {
            for (int j = 0; j < dWidth; j++) {
                final MovieDb containt = discoverList.get(n);
                final Image tempImage = cl.getImage(containt);
                discoverImageList.add(tempImage);
                cache.put(tempImage, containt);
                final MediaObject ob = new MediaObject(tempImage, containt
                        .getTitle(), this);
                final VBox box = ob.getVBox();
                discoverGrid.add(box, j, i);
                n++;
            }

        }
    }

    /**
     * Produce the favorites view layout.
     */
    public void favoriteLayout() {
        final int dWidth = 3;
        final int dHeight = 4;
        final ScrollPane temp = (ScrollPane) favoritesTab.getContent();
        favoritesGrid = (GridPane) temp.getContent();
        favoritesGrid.getChildren().clear();
        final Iterator<Multi> itr = favoriteList.iterator();
        for (int i = 0; i < dHeight; i++) {
            for (int j = 0; j < dWidth && itr.hasNext(); j++) {
                Multi containt = itr.next();
                final Image tempImage = cl.getImage(containt);
                cache.put(tempImage, containt);
                switch (containt.getMediaType()) {
                case MOVIE:
                    final MediaObject ob = new MediaObject(tempImage,
                            ((MovieDb) containt).getTitle(), this);
                    final VBox box = ob.getVBox();
                    favoritesGrid.add(box, j, i);
                    break;
                case TV_SERIES:
                    final MediaObject ob1 = new MediaObject(tempImage,
                            ((TvSeries) containt).getName(), this);
                    final VBox box1 = ob1.getVBox();
                    favoritesGrid.add(box1, j, i);
                    break;
                default:
                    break;
                }
            }
        }
    }

    /**
     * Produce the now playing view layout.
     */
    public void nowPlayingLayout() {
        final int dWidth = 3;
        final int dHeight = 4;
        final ScrollPane temp = (ScrollPane) nowPlayingTab.getContent();
        nowPlayingGrid = (GridPane) temp.getContent();
        nowPlayingGrid.getChildren().clear();
        final Iterator<MovieDb> itr = cl.getNowPlaying().iterator();
        for (int i = 0; i < dHeight; i++) {
            for (int j = 0; j < dWidth && itr.hasNext(); j++) {
                final Multi containt = itr.next();
                final Image tempImage = cl.getImage(containt);
                cache.put(tempImage, containt);
                final MediaObject ob = new MediaObject(tempImage,
                        ((MovieDb) containt).getTitle(), this);
                final VBox box = ob.getVBox();
                nowPlayingGrid.add(box, j, i);
            }
        }
    }

    /**
     * Produce the upcoming view layout.
     */
    public void upComingLaying() {
        final int dWidth = 3;
        final int dHeight = 4;
        final ScrollPane temp = (ScrollPane) upComingTab.getContent();
        upComingGrid = (GridPane) temp.getContent();
        upComingGrid.getChildren().clear();
        final Iterator<MovieDb> itr = cl.getUpComping().iterator();
        for (int i = 0; i < dHeight; i++) {
            for (int j = 0; j < dWidth && itr.hasNext(); j++) {
                final Multi containt = itr.next();
                final Image tempImage = cl.getImage(containt);
                cache.put(tempImage, containt);
                final MediaObject ob = new MediaObject(tempImage,
                        ((MovieDb) containt).getTitle(), this);
                final VBox box = ob.getVBox();
                upComingGrid.add(box, j, i);
            }
        }
    }

    /**
     * Handler for a click in the discover area.
     *
     * @param image
     *            The image that was clicked.
     */
    public void clickImageInDiscovery(final Image image) {
        if (selectedMedia != null) {
            selectedMedia = null;
        }
        final Multi media = cache.get(image);
        if (media != null) {
            selectedMedia = media;
            populatePopUpPane(image);
            goToPopUpPane();
        }
    }

    /**
     * Determine whether the current search is for movies.
     *
     * @return True if it is for movies.
     */
    public boolean isMovies() {
        return isMovies;
    }

    /**
     * Get the url for a trailer.
     */
    public void getTrailerUrl() {
        final MovieDb mv = discoverList.get(0);
        final String url = cl.getVideo(mv);
        if (url != null) {
            engine = webView.getEngine();
            engine.load(url);
        }
    }

    /**
     * Show the log in page.
     */
    @FXML
    public void goToSignInPane() {
        if (!isAuthorized()) {
            signInPane.toFront();
            signInPane.setVisible(true);
            appPane.toBack();
            appPane.setVisible(false);
            mainSignInBtn.setText("Sign Out");
        } else {
            cl.endSession();
            mainSignInBtn.setText("Sign In");
        }
    }

    /**
     * Go to the app pane.
     */
    private void goToAppPane() {
        signInPane.toBack();
        signInPane.setVisible(false);
        appPane.toFront();
        appPane.setVisible(true);
    }

    /**
     * Execute the log-in actions using the entered data.
     */
    @FXML
    public void signIn() {
        final String userName = userNameField.getText();
        final String password = passWordField.getText();
        try {
            cl.startSession(userName, password);
            favoriteList = cl.getFavorites();
            System.out.println(favoriteList);
            favoriteLayout();
            goToAppPane();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the selected media item.
     *
     * @return The selected item.
     */
    private Multi getSelectedMedia() {
        return this.selectedMedia;
    }

    /**
     * Populate the pop-up pane.
     *
     * @param poster
     *            The poster to use.
     */
    private void populatePopUpPane(final Image poster) {
        final int maximumRating = 5;
        popUpDescription.setText(cl.getDescription(selectedMedia));
        popUpTitle.setText(cl.getTitle(selectedMedia));

        if (selectedMedia.getMediaType() == MediaType.MOVIE || selectedMedia
                .getMediaType() == MediaType.TV_SERIES) {
            crewDr.setVisible(true);
            crewWrt.setVisible(true);
            crewSd.setVisible(true);
            castingT.setVisible(true);
            List<PersonCast> casting = cl.getCasting(selectedMedia);
            List<PersonCrew> crew = cl.getCrew(selectedMedia);
            Iterator itr = casting.iterator();
            String actor = "";
            String dr = "";
            String wrt = "";
            String sd = "";
            while (itr.hasNext()) {
                PersonCast person = (PersonCast) itr.next();
                actor += person.getName() + ", ";
            }
            Iterator itr2 = crew.iterator();
            while (itr2.hasNext()) {
                PersonCrew person = (PersonCrew) itr2.next();
                if (person.getDepartment().equals("Directing")) {
                    dr += person.getName() + ", ";
                } else if (person.getDepartment().equals("Writing")) {
                    wrt += person.getName() + ", ";
                } else if (person.getDepartment().equals("Sound")) {
                    sd += person.getName() + ", ";
                }
            }
            writer.setText(wrt);
            director.setText(dr);
            soundPerson.setText(sd);
            castingActors.setText(actor);
        } else {
            crewDr.setVisible(false);
            crewWrt.setVisible(false);
            crewSd.setVisible(false);
            castingT.setVisible(false);
            writer.setText("");
            director.setText("");
            soundPerson.setText("");
            castingActors.setText("");
        }

        popUpImage.setImage(poster);
        if (selectedMedia.getMediaType() == MediaType.MOVIE || selectedMedia
                .getMediaType() == MediaType.TV_SERIES) {
            ratingDb = new Rating(maximumRating);
            ratingDb.setPartialRating(true);
            ratingDb.setUpdateOnHover(false);
            ratingDb.setRating(cl.getRating(selectedMedia));
            dbRating.getChildren().clear();
            dbRating.getChildren().add(ratingDb);
        } else {
            dbRating.getChildren().clear();
        }

        if (isAuthorized()) {
            userRating.getChildren().clear();
            userRating.setVisible(true);
            ratingUser = new Rating(maximumRating);
            ratingUser.setPartialRating(false);
            ratingUser.setUpdateOnHover(false);
            ratingUser.setRating(cl.getUserRating(selectedMedia));
            ratingUser.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(final MouseEvent event) {
                    cl.rateContent(selectedMedia, ratingUser.getRating() * 2);
                }

            });
            userRating.getChildren().clear();
            userRating.getChildren().add(ratingUser);
            if (favoriteList.contains(selectedMedia)) {
                popUpFavBtn.setStyle("-fx-background-color: red");
            } else {
                popUpFavBtn.setStyle("-fx-background-color: green");
            }
        } else {
            userRating.setVisible(false);
        }

    }

    /**
     * Open the pop up pane.
     */
    @FXML
    public void goToPopUpPane() {
        popUpPanel.toFront();
        popUpPanel.setVisible(true);
        appPane.toBack();
        appPane.setVisible(false);
        signInPane.toBack();
        signInPane.setVisible(false);
    }

    /**
     * Test if authentication has been performed.
     *
     * @return True if authentication is performed.
     */
    private boolean isAuthorized() {
        // TODO: add all the auth stuff that need to be done here
        if (cl.getSessionToken() == null) {
            popUpFavBtn.setDisable(true);
            addFavorites.setDisable(true);
            return false;
        }
        popUpFavBtn.setDisable(false);
        addFavorites.setDisable(false);
        return true;
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
        signInPane.toBack();
        signInPane.setVisible(false);
        // webViewPane.toBack();
        upComingLaying();
        nowPlayingLayout();
        discoverLayout();

    }
}

package package1;
import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.keywords.Keyword;
import info.movito.themoviedbapi.model.tv.TvSeries;
import info.movito.themoviedbapi.tools.ApiUrl;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.ResultsPage;
import static info.movito.themoviedbapi.TmdbCollections.TMDB_METHOD_COLLECTION;
import static info.movito.themoviedbapi.TmdbLists.TMDB_METHOD_LIST;
import static info.movito.themoviedbapi.TmdbMovies.TMDB_METHOD_MOVIE;
import static info.movito.themoviedbapi.TmdbTV.TMDB_METHOD_TV;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

	MovieDBcl cl = new MovieDBcl();
	@FXML
	private TextField myTextField;
	@FXML
	private Labeled myMessage;
	@FXML
	private TextArea myTextArea;
	@FXML 
	private ListView myListView;
	@FXML
	private MenuButton myMenuButton;
	@FXML 
	private MenuItem myMenuItem;

	private String userInput1;
	private String moviesList;
	private String showsList;

	public void btnMenuChange() {
		
		if(myMenuButton.getText().equals("Movies")) {
			myMenuButton.setText("Shows");
			myMenuItem.setText("Movies");
		}
		else
		{
			myMenuButton.setText("Movies");
			myMenuItem.setText("Shows");
		}
	}
	
	public void btnSearch() {
		
		userInput1 = myTextField.getText();
		if (!userInput1.equals("")){
			myMessage.setText("Search results for " + userInput1);
			if(myMenuButton.getText().equals("Movies")){
				moviesList = cl.SearchingMovies(userInput1).toString();
				ObservableList<MovieDb> movies = FXCollections.observableArrayList
						(cl.SearchingMovies(userInput1));
				if(movies.size()> 0){
					myListView.setItems(movies);
				}
				else {
					myMessage.setText("No Movie results");
				}
			}
			else {
				showsList = cl.SearchingShows(userInput1).toString();
				ObservableList<TvSeries> shows = FXCollections.observableArrayList
						(cl.SearchingShows(userInput1));
				if(shows.size()>0) {
					myListView.setItems(shows);
				}
				else {
					myMessage.setText("No Show results");
				}
			}
			myTextArea.setText(moviesList + showsList);
		}
		else {
			myMessage.setText("Invalid Input");
		}
	}
	
	public void addFavorites() {

	}
	
	public void rateIt() {

	}
}
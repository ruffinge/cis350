package package1;
import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.keywords.Keyword;
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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Labeled;
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
	private TableView myTextView;
	private String userInput1;
	private String moviesList;
	private String showsList;

	public void btnSearch() {
		userInput1 = myTextField.getText();
		moviesList = cl.SearchingMovies(userInput1).toString();
		showsList = cl.SearchingShows(userInput1).toString();
		ObservableList<String> movies = FXCollections.observableArrayList();
		//movies.add
		//myTextView.setItems(myArray);
		//System.out.println(moviesList);
		myTextArea.setText(moviesList + showsList);
	}
	public void addFavorites() {
		
	}
	public void rateIt() {
		
	}
	
}
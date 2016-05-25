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

import java.lang.reflect.Array;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {

	MovieDBcl cl = new MovieDBcl();
	@FXML
	private TextField searchField;
	@FXML
	private Labeled myMessage;
	@FXML
	private TextArea myTextArea;
	@FXML 
	private ListView<Multi> listView;
	@FXML
	private MenuButton myMenuButton;
	@FXML 
	private MenuItem myMenuItem;
	
	@FXML
	private ImageView imageView;

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

	@FXML
	public void search() {
		String query = searchField.getText();
		if (query.equals("")) {
			listView.getItems().clear();
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
		if(selectedItem != null)
			 image = cl.getImage(selectedItem);
		if(image != null)
			imageView.setImage(image);
		//@TODO set wanted info here: title, description,...  
	}

	public void convertFormId(int id) {
		//take id and convert to movie/show that can call getTitle for list, 
		//getImage, getTrailer, etc.. for right side
	}
	public String getCast() {
		//may have to be asking for a list?
		//send selectedMovie/Show
		return null;
	}
	
	public void addFavorites() {

	}

	public void rateIt() {

	}
}
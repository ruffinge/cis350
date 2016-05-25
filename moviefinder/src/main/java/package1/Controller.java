package package1;

import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.tv.TvSeries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
	private MenuButton menuButton;
	@FXML 
	private MenuItem myMenuItem;
	@FXML
	private ImageView imageView;

	private String userInput1;
	private String moviesList;
	private String showsList;

	public void btnMenuChange() {

		if(menuButton.getText().equals("Movies")) {
			menuButton.setText("Shows");
			myMenuItem.setText("Movies");
		}
		else
		{
			menuButton.setText("Movies");
			myMenuItem.setText("Shows");
		}
	}
	      public void searching() {
		  
		          userInput1 = searchField.getText();
		        //change userinput check to before method call
		          if (!userInput1.equals("")){
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
package package1;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class MovieDBrun extends Application{

	//private String userInput;
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("package1/MainView.fxml"));
		primaryStage.setTitle("Movie DB");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	public static void main(String[] args){
		launch(args);
		//MovieDBcl mvdb = new MovieDBcl();
		//System.out.println(mvdb.Searching("Here"));
	}

}
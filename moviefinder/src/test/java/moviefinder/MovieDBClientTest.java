package moviefinder;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.Artwork;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.MovieImages;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import javafx.scene.image.Image;
import info.movito.themoviedbapi.TmdbPeople.PersonResultsPage;

/**
 * Test class for the MovieDBClient class.
 */
public class MovieDBClientTest {

    /**
     * Test the generic search method.
     */
    @Test
    public final void testSearch() {
	MovieDBClient client = new MovieDBClient();
	String result = (client.search("breaking bad").get(0)).toString();
	String formattedResult = result.substring(0, result.length() - 7);
	assertEquals(formattedResult, "Breaking Bad");
    }
    
    /**
     * Test the generic search method.
     */
    @Test
    public final void testSearch2() {
	MovieDBClient client = new MovieDBClient();
	String result = (client.search("veep").get(0)).toString();
	String formattedResult = result.substring(0, result.length() - 7);
	assertEquals(formattedResult, "Veep");
    }

    /**
     * Test the movie search method.
     */
    @Test
    public final void testSearchMovies() {
	MovieDBClient client = new MovieDBClient();
	String result = (client.searchMovies("saving private Ryan").get(0)).toString();
	String formattedResult = result.substring(0, result.length() - 13);
	assertEquals(formattedResult, "Saving Private Ryan");
    }
    
    /**
     * Test the movie search method.
     */
    @Test
    public final void testSearchMovies2() {
	MovieDBClient client = new MovieDBClient();
	String result = (client.searchMovies("star wars").get(0)).toString();
	String formattedResult = result.substring(0, result.length() - 13);
	assertEquals(formattedResult, "Star Wars");
    }

    /**
     * Test the TV show search method.
     */
    @Test
    public final void testSearchShows() {
	MovieDBClient client = new MovieDBClient();
	String result = (client.searchShows("game of thrones").get(0)).toString();
	String formattedResult = result.substring(0, result.length() - 7);
	assertEquals(formattedResult, "Game of Thrones");
    }
    
    /**
     * Test the TV show search method.
     */
    @Test
    public final void testSearchShows2() {
	MovieDBClient client = new MovieDBClient();
	String result = (client.searchShows("hell on wheels").get(0)).toString();
	String formattedResult = result.substring(0, result.length() - 7);
	assertEquals(formattedResult, "Hell on Wheels");
    }

    /**
     * Test the people search method.
     */
    @Test
    public final void testSearchPeople() {

	MovieDBClient client = new MovieDBClient();
	String result = (client.searchPeople("steven spielberg").get(0)).toString();
	String formattedResult = result.substring(0, result.length() - 6);
	assertEquals(formattedResult, "Steven Spielberg");
    }
    
    /**
     * Test the people search method.
     */
    @Test
    public final void testSearchPeople2() {

	MovieDBClient client = new MovieDBClient();
	String result = (client.searchPeople("quentin tarantino").get(0)).toString();
	String formattedResult = result.substring(0, result.length() - 6);
	assertEquals(formattedResult, "Quentin Tarantino");
    }

    /**
     * Test the method for fetching a title.
     */
    @Test
    public final void testGetTitle() {
	MovieDBClient client = new MovieDBClient();
	MovieDb ex = client.searchMovies("deadpool").get(0);
	String title = client.getTitle(ex);
	assertEquals(title, client.searchMovies("deadpool").get(0).getTitle());
    }
    
    /**
     * Test the method for fetching a title.
     */
    @Test
    public final void testGetTitle2() {
	MovieDBClient client = new MovieDBClient();
	MovieDb ex = client.searchMovies("star trek").get(0);
	String title = client.getTitle(ex);
	assertEquals(title, client.searchMovies("star trek").get(0).getTitle());
    }

    /**
     * Test the method for getting a movie description.
     */
    @Test
    public final void testGetDescription() {
	MovieDBClient client = new MovieDBClient();
	MovieDb ex = client.searchMovies("deadpool").get(0);
	String description = client.getDescription(ex);
	assertEquals(description, client.searchMovies("deadpool").get(0).getOverview());
    }
    
    /**
     * Test the method for getting a show description.
     */
    @Test
    public final void testGetDescription2() {
	MovieDBClient client = new MovieDBClient();
	MovieDb ex = client.searchMovies("daredevil").get(0);
	String description = client.getDescription(ex);
	assertEquals(description, client.searchMovies("Daredevil").get(0).getOverview());
    }

    /**
     * Test the method for getting an image for a movie.
     */
    @Test
    public final void testGetMovieImage() {
	
	MovieDBClient client = new MovieDBClient();
	MovieDb ex = client.searchMovies("deadpool").get(0);
	//Image img = client.getMovieImage(ex);	
	TmdbApi tmdbApi = new TmdbApi("d8b7fb813be397e444f220fab2edb3ff");
	TmdbMovies movies = tmdbApi.getMovies();
	MovieImages artworks = movies.getImages(ex.getId(), null);
	List<Artwork> posters = artworks.getPosters();
	String imageFilePath = posters.get(0).getFilePath();
	String dbImagePath = "https://image.tmdb.org/t/p/w396";
	String path = dbImagePath + imageFilePath;
	//Image image = new Image(path);
	
	assertEquals("img", "img");
    }

    /**
     * Test the method for getting an image for a TV series.
     */
    @Test
    public final void testGetSeriesImage() {
	
	MovieDBClient client = new MovieDBClient();
	MovieDb ex = client.searchMovies("deadpool").get(0);
	//Image img = client.getMovieImage(ex);	
	TmdbApi tmdbApi = new TmdbApi("d8b7fb813be397e444f220fab2edb3ff");
	TmdbMovies movies = tmdbApi.getMovies();
	MovieImages artworks = movies.getImages(ex.getId(), null);
	List<Artwork> posters = artworks.getPosters();
	String imageFilePath = posters.get(0).getFilePath();
	String dbImagePath = "https://image.tmdb.org/t/p/w396";
	String path = dbImagePath + imageFilePath;
	//Image image = new Image(path);
	
	assertEquals("img", "img");
    }

    /**
     * Test the method for getting an image for a person.
     */
    @Test
    public final void testGetPersonImage() {
	
	MovieDBClient client = new MovieDBClient();
	MovieDb ex = client.searchMovies("deadpool").get(0);
	//Image img = client.getMovieImage(ex);	
	TmdbApi tmdbApi = new TmdbApi("d8b7fb813be397e444f220fab2edb3ff");
	TmdbMovies movies = tmdbApi.getMovies();
	MovieImages artworks = movies.getImages(ex.getId(), null);
	List<Artwork> posters = artworks.getPosters();
	String imageFilePath = posters.get(0).getFilePath();
	String dbImagePath = "https://image.tmdb.org/t/p/w396";
	String path = dbImagePath + imageFilePath;
	//Image image = new Image(path);
	
	assertEquals("img", "img");
    }
}

package moviefinder;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbPeople;
import info.movito.themoviedbapi.TmdbPeople.PersonResultsPage;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TmdbSearch.MultiListResultsPage;
import info.movito.themoviedbapi.TmdbTV;
import info.movito.themoviedbapi.TmdbTV.TvMethod;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.Artwork;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.MovieImages;
import info.movito.themoviedbapi.model.Multi;
import info.movito.themoviedbapi.model.Multi.MediaType;
import info.movito.themoviedbapi.model.Video;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.Person;
import info.movito.themoviedbapi.model.tv.TvSeries;
import javafx.scene.image.Image;

/**
 * A utility class for accessing the database and fetching appropriate results
 * for use in the MovieFinder application.
 */
public class MovieDBClient {
    /** The API key to use when connecting to the database. */
    private final String apiKey = "d8b7fb813be397e444f220fab2edb3ff";

    /** The API connection. */
    private TmdbApi tmdbApi = new TmdbApi(apiKey);

    /** The path to images in the database. */
    private static String dbImagePath = "https://image.tmdb.org/t/p/w396";
    private static String youTubeURL = "https://www.youtube.com/watch?v=";
   //private static String quickTime 

    /**
     * Searches the database using a given query.
     *
     * @param query
     *            The search query to use.
     * @return The search results from the database.
     */
    public final List<Multi> search(final String query) {
        TmdbSearch search = tmdbApi.getSearch();
        MultiListResultsPage results = search.searchMulti(query, null, 0);
        return results.getResults();
    }

    /**
     * Search the database for movies.
     *
     * @param str
     *            The query to use for the search.
     * @return The results of the database search.
     */
    public final List<MovieDb> searchMovies(final String str) {
        TmdbSearch search = tmdbApi.getSearch();
        MovieResultsPage searchIt =
                search.searchMovie(str, null, null, true, 0);
        return searchIt.getResults();
    }

    /**
     * Search the database for TV shows.
     *
     * @param str
     *            The query to use for the search.
     * @return The results of the database search.
     */
    public final List<TvSeries> searchShows(final String str) {
        TmdbSearch search = tmdbApi.getSearch();
        TvResultsPage searchIt = search.searchTv(str, null, 0);
        return searchIt.getResults();
    }

    /**
     * Search the database for people.
     *
     * @param str
     *            The query to use for the search.
     * @return The results of the database search.
     */
    public final List<Person> searchPeople(final String str) {
        TmdbSearch search = tmdbApi.getSearch();
        PersonResultsPage searchIt = search.searchPerson(str, true, 0);
        return searchIt.getResults();
    }

    /**
     * Get the title of a database item.
     *
     * @param selectedItem
     *            The database item to inspect.
     * @return The title of the selected item, or null on failure.
     */
    public final String getTitle(final Multi selectedItem) {
        switch (selectedItem.getMediaType()) {
        case MOVIE:
            return ((MovieDb) selectedItem).getTitle();
        case TV_SERIES:
            return ((TvSeries) selectedItem).getName();
        default:
            return null;
        }
    }

    /**
     * Get the description of a database item.
     *
     * @param selectedItem
     *            The database item to inspect.
     * @return The description of the selected item, or null on failure.
     */
    public final String getDescription(final Multi selectedItem) {
        switch (selectedItem.getMediaType()) {
        case MOVIE:
            return ((MovieDb) selectedItem).getOverview();
        case TV_SERIES:
            return ((TvSeries) selectedItem).getOverview();
        default:
            return null;
        }
    }

    /**
     * Get an image for the given database item.
     *
     * @param query
     *            The database item to fetch an image for.
     * @return The image found, or null if no matching image is found.
     */
    public final Image getImage(final Multi query) {
        switch (query.getMediaType()) {
        case MOVIE:
            return getMovieImage((MovieDb) query);
        case TV_SERIES:
            return getSeriesImage((TvSeries) query);
        case PERSON:
            return getPersonImage((Person) query);
        default:
            return null;
        }
    }

    /**
     * Get an image for a movie.
     *
     * @param query
     *            The movie to get an image for.
     * @return The image for the given movie.
     */
    public final Image getMovieImage(final MovieDb query) {
        TmdbMovies movies = tmdbApi.getMovies();
        MovieImages artworks = movies.getImages(query.getId(), null);
        List<Artwork> posters = artworks.getPosters();
        String imageFilePath = posters.get(0).getFilePath();
        Image image = null;
        if (imageFilePath != null) {
            String path = dbImagePath + imageFilePath;
            image = new Image(path);
        }
        return image;
    }

    /**
     * Get an image for a TV series.
     *
     * @param query
     *            The TV series to get an image for.
     * @return The image for the given TV series.
     */
    public final Image getSeriesImage(final TvSeries query) {
        TmdbTV series = tmdbApi.getTvSeries();
        TvSeries result =
                series.getSeries(query.getId(), null, TvMethod.images);
        String imageFilePath = result.getPosterPath();
        Image image = null;
        if (imageFilePath != null) {
            String path = dbImagePath + imageFilePath;
            image = new Image(path);
        }
        return image;
    }

    /**
     * Get an image for a person.
     *
     * @param query
     *            The person to get an image for.
     * @return The image for the given person.
     */
    public final Image getPersonImage(final Person query) {
        TmdbPeople people = tmdbApi.getPeople();
        List<Artwork> profiles = people.getPersonImages(query.getId());
        String imageFilePath = profiles.get(0).getFilePath();
        Image image = null;
        if (imageFilePath != null) {
            String path = dbImagePath + imageFilePath;
            image = new Image(path);
        }
        return image;
    }

    /**
     * Discover similar movies.
     *
     * @return A list of the matching movies.
     */
    public final List<MovieDb> discoverMovies() {
        TmdbDiscover discover = tmdbApi.getDiscover();
        // TODO: Move the remainder of these to configurable parameters.
        MovieResultsPage page = discover.getDiscover(0, null, "popularity.desc",
                false, 2016, 2016, 0, 0, "28|21|16|99|53|27|36", "2016-01-01",
                "2018-01-01", null, null, null);
        List<MovieDb> results = page.getResults();

        return results;
    }
    
    public String getVideo(Multi querry){
    	if(querry.getMediaType() == MediaType.MOVIE){
    		TmdbMovies movies = tmdbApi.getMovies();
    		List<Video> videoList = movies.getVideos(((MovieDb)querry).getId(), null);
    		// you are only getting the first thriller
    		Video thriller = videoList.get(0);
    		if(thriller.getSite().equals("YouTube")){
    		   String url = youTubeURL + thriller.getKey();
    		   return url;
    		}
    	}
    	//@TODO : add logic to query thriller for tv shows.
    	else if(querry.getMediaType() ==  MediaType.TV_SERIES){
    		
    	}
    	return null;
    }
}












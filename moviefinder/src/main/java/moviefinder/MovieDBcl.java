package moviefinder;

import info.movito.themoviedbapi.model.people.Person;
import info.movito.themoviedbapi.model.people.PersonPeople;
import info.movito.themoviedbapi.model.tv.TvSeries;
import javafx.scene.image.Image;
import java.util.List;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbPeople;
import info.movito.themoviedbapi.TmdbPeople.PersonResultsPage;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TmdbTV;
import info.movito.themoviedbapi.TmdbTV.TvMethod;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.TmdbSearch.MultiListResultsPage;
import info.movito.themoviedbapi.model.Artwork;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.MovieImages;
import info.movito.themoviedbapi.model.Multi;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class MovieDBcl {
    private final String apiKey = "d8b7fb813be397e444f220fab2edb3ff";
    private TmdbApi tmdbApi = new TmdbApi(apiKey);
    private static String dbImagePath = "https://image.tmdb.org/t/p/w396";

    public List<Multi> Search(String query) {
	TmdbSearch search = tmdbApi.getSearch();
	MultiListResultsPage results = search.searchMulti(query, null, 0);
	return results.getResults();
    }

    public List<MovieDb> SearchingMovies(String str) {
	TmdbSearch search = tmdbApi.getSearch();
	TmdbMovies movies = tmdbApi.getMovies();
	MovieResultsPage searchIt = search.searchMovie(str, null, null, true, 0);
	return searchIt.getResults();
    }

    public List<TvSeries> SearchingShows(String str) {
	TmdbSearch search = tmdbApi.getSearch();
	TmdbTV shows = tmdbApi.getTvSeries();
	TvResultsPage searchIt = search.searchTv(str, null, 0);
	return searchIt.getResults();
    }

    public List<Person> SearchingPeople(String str) {
	TmdbSearch search = tmdbApi.getSearch();
	TmdbPeople people = tmdbApi.getPeople();
	PersonResultsPage searchIt = search.searchPerson(str, true, 0);
	return searchIt.getResults();
    }

    public Image getImage(Multi query) {
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

    public String getTitle(Multi selectedItem) {
	switch (selectedItem.getMediaType()) {
	case MOVIE:
	    return ((MovieDb) selectedItem).getTitle();
	case TV_SERIES:
	    return ((TvSeries) selectedItem).getName();
	default:
	    return null;
	}
    }

    public String getDescription(Multi selectedItem) {

	switch (selectedItem.getMediaType()) {
	case MOVIE:
	    return ((MovieDb) selectedItem).getOverview();
	case TV_SERIES:
	    return ((TvSeries) selectedItem).getOverview();
	default:
	    return null;
	}
    }

    public Image getMovieImage(MovieDb query) {
	TmdbMovies movies = tmdbApi.getMovies();
	MovieImages artworks = movies.getImages(query.getId(), null);
	List<Artwork> posters = artworks.getPosters();
	String imageFilePath = posters.get(0).getFilePath();
	Image image = null;
	if (imageFilePath != null) {
	    String path = dbImagePath + imageFilePath;
	    System.out.println(path);
	    image = new Image(path);
	}
	return image;
    }

    public Image getSeriesImage(TvSeries query) {
	TmdbTV series = tmdbApi.getTvSeries();
	TvSeries result = series.getSeries(query.getId(), null, TvMethod.images);
	String imageFilePath = result.getPosterPath();
	Image image = null;
	if (imageFilePath != null) {
	    String path = dbImagePath + imageFilePath;
	    System.out.println(path);
	    image = new Image(path);
	}
	return image;
    }

    public Image getPersonImage(Person query) {
	TmdbPeople people = tmdbApi.getPeople();
	List<Artwork> profiles = people.getPersonImages(query.getId());
	String imageFilePath = profiles.get(0).getFilePath();
	Image image = null;
	if (imageFilePath != null) {
	    String path = dbImagePath + imageFilePath;
	    System.out.println(path);
	    image = new Image(path);
	}
	return image;
    }

    // String sortBY, int votCount, String genre
    public List<MovieDb> discoverMovies() {

	TmdbDiscover discover = tmdbApi.getDiscover();
	MovieResultsPage page = discover.getDiscover(0, null, "popularity.desc", false, 2016, 2016, 0, 0,
		"28|21|16|99|53|27|36", "2016-01-01", "2018-01-01", null, null, null);
	List<MovieDb> results = page.getResults();

	for (int i = 0; i < results.size(); i++) {
	    // System.out.println(results.get(i));
	}

	return results;
    }

}

package package1;

import java.util.List;
import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.TmdbSearch.MultiListResultsPage;
import info.movito.themoviedbapi.TmdbTV.TvMethod;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.people.Person;
import info.movito.themoviedbapi.model.tv.TvSeries;
import javafx.scene.image.Image;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class MovieDBcl {
	private final String apiKey = "d8b7fb813be397e444f220fab2edb3ff";
	private TmdbApi tmdbApi = new TmdbApi(apiKey);
	private static String dbImagePath = "https://image.tmdb.org/t/p/w396";
	

	public List<MovieDb> SearchingMovies(String str) {

		TmdbSearch search = tmdbApi.getSearch();
		TmdbMovies movies = tmdbApi.getMovies();
		
		MovieResultsPage searchIt = search.searchMovie(str,null, null, true, 0);
		return searchIt.getResults();	
		
	}
	
	public List<TvSeries> SearchingShows(String str) {

		TmdbSearch search = tmdbApi.getSearch();
		TmdbTV shows = tmdbApi.getTvSeries();
		TvResultsPage searchIt = search.searchTv(str, null, 0);
		//int theShowId = searchIt.getResults().get(0).getId();
		//TvSeries theShow = shows.getSeries(theShowId, "en");
		
		return searchIt.getResults();
	}
	
	
	public List<Multi> Search(String query) {
		TmdbSearch search = tmdbApi.getSearch();
		MultiListResultsPage results = search.searchMulti(query, null, 2);
		return results.getResults();
	}

	public Image getImage(Multi query) {
		switch (query.getMediaType()) {
		case MOVIE:
			return getMovieImage((MovieDb) query);
		case TV_SERIES:
			return getSeriesImage((TvSeries) query);
		case PERSON:
			return getPersonImage((Person) query);
		default :	return null;
		}
	}

	public Image getMovieImage(MovieDb query) {
		TmdbMovies movies = tmdbApi.getMovies();
		MovieImages artworks = movies.getImages(query.getId(), null);
		List<Artwork> posters = artworks.getPosters();
		String imageFilePath = posters.get(0).getFilePath();
		Image image = null;
		if(imageFilePath != null){
			 String path = dbImagePath + imageFilePath ;
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
		if(imageFilePath != null){
			 String path = dbImagePath + imageFilePath ;
			 System.out.println(path);
			 image = new Image(path);
			 }
		return image;
	}
	
	public Image getPersonImage(Person query){
		TmdbPeople people = tmdbApi.getPeople();
		List<Artwork> profiles = people.getPersonImages(query.getId());
		String imageFilePath = profiles.get(0).getFilePath();
		Image image = null;
		if(imageFilePath != null){
			 String path = dbImagePath + imageFilePath ;
			 System.out.println(path);
			 image = new Image(path);
		}
		return image;
	}

}
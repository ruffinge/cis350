package package1;
import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.List;
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
import info.movito.themoviedbapi.model.core.IdElement;

public class MovieDBcl {
	private final String apiKey = "d8b7fb813be397e444f220fab2edb3ff";
	private TmdbApi tmdbApi = new TmdbApi(apiKey);
	//private String userInput;

	public List<MovieDb> SearchingMovies(String str) {

		TmdbSearch search = tmdbApi.getSearch();
		TmdbMovies movies = tmdbApi.getMovies();
		
		MovieResultsPage searchIt = search.searchMovie(str,null, null, true, 0);
		
		int theMovieId = searchIt.getResults().get(0).getId();
		
		MovieDb theMovie = movies.getMovie(theMovieId, "en");
	System.out.println(theMovie.getOverview());
	//cant get cast? list
	System.out.println(theMovie.getPopularity());
	
		return searchIt.getResults();	
		
	}
	
	public List<TvSeries> SearchingShows(String str) {

		TmdbSearch search = tmdbApi.getSearch();
		TmdbTV shows = tmdbApi.getTvSeries();
		TvResultsPage searchIt = search.searchTv(str, null, 0);
		int theShowId = searchIt.getResults().get(0).getId();
		TvSeries theShow = shows.getSeries(theShowId, "en");
		
		return searchIt.getResults();
	}
}
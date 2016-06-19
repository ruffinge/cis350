package moviefinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.omg.CORBA.TCKind;

import info.movito.themoviedbapi.TmdbAccount;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbAuthentication;
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
import info.movito.themoviedbapi.model.config.Account;
import info.movito.themoviedbapi.model.config.TokenAuthorisation;
import info.movito.themoviedbapi.model.config.TokenSession;
import info.movito.themoviedbapi.model.Video;
import info.movito.themoviedbapi.model.core.AccountID;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.ResponseStatus;
import info.movito.themoviedbapi.model.core.SessionToken;
import info.movito.themoviedbapi.model.people.Person;
import info.movito.themoviedbapi.model.tv.TvSeries;
import info.movito.themoviedbapi.tools.ApiUrl;
import javafx.scene.image.Image;
import info.movito.themoviedbapi.tools.ApiUrl;
/**
 * A utility class for accessing the database and fetching appropriate results
 * for use in the MovieFinder application.
 */
public class MovieDBClient {
    /** The API key to use when connecting to the database. */
    private final static String apiKey = "d8b7fb813be397e444f220fab2edb3ff";

    /** The API connection. */
    private static TmdbApi tmdbApi = new TmdbApi(apiKey);
    
    private static SessionToken sessionToken;
	private String password;
	private String user; 
    
	private TmdbAccount currentAccount;
    /** The path to images in the database. */
    private static String dbImagePath = "https://image.tmdb.org/t/p/w396";
    private static String youTubeURL = "https://www.youtube.com/watch?v=";
   //private static String quickTime 
	private List<Multi> favorites = new ArrayList<Multi>();
	private HashMap<Multi,Image> imageCach = new HashMap<Multi,Image>();
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
    	
    	if( imageCach.get(query) != null ){
    		return imageCach.get(query);
    	}
    	
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
        Image image = null;
        MovieImages artworks = movies.getImages(query.getId(), null);
        List<Artwork> posters = artworks.getPosters();
        if(posters.isEmpty() == false){
        	String imageFilePath = posters.get(0).getFilePath();
            if (imageFilePath != null) {
                String path = dbImagePath + imageFilePath;
                image = new Image(path);
                imageCach.put(query,image);
            }	
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
            imageCach.put(query,image);
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
    	Image image = null;
        TmdbPeople people = tmdbApi.getPeople();
        List<Artwork> profiles = people.getPersonImages(query.getId());
        if(profiles.isEmpty() == false){
        	String imageFilePath = profiles.get(0).getFilePath();
            if (imageFilePath != null) {
                String path = dbImagePath + imageFilePath;
                image = new Image(path);
                imageCach.put((Multi) query,image);
            }
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
    
    public void startSession(String pUser, String pPassword)throws Exception{
		if(pUser == null || pPassword == null)
			 throw new Exception("Invalid user information.");
		
	    this.user = new String(pUser);
	    this.password = new String(pPassword);
	    sessionToken = getSessionToken(this.user, this.password);
	    if(sessionToken != null){
	    	this.currentAccount = tmdbApi.getAccount();
	    }
	}
    
    public void endSession(){
        this.sessionToken = null;
    }
	
	private static SessionToken getSessionToken(String user, String password) {
		
		TmdbAuthentication tmdbAuth = tmdbApi.getAuthentication();
		TokenAuthorisation tokenAuth = tmdbAuth.getLoginToken(tmdbAuth.getAuthorisationToken(),user, password);
		TokenSession tokenSession = tmdbAuth.getSessionToken(tokenAuth);
		String sessionId = tokenSession.getSessionId();
		SessionToken sessionToken = new SessionToken(sessionId);
		
		return sessionToken;
	}
	
	public SessionToken getSessionToken(){
		return sessionToken;
	} 
	
	public String getUser(){
		return this.user;
	}
	
	//@TODO fox the hard coded number in the method later
	public List<Multi> getFavorites() {
	
		favorites = new ArrayList<Multi>();
		List<MovieDb> movies = new ArrayList<MovieDb>();
		List<TvSeries> series = new ArrayList<TvSeries>();
		Account account = currentAccount.getAccount(getSessionToken());
		if (account != null) {
			AccountID id = new AccountID(account.getId());
			MovieResultsPage favoriteMovies = currentAccount.getFavoriteMovies(this.getSessionToken(), id);
			TvResultsPage favoriteSeries = currentAccount.getFavoriteSeries(this.getSessionToken(), id, 3);
			movies = favoriteMovies.getResults();
			series = favoriteSeries.getResults();
			favorites.addAll(movies);
			favorites.addAll(series);

		}
		return favorites;
	}
	
	public boolean addFavorite(Multi media){
		if(media == null)
			return false;
		
		Account account = currentAccount.getAccount(getSessionToken());
		AccountID id = new AccountID(account.getId());
		MediaType mediaType = media.getMediaType();
		info.movito.themoviedbapi.TmdbAccount.MediaType type; 
		Integer mediaId = null; 
		
		switch(mediaType){
		  case MOVIE : mediaId = ((MovieDb)media).getId();
		               type = info.movito.themoviedbapi.TmdbAccount.MediaType.MOVIE ;
			  break;
		  case  TV_SERIES : mediaId = ((TvSeries)media).getId(); 
		  					type = info.movito.themoviedbapi.TmdbAccount.MediaType.TV ;
		  	 break;
		  default : 
			       return false;
		}
		
		
		if (account != null) {
			
			if(favorites.contains(media)){
				ResponseStatus status = currentAccount.removeFavorite(getSessionToken(), id, mediaId, type);
				if( status.getStatusCode() == 13){
					favorites.remove(media);
					return true;
				}
					
			}
			else{
				ResponseStatus status =  currentAccount.addFavorite(getSessionToken(),id,mediaId,type);
				if( status.getStatusCode() == 1)
					return true;
			}	
		}
		return false;
	}
	public List<MovieDb> getNowPlaying(){
		TmdbMovies mv = tmdbApi.getMovies();
		MovieResultsPage results = mv.getNowPlayingMovies(null,0);
		return results.getResults();
	}
	
	public List<MovieDb> getUpComping(){
		TmdbMovies mv = tmdbApi.getMovies();
		MovieResultsPage results = mv.getUpcoming(null,0);
		return results.getResults();
	}
	
	public double getRating(Multi query){
		switch(query.getMediaType()){
		case MOVIE: return ((MovieDb) query).getVoteAverage() / 2;
		case TV_SERIES: return ((TvSeries)query).getVoteAverage() / 2;
		}
		return 0;
	}
	
}












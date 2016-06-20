package moviefinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import info.movito.themoviedbapi.model.Video;
import info.movito.themoviedbapi.model.config.Account;
import info.movito.themoviedbapi.model.config.TokenAuthorisation;
import info.movito.themoviedbapi.model.config.TokenSession;
import info.movito.themoviedbapi.model.core.AccountID;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.ResponseStatus;
import info.movito.themoviedbapi.model.core.SessionToken;
import info.movito.themoviedbapi.model.people.Person;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.tv.TvSeries;
import javafx.scene.image.Image;

/**
 * A utility class for accessing the database and fetching appropriate results
 * for use in the MovieFinder application.
 */
public final class MovieDBClient {
    /** The API key to use when connecting to the database. */
    private static final String APIKEY = "d8b7fb813be397e444f220fab2edb3ff";

    /** The API connection. */
    private static TmdbApi tmdbApi = new TmdbApi(APIKEY);

    /** The token obtained for the current session. */
    private static SessionToken sessionToken;
    /** The user-entered password. */
    private String password;
    /** The user-entered username. */
    private String user;

    /** The currently logged in account. */
    private TmdbAccount currentAccount;
    /** The path to images in the database. */
    private static String dbImagePath = "https://image.tmdb.org/t/p/w396";
    /** The url prefix for youtube. */
    private static String youTubeURL = "https://www.youtube.com/watch?v=";
    /** The user's favorites. */
    private List<Multi> favorites = new ArrayList<Multi>();
    /** A cache of images. */
    private final HashMap<Multi, Image> imageCach = new HashMap<Multi, Image>();

    /**
     * Searches the database using a given query.
     *
     * @param query
     *            The search query to use.
     * @return The search results from the database.
     */
    public List<Multi> search(final String query) {
        final TmdbSearch search = tmdbApi.getSearch();
        final MultiListResultsPage results = search.searchMulti(query, null, 0);
        return results.getResults();
    }

    /**
     * Search the database for movies.
     *
     * @param str
     *            The query to use for the search.
     * @return The results of the database search.
     */
    public List<MovieDb> searchMovies(final String str) {
        final TmdbSearch search = tmdbApi.getSearch();
        final MovieResultsPage searchIt =
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
    public List<TvSeries> searchShows(final String str) {
        final TmdbSearch search = tmdbApi.getSearch();
        final TvResultsPage searchIt = search.searchTv(str, null, 0);
        return searchIt.getResults();
    }

    /**
     * Search the database for people.
     *
     * @param str
     *            The query to use for the search.
     * @return The results of the database search.
     */
    public List<Person> searchPeople(final String str) {
        final TmdbSearch search = tmdbApi.getSearch();
        final PersonResultsPage searchIt = search.searchPerson(str, true, 0);
        return searchIt.getResults();
    }

    /**
     * Get the title of a database item.
     *
     * @param selectedItem
     *            The database item to inspect.
     * @return The title of the selected item, or null on failure.
     */
    public String getTitle(final Multi selectedItem) {
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
    public String getDescription(final Multi selectedItem) {
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
    public Image getImage(final Multi query) {

        if (imageCach.get(query) != null) {
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
    public Image getMovieImage(final MovieDb query) {
        final TmdbMovies movies = tmdbApi.getMovies();
        Image image = null;
        final MovieImages artworks = movies.getImages(query.getId(), null);
        final List<Artwork> posters = artworks.getPosters();
        if (!posters.isEmpty()) {
            final String imageFilePath = posters.get(0).getFilePath();
            if (imageFilePath != null) {
                final String path = dbImagePath + imageFilePath;
                image = new Image(path);
                imageCach.put(query, image);
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
    public Image getSeriesImage(final TvSeries query) {
        final TmdbTV series = tmdbApi.getTvSeries();
        final TvSeries result =
                series.getSeries(query.getId(), null, TvMethod.images);
        final String imageFilePath = result.getPosterPath();
        Image image = null;
        if (imageFilePath != null) {
            final String path = dbImagePath + imageFilePath;
            image = new Image(path);
            imageCach.put(query, image);
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
    public Image getPersonImage(final Person query) {
        Image image = null;
        final TmdbPeople people = tmdbApi.getPeople();
        final List<Artwork> profiles = people.getPersonImages(query.getId());
        if (!profiles.isEmpty()) {
            final String imageFilePath = profiles.get(0).getFilePath();
            if (imageFilePath != null) {
                final String path = dbImagePath + imageFilePath;
                image = new Image(path);
                imageCach.put((Multi) query, image);
            }
        }

        return image;
    }

    /**
     * Discover similar movies.
     *
     * @return A list of the matching movies.
     */
    public List<MovieDb> discoverMovies() {
        final TmdbDiscover discover = tmdbApi.getDiscover();
        // TODO: Move the remainder of these to configurable parameters.
        final MovieResultsPage page =
                discover.getDiscover(0, null, "popularity.desc", false, 2016,
                        2016, 0, 0, "28|21|16|99|53|27|36", "2016-01-01",
                        "2018-01-01", null, null, null);
        final List<MovieDb> results = page.getResults();

        return results;
    }

    /**
     * Get the URL for a video for a given item.
     *
     * @param querry
     *            The item to get the video for.
     * @return The URL for the item's trailer.
     */
    public String getVideo(final Multi querry) {
        if (querry.getMediaType() == MediaType.MOVIE) {
            final TmdbMovies movies = tmdbApi.getMovies();
            final List<Video> videoList =
                    movies.getVideos(((MovieDb) querry).getId(), null);
            // you are only getting the first thriller
            final Video thriller = videoList.get(0);
            if (thriller.getSite().equals("YouTube")) {
                final String url = youTubeURL + thriller.getKey();
                return url;
            }
        } else if (querry.getMediaType() == MediaType.TV_SERIES) {
            // TODO: add logic to query trailer for tv shows.
        }
        return null;
    }

    /**
     * Start a session.
     *
     * @param pUser
     *            The username for the connection.
     * @param pPassword
     *            The password for the connection.
     * @throws IllegalArgumentException
     *             Thrown if one of the arguments is null.
     */
    public void startSession(final String pUser, final String pPassword)
            throws IllegalArgumentException {
        if (pUser == null || pPassword == null) {
            throw new IllegalArgumentException("Invalid user information.");
        }

        this.user = new String(pUser);
        this.password = new String(pPassword);
        sessionToken = getSessionToken(this.user, this.password);
        if (sessionToken != null) {
            this.currentAccount = tmdbApi.getAccount();
        }
    }

    /**
     * End the current session.
     */
    public void endSession() {
        MovieDBClient.sessionToken = null;
    }

    /**
     * Get a session token by logging in.
     *
     * @param user
     *            The username to use.
     * @param password
     *            The password to use.
     * @return The retrieved session token.
     */
    private static SessionToken getSessionToken(final String user,
            final String password) {

        final TmdbAuthentication tmdbAuth = tmdbApi.getAuthentication();
        final TokenAuthorisation tokenAuth = tmdbAuth.getLoginToken(
                tmdbAuth.getAuthorisationToken(), user, password);
        final TokenSession tokenSession = tmdbAuth.getSessionToken(tokenAuth);
        final String sessionId = tokenSession.getSessionId();
        sessionToken = new SessionToken(sessionId);

        return sessionToken;
    }

    /**
     * Get the current session token.
     *
     * @return The current session token.
     */
    public SessionToken getSessionToken() {
        return sessionToken;
    }

    /**
     * Get the username.
     *
     * @return The current user's username.
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Get a list of favorites.
     *
     * @return The list of the user's favorites.
     */
    // @TODO fix the hard coded number in the method later
    public List<Multi> getFavorites() {

        favorites = new ArrayList<Multi>();
        List<MovieDb> movies = new ArrayList<MovieDb>();
        List<TvSeries> series = new ArrayList<TvSeries>();
        final Account account = currentAccount.getAccount(getSessionToken());
        if (account != null) {
            final AccountID id = new AccountID(account.getId());
            final MovieResultsPage favoriteMovies = currentAccount
                    .getFavoriteMovies(this.getSessionToken(), id);
            final TvResultsPage favoriteSeries = currentAccount
                    .getFavoriteSeries(this.getSessionToken(), id, 3);
            movies = favoriteMovies.getResults();
            series = favoriteSeries.getResults();
            favorites.addAll(movies);
            favorites.addAll(series);

        }
        return favorites;
    }

    /**
     * Add an item to favorites.
     *
     * @param media
     *            The item to favorite.
     * @return True if successful, false if not.
     */
    public boolean addFavorite(final Multi media) {
        if (media == null) {
            return false;
        }

        final Account account = currentAccount.getAccount(getSessionToken());
        final AccountID id = new AccountID(account.getId());
        final MediaType mediaType = media.getMediaType();
        info.movito.themoviedbapi.TmdbAccount.MediaType type;
        Integer mediaId = null;

        switch (mediaType) {
        case MOVIE:
            mediaId = ((MovieDb) media).getId();
            type = info.movito.themoviedbapi.TmdbAccount.MediaType.MOVIE;
            break;
        case TV_SERIES:
            mediaId = ((TvSeries) media).getId();
            type = info.movito.themoviedbapi.TmdbAccount.MediaType.TV;
            break;
        default:
            return false;
        }

        if (account != null) {
            if (favorites.contains(media)) {
                final ResponseStatus status = currentAccount
                        .removeFavorite(getSessionToken(), id, mediaId, type);
                final int okResponse = 13;
                if (status.getStatusCode() == okResponse) {
                    favorites.remove(media);
                    return true;
                }

            } else {
                final ResponseStatus status = currentAccount
                        .addFavorite(getSessionToken(), id, mediaId, type);
                if (status.getStatusCode() == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get a list of currently playing movies.
     *
     * @return A list of currently playing movies.
     */
    public List<MovieDb> getNowPlaying() {
        final TmdbMovies mv = tmdbApi.getMovies();
        final MovieResultsPage results = mv.getNowPlayingMovies(null, 0);
        return results.getResults();
    }

    /**
     * Get a list of upcoming movies.
     *
     * @return The list of upcoming movies.
     */
    public List<MovieDb> getUpComping() {
        final TmdbMovies mv = tmdbApi.getMovies();
        final MovieResultsPage results = mv.getUpcoming(null, 0);
        return results.getResults();
    }

    /**
     * Get the average rating for an item.
     *
     * @param query
     *            The item whose rating is to be fetched.
     * @return The average rating of the item.
     */
    public double getRating(final Multi query) {
        switch (query.getMediaType()) {
        case MOVIE:
            return ((MovieDb) query).getVoteAverage() / 2;
        case TV_SERIES:
            return ((TvSeries) query).getVoteAverage() / 2;
        default:
            break;
        }
        return 0;
    }

    /**
     * Set a rating for some content.
     *
     * @param query
     *            The content to rate.
     * @param d
     *            The rating to set.
     */
    public void rateContent(final Multi query, final double d) {
        int rating = d < 1 ? 1 : (int) d;
        final Account account = currentAccount.getAccount(getSessionToken());
        if (account == null) {
            return;
        }

        final AccountID id = new AccountID(account.getId());
        final MediaType mediaType = query.getMediaType();
        switch (mediaType) {
        case MOVIE:
            final MovieDb mv = (MovieDb) query;
            currentAccount.postMovieRating(getSessionToken(), mv.getId(),
                    rating);
            break;
        case TV_SERIES:
            final TvSeries s = (TvSeries) query;
            currentAccount.postTvSeriesRating(getSessionToken(), s.getId(),
                    rating);
            break;
        default:
            break;
        }
    }

    /**
     * Get the user's rating for an item.
     *
     * @param query
     *            The item to get the rating for.
     * @return The user's rating.
     */
    public double getUserRating(final Multi query) {
        final Account account = currentAccount.getAccount(getSessionToken());
        if (account == null) {
            return 0;
        }

        final AccountID id = new AccountID(account.getId());
        final MediaType mediaType = query.getMediaType();
        switch (mediaType) {
        case MOVIE:
            final MovieDb mv = (MovieDb) query;
            final MovieResultsPage ratedMovies =
                    currentAccount.getRatedMovies(getSessionToken(), id, 1);
            final List<MovieDb> m = ratedMovies.getResults();
            MovieDb r = null;
            final Iterator<MovieDb> itr = m.iterator();
            while (itr.hasNext()) {
                r = itr.next();
                if (mv.getId() == r.getId()) {
                    return r.getUserRating() / 2;
                }
            }
            break;
        case TV_SERIES:
            final TvSeries sr = (TvSeries) query;
            final TvResultsPage ratedSeries =
                    currentAccount.getRatedTvSeries(getSessionToken(), id, 1);
            final List<TvSeries> tv = ratedSeries.getResults();
            TvSeries s = null;
            final Iterator<TvSeries> itr1 = tv.iterator();
            while (itr1.hasNext()) {
                s = itr1.next();
                if (sr.getId() == s.getId()) {
                    return s.getUserRating() / 2;
                }
            }
            break;
        default:
            break;
        }
        return 0;
    }

    /**
     * Get a list of the cast for an item.
     *
     * @param query
     *            The item to get the cast for.
     * @return A list of the cast.
     */
    public List<PersonCast> getCasting(final Multi query) {
        final MediaType mediaType = query.getMediaType();
        switch (mediaType) {
        case MOVIE:
            final MovieDb mv = (MovieDb) query;
            final List<PersonCast> cast = mv.getCast();
            return cast;
        default:
            return new ArrayList<PersonCast>();
        }
    }
}

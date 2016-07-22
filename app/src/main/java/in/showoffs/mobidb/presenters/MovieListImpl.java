package in.showoffs.mobidb.presenters;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.net.URL;

import in.showoffs.mobidb.API.Discover;
import in.showoffs.mobidb.MovieDBApplication;
import in.showoffs.mobidb.listeners.MovieListListener;
import in.showoffs.mobidb.models.movies.MoviesResult;
import in.showoffs.mobidb.utils.Constants;

/**
 * Created by nagraj on 22/7/16.
 */

public class MovieListImpl implements MovieListPresenter {

    MovieListListener movieListListener;
    Context context;

    public MovieListImpl(MovieListListener movieListListener){
        if (movieListListener == null) {
            throw new RuntimeException("MovieListListener is needed.");
        }
        this.movieListListener = movieListListener;
        this.context = movieListListener.getTheContext();
    }

    @Override
    public void loadMovieList(int page, String sortBy) {
        movieListListener.showLoading(true);
        movieListListener.showError(false);
        URL url = getUrl(page, sortBy);
        System.out.println(url);
        Ion.with(context)
                .load(url.toString())
                .as(new TypeToken<MoviesResult>(){})
                .setCallback(new FutureCallback<MoviesResult>() {
                    @Override
                    public void onCompleted(Exception e, MoviesResult moviesResults) {
                        if (null == e) {
                            movieListListener.onMovieListLoaded(moviesResults);
                        } else {
                            movieListListener.onErrorLoading(e);
                        }
                    }
                });
    }

    private URL getUrl(int page, String sortBy) {
        if (sortBy == null) {
            sortBy = Constants.SORT_BY_POPULARITY_DESC;
        }
        return new Discover().apiKey(MovieDBApplication.apiKey)
                .sortBy(sortBy)
                .language("en")
                .page(page)
                .buildUrl();
    }

    @Override
    public void refreshLoadData() {

    }

    @Override
    public void sortData() {

    }


}

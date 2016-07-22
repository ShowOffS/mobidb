package in.showoffs.mobidb.listeners;

import android.content.Context;

import in.showoffs.mobidb.models.movies.MoviesResult;

/**
 * Created by nagraj on 22/7/16.
 */

public interface MovieListListener {
    void showLoading(boolean show);
    void showError(boolean show);
    void onMovieListLoaded(MoviesResult moviesResult);
    void onErrorLoading(Exception e);
    Context getTheContext();
}

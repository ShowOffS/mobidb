package in.showoffs.mobidb.presenters;

import android.content.Context;

import in.showoffs.mobidb.listeners.MovieListListener;

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
        this.context = movieListListener.getContext();
    }

    @Override
    public void loadMovieList() {
        movieListListener.showLoading(true);
        movieListListener.showError(false);
        movieListListener.onMovieListLoaded();
    }

    @Override
    public void refreshLoadData() {

    }

    @Override
    public void sortData() {

    }
}

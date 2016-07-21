package in.showoffs.mobidb.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.showoffs.mobidb.R;
import in.showoffs.mobidb.listeners.MovieListListener;
import in.showoffs.mobidb.presenters.MovieListImpl;
import in.showoffs.mobidb.presenters.MovieListPresenter;

/**
 * A placeholder fragment containing a simple view.
 */
public class DashboardFragment extends Fragment implements MovieListListener{

    MovieListPresenter movieListP = new MovieListImpl(this);
    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieListP.loadMovieList();
    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    public void showError(boolean show) {

    }

    @Override
    public void onMovieListLoaded() {

    }

    @Override
    public void onErrorLoading() {

    }
}

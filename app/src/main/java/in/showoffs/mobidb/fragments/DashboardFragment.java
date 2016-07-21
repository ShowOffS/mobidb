package in.showoffs.mobidb.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.showoffs.mobidb.R;
import in.showoffs.mobidb.apapters.MovieListAdapter;
import in.showoffs.mobidb.listeners.MovieListListener;
import in.showoffs.mobidb.models.movies.MoviesResult;
import in.showoffs.mobidb.presenters.MovieListImpl;
import in.showoffs.mobidb.presenters.MovieListPresenter;

/**
 * A placeholder fragment containing a simple view.
 */
public class DashboardFragment extends Fragment implements MovieListListener{

    @BindView(R.id.movie_list_recycler)
    RecyclerView movieListRecycler;
    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,
            false);
    MovieListAdapter adapter = new MovieListAdapter();

    MovieListPresenter movieListP;
    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        movieListRecycler.setAdapter(adapter);
        movieListRecycler.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieListP = new MovieListImpl(this);
        movieListP.loadMovieList();
    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    public void showError(boolean show) {

    }

    @Override
    public void onMovieListLoaded(MoviesResult moviesResult) {
        adapter.add(moviesResult.getMovies());
    }

    @Override
    public void onErrorLoading(Exception e) {
        showError(true);
    }

    @Override
    public Context getTheContext() {
        return getContext();
    }
}

package in.showoffs.mobidb.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.State;
import in.showoffs.mobidb.R;
import in.showoffs.mobidb.activities.MovieDetails;
import in.showoffs.mobidb.apapters.MovieListAdapter;
import in.showoffs.mobidb.listeners.MovieListListener;
import in.showoffs.mobidb.models.movies.Movie;
import in.showoffs.mobidb.models.movies.MoviesResult;
import in.showoffs.mobidb.presenters.MovieListImpl;
import in.showoffs.mobidb.presenters.MovieListPresenter;
import in.showoffs.mobidb.utils.Constants;
import in.showoffs.mobidb.utils.SharedPrefUtils;


public class DashboardFragment extends Fragment implements MovieListListener,
        MovieListAdapter.MovieListAdapterListener {

    private static final String SAVED_LAYOUT_MANAGER = "RECYCLER_POSITION";
    private static final String SAVED_MOVIE_RESULTS = "MOVIE_RESULT";
    private static final String PAGE = "PAGE";
    private static final String TOTAL_PAGE = "TOTAL_PAGES";
    private static final String SORT_BY = "SORT_BY";
    @BindView(R.id.movie_list_recycler)
    RecyclerView movieListRecycler;
    @State
    int page = 0;
    @State
    int totalPages = 0;
    @State
    boolean mIsLoading = true;
    private String sortBy = Constants.SORT_BY_POPULARITY_DESC;
    private GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL,
            false);
    private MovieListAdapter adapter = new MovieListAdapter();
    private MovieListPresenter movieListP;

    DashboardInteractionListener interactionListener;

    public interface DashboardInteractionListener{
        void setTitle(String title);
    }
    private RecyclerView.OnScrollListener mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!mIsLoading) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    if (page < totalPages) {
                        mIsLoading = true;
                        movieListP.loadMovieList(page + 1, sortBy);
                    } else {
                        mIsLoading = false;
                    }
                }
            }
        }
    };

    public DashboardFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        adapter.setMListener(this);
        movieListRecycler.setAdapter(adapter);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getContext(), 3);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 5);
        }
        movieListRecycler.setLayoutManager(layoutManager);
        movieListRecycler.addOnScrollListener(mRecyclerViewOnScrollListener);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieListP = new MovieListImpl(this);
        sortBy = SharedPrefUtils.getSortValue();
        if (savedInstanceState == null) {
            movieListP.loadMovieList(0, sortBy);
            mIsLoading = true;
        } else {
            mIsLoading = false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DashboardInteractionListener) {
            interactionListener = (DashboardInteractionListener) context;
        } else {
            throw new ClassCastException(context.getClass().getName() + "Must implement DashboardInteractionListener");
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            page = savedInstanceState.getInt(PAGE);
            totalPages = savedInstanceState.getInt(TOTAL_PAGE);
            sortBy = savedInstanceState.getString(SORT_BY);
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
            List<Movie> savedMovies = savedInstanceState.getParcelableArrayList(SAVED_MOVIE_RESULTS);
            adapter.clear();
            adapter.add(savedMovies);
            movieListRecycler.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVED_LAYOUT_MANAGER, movieListRecycler.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArrayList(SAVED_MOVIE_RESULTS, (ArrayList<Movie>) adapter.getMovies());
        outState.putInt(PAGE, page);
        outState.putInt(TOTAL_PAGE, totalPages);
        outState.putString(SORT_BY, sortBy);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (sortBy.equals(Constants.SORT_BY_POPULARITY_DESC)) {
            menu.getItem(0).getSubMenu().getItem(0).setChecked(true);
            setTitle(Constants.POPULAR_MOVIES);
        } else if (sortBy.equals(Constants.SORT_BY_VOTE_AVERAGE_DESC)) {
            menu.getItem(0).getSubMenu().getItem(1).setChecked(true);
            setTitle(Constants.TOP_RATED);
        }
    }

    private void setTitle(String title) {
        if (interactionListener != null) {
            interactionListener.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular_menu: {
                item.setChecked(true);
                SharedPrefUtils.setSortValue(Constants.SORT_BY_POPULARITY_DESC);
                sortBy = Constants.SORT_BY_POPULARITY_DESC;
                adapter.clear();
                adapter.notifyDataSetChanged();
                movieListP.loadMovieList(0, sortBy);
                setTitle(Constants.POPULAR_MOVIES);
                break;
            }
            case R.id.rated_menu: {
                item.setChecked(true);
                SharedPrefUtils.setSortValue(Constants.SORT_BY_VOTE_AVERAGE_DESC);
                sortBy = Constants.SORT_BY_VOTE_AVERAGE_DESC;
                adapter.clear();
                adapter.notifyDataSetChanged();
                movieListP.loadMovieList(0, sortBy);
                setTitle(Constants.TOP_RATED);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    public void showError(boolean show) {

    }

    @Override
    public void onMovieListLoaded(MoviesResult moviesResult) {
        mIsLoading = false;
        page = moviesResult.getPage();
        totalPages = moviesResult.getTotalPages();
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

    @Override
    public void showMovieDetails(View view, Movie movie) {
        Intent intent = new Intent(view.getContext(), MovieDetails.class);
        intent.putExtra(Constants.TITLE, movie.getTitle());
        intent.putExtra(Constants.MOVIE, movie);
        intent.putExtra(MovieDetails.URI, (String.format("%s%s", "http://image.tmdb.org/t/p/w342", movie.getPosterPath())));
        Pair<View, String> p1 = Pair.create(view, "poster");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), p1);
        getActivity().startActivity(intent, options.toBundle());
    }
}

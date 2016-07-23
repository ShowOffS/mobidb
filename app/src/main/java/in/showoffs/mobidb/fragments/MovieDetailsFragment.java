package in.showoffs.mobidb.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.showoffs.mobidb.R;
import in.showoffs.mobidb.activities.MovieDetails;
import in.showoffs.mobidb.databinding.FragmentMovieDetailsBinding;
import in.showoffs.mobidb.models.movies.Movie;
import in.showoffs.mobidb.utils.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    private String uri;
    @BindView(R.id.imageView)
    SimpleDraweeView imageView;

    private Movie movie;
    public MovieDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uri = getActivity().getIntent().getStringExtra(MovieDetails.URI);
        movie = getActivity().getIntent().getParcelableExtra(Constants.MOVIE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMovieDetailsBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie_details, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        binding.setMovie(movie);
        imageView.setImageURI(uri);
        return view;
    }

}

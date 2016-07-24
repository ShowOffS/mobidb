package in.showoffs.mobidb.apapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.showoffs.mobidb.R;
import in.showoffs.mobidb.custom.ConstrainedImageView;
import in.showoffs.mobidb.models.movies.Movie;
import in.showoffs.mobidb.utils.SharedPrefUtils;

/**
 * Created by nagraj on 22/7/16
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    List<Movie> movieList = new ArrayList<>();

    MovieListAdapterListener mListener;

    public interface MovieListAdapterListener{
        void showMovieDetails(View view, Movie movie);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imageView.setImageURI(Uri.parse(String.format("%s%s", SharedPrefUtils.getBaseImageUrl(), getItem(position).getPosterPath())));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener == null) {
                    System.out.println("Not implemented");
                } else {
                    mListener.showMovieDetails(view, getItem(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList != null
                ? movieList.size()
                : 0;
    }

    public void add(List<Movie> movies) {
        movieList.addAll(movies);
        notifyDataSetChanged();
    }

    public void setMListener(MovieListAdapterListener mListener) {
        this.mListener = mListener;
    }

    private Movie getItem(int position) {
        return movieList.get(position);
    }

    public List<Movie> getMovies() {
        return movieList;
    }

    public void clear() {
        movieList.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ConstrainedImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

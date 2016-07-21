package in.showoffs.mobidb.apapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.showoffs.mobidb.R;
import in.showoffs.mobidb.models.movies.Movie;

/**
 * Created by nagraj on 22/7/16
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    List<Movie> movieList = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ion.with(holder.imageView)
                .load(String.format("%s%s", "http://image.tmdb.org/t/p/w342", getItem(position).getPosterPath()));
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

    private Movie getItem(int position) {
        return movieList.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}

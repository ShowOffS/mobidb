package in.showoffs.mobidb.listeners;

import android.content.Context;

/**
 * Created by nagraj on 22/7/16.
 */

public interface MovieListListener {
    void showLoading(boolean show);
    void showError(boolean show);
    void onMovieListLoaded();
    void onErrorLoading();
    Context getTheContext();
}

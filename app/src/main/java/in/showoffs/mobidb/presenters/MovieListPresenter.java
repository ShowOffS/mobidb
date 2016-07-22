package in.showoffs.mobidb.presenters;

/**
 * Created by nagraj on 22/7/16.
 */

public interface MovieListPresenter {
    void loadMovieList(int page, String sortBy);
    void refreshLoadData();
    void sortData();
}

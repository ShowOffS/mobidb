package in.showoffs.mobidb.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Gaurav Ravi on 7/22/2016.
 */

public class SharedPrefUtils {
    private static  Context context;
    private static SharedPrefUtils instance;

    public static SharedPrefUtils getInstance(Context mContext) {
        if (instance == null) {
            instance = new SharedPrefUtils(context);
        }
        return instance;
    }

    public SharedPrefUtils(Context mContext) {
        context = mContext;
    }

    public static void setSortValue(String sortBy) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.SHARED_PREF,
                Context.MODE_PRIVATE).edit();
        editor.putString(Constants.SHORT_FIED, sortBy);
        editor.commit();
    }

    public static String getSortValue() {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        return mPrefs.getString(Constants.SHORT_FIED, Constants.SORT_BY_POPULARITY_DESC);
    }

    public static void initialize(Context mContext) {
        instance = new SharedPrefUtils(mContext);
    }
}

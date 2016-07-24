package in.showoffs.mobidb.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.threeten.bp.Instant;

import in.showoffs.mobidb.models.config.Images;

/**
 * Created by Gaurav Ravi on 7/22/2016.
 */

public class SharedPrefUtils {
    private static Context context;
    private static SharedPrefUtils instance;

    public SharedPrefUtils(Context mContext) {
        context = mContext;
    }

    public static SharedPrefUtils getInstance(Context mContext) {
        if (instance == null) {
            instance = new SharedPrefUtils(context);
        }
        return instance;
    }

    public static String getSortValue() {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        return mPrefs.getString(Constants.SHORT_FIED, Constants.SORT_BY_POPULARITY_DESC);
    }

    public static void setSortValue(String sortBy) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.SHARED_PREF,
                Context.MODE_PRIVATE).edit();
        editor.putString(Constants.SHORT_FIED, sortBy);
        editor.commit();
    }

    public static long getLastConfigSaveTime() {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        return mPrefs.getLong(Constants.UPDATE_TIME, 0);
    }

    public static void setConfiguration(Images images) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.SHARED_PREF,
                Context.MODE_PRIVATE).edit();
        editor.putLong(Constants.UPDATE_TIME, Instant.now().getEpochSecond());
        String imgSize = "w342";
        if (!images.getPosterSizes().contains(imgSize)) {
            if (images.getPosterSizes().size() > 1) {
                imgSize = images.getPosterSizes().get(images.getPosterSizes().size() - 1);
            } else {
                imgSize = images.getPosterSizes().get(0);
            }
        }
        editor.putString(Constants.BASE_IMAGE_URL_CONFIG, images.getBaseUrl() + imgSize);
        editor.commit();
    }

    public static String  getBaseImageUrl() {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        return mPrefs.getString(Constants.BASE_IMAGE_URL_CONFIG, Constants.BASE_IMAGE_URL);
    }

    public static void initialize(Context mContext) {
        instance = new SharedPrefUtils(mContext);
    }
}

package in.showoffs.mobidb;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

import in.showoffs.mobidb.utils.SharedPrefUtils;

/**
 * Created by nagraj on 22/7/16.
 */

public class MovieDBApplication extends Application {
    private static final String TAG = "MovieDBApplication";
    public static String apiKey;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        SharedPrefUtils.initialize(this);
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            apiKey = bundle.getString("tmdb_api_key");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
    }


}

package in.showoffs.mobidb.presenters;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.threeten.bp.Instant;

import java.net.URL;

import in.showoffs.mobidb.API.Discover;
import in.showoffs.mobidb.MovieDBApplication;
import in.showoffs.mobidb.listeners.ConfigurationListener;
import in.showoffs.mobidb.models.config.Configuration;
import in.showoffs.mobidb.utils.Constants;

/**
 * Created by nagraj on 25/7/16.
 */

public class ConfigurationImpl implements ConfigurationPresenter {
    ConfigurationListener listener;
    Context context;

    public ConfigurationImpl(ConfigurationListener listener) {
        if (listener == null) {
            throw new RuntimeException("ConfigurationListener is needed.");
        }
        this.listener = listener;
        this.context = listener.getConfigContext();
    }
    @Override
    public void loadConfiguration(long updateTime) {
        if (Instant.now().getEpochSecond() - updateTime < 172800/*Total seconds in two days*/) {
            listener.configNotChanged();
            return;
        }
        URL url = getUrl();
        System.out.println(url);
        Ion.with(context)
                .load(url.toString())
                .as(new TypeToken<Configuration>() {
                })
                .setCallback(new FutureCallback<Configuration>() {
                    @Override
                    public void onCompleted(Exception e, Configuration configuration) {
                        if (null == e) {
                            listener.configurationLoaded(configuration);
                        } else {
                            listener.onErrorLoadingConfig(e);
                        }
                    }
                });
    }

    private URL getUrl() {
        return new Discover().apiKey(MovieDBApplication.apiKey)
                .withPath(Constants.CONFIGURATION)
                .buildUrl();
    }
}

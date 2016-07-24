package in.showoffs.mobidb.listeners;

import android.content.Context;

import in.showoffs.mobidb.models.config.Configuration;

/**
 * Created by nagraj on 25/7/16.
 */

public interface ConfigurationListener {
    void configurationLoaded(Configuration configuration);

    void onErrorLoadingConfig(Exception e);

    Context getConfigContext();

    void configNotChanged();
}

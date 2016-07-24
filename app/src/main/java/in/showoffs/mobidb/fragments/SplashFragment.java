package in.showoffs.mobidb.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.showoffs.mobidb.R;
import in.showoffs.mobidb.activities.Dashboard;
import in.showoffs.mobidb.listeners.ConfigurationListener;
import in.showoffs.mobidb.models.config.Configuration;
import in.showoffs.mobidb.presenters.ConfigurationImpl;
import in.showoffs.mobidb.presenters.ConfigurationPresenter;
import in.showoffs.mobidb.utils.SharedPrefUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class SplashFragment extends Fragment implements ConfigurationListener {

    ConfigurationPresenter confP;
    Handler handler;
    @BindView(R.id.loader)
    ProgressBar loader;
    @BindView(R.id.error)
    LinearLayout error;

    public SplashFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, view);
        handler = new Handler(Looper.getMainLooper());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        confP = new ConfigurationImpl(this);
        confP.loadConfiguration(SharedPrefUtils.getLastConfigSaveTime());
    }

    @Override
    public void configurationLoaded(Configuration configuration) {
        SharedPrefUtils.setConfiguration(configuration.getImages());
        loadDashboard();
    }

    @Override
    public void onErrorLoadingConfig(Exception e) {
        loader.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "Error loading Configuration. Try again later.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getConfigContext() {
        return getContext();
    }

    @Override
    public void configNotChanged() {
        loadDashboard();
    }

    private void loadDashboard() {
        final Intent intent = new Intent(getContext(), Dashboard.class);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                getActivity().finish();
            }
        }, 1000);
    }

    @OnClick(R.id.error)
    public void retry(View view) {
        error.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        confP.loadConfiguration(SharedPrefUtils.getLastConfigSaveTime());
    }
}

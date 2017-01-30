package nikhilg.dev.trekavenue.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import nikhilg.dev.trekavenue.Data.FilterParams;
import nikhilg.dev.trekavenue.Interfaces.NetworkRequestCallback;
import nikhilg.dev.trekavenue.Networking.NetworkCallManager;
import nikhilg.dev.trekavenue.Networking.NetworkURLs;
import nikhilg.dev.trekavenue.Networking.RequestTypes;
import nikhilg.dev.trekavenue.R;
import nikhilg.dev.trekavenue.TApplication;
import nikhilg.dev.trekavenue.Utils.Constants;

public class SplashActivity extends AppCompatActivity implements NetworkRequestCallback {

    private TextView appName;
    private boolean animationComplete, filterCallComplete;

    private boolean destroyed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        destroyed = false;

        initLayout();

        makeFiltersCall();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animationComplete = true;
                if (filterCallComplete)
                    navigate();
            }
        }, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initLayout() {
        appName = (TextView) findViewById(R.id.appName);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        appName.startAnimation(animation);
    }

    private void makeFiltersCall() {
        NetworkCallManager.getInstance().MakeJsonGetRequest(RequestTypes.FILTER_PARAMS, NetworkURLs.FILTER_PARAMS_URL, this, SplashActivity.class.getSimpleName());
    }

    private void navigate() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(int requestType, String response) {
        if (!destroyed) {
            if (requestType == RequestTypes.FILTER_PARAMS && response != null) {
                FilterParams filterParams = new Gson().fromJson(response, FilterParams.class);
                if (filterParams.getFlag() == Constants.NETWORK_CALL_SUCCESS_CODE) {
                    ((TApplication) getApplication()).setFilterParams(filterParams);
                    filterCallComplete = true;
                    if (animationComplete)
                        navigate();
                } else {
                    Toast.makeText(SplashActivity.this, filterParams.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onFailure(int requestType, String error) {
        if (requestType == RequestTypes.FILTER_PARAMS) {
            Toast.makeText(SplashActivity.this, "Failed to get data, please try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        destroyed = true;
        super.onDestroy();
    }
}

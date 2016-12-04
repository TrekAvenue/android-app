package nikhilg.dev.trekavenue.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nikhilg.dev.trekavenue.Interfaces.NetworkRequestCallback;
import nikhilg.dev.trekavenue.R;

public class HomeActivity extends AppCompatActivity implements NetworkRequestCallback {

    private boolean destroyed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        destroyed = false;
    }

    @Override
    protected void onDestroy() {
        destroyed = true;
        super.onDestroy();
    }

    @Override
    public void onSuccess(int requestType, String response) {

    }

    @Override
    public void onFailure(int requestType, String error) {

    }
}

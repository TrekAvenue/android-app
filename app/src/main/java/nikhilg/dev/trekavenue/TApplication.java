package nikhilg.dev.trekavenue;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import nikhilg.dev.trekavenue.Data.FilterParams;
import nikhilg.dev.trekavenue.Networking.NetworkCallManager;

/**
 * Created by nik on 3/12/16.
 */
public class TApplication extends Application {

    private final String TAG = TApplication.class.getSimpleName();

    public static RequestQueue networkRequestQueue;
    private static Context context;

    private FilterParams filterParams;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        TApplication.context = getApplicationContext();

        // make request queue for network calls
        MakeRequestQueue();

        // Initialise networkcallmanager
        NetworkCallManager.getInstance().setContext(this);
    }

    public static Context getAppContext() {
        return TApplication.context;
    }

    public void MakeRequestQueue() {
        networkRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public FilterParams getFilterParams() {
        return filterParams;
    }

    public void setFilterParams(FilterParams filterParams) {
        this.filterParams = filterParams;
    }
}

package nikhilg.dev.trekavenue;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by nik on 3/12/16.
 */
public class TApplication extends Application {

    public static RequestQueue networkRequestQueue;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MakeRequestQueue();
    }

    public void MakeRequestQueue() {
        networkRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }
}

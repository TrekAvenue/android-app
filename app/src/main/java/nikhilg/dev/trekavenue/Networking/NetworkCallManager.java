package nikhilg.dev.trekavenue.Networking;

import android.content.Context;
import android.provider.Settings;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import nikhilg.dev.trekavenue.Interfaces.NetworkRequestCallback;
import nikhilg.dev.trekavenue.TApplication;

/**
 * Created by nik on 4/12/16.
 */
public class NetworkCallManager {

    private static final String TAG = NetworkCallManager.class.getSimpleName();

    private static NetworkCallManager networkCallManager;
    private Context context;

    private NetworkCallManager() {}

    public static NetworkCallManager getInstance() {
        if (networkCallManager == null) {
            synchronized (NetworkCallManager.class) {
                if (networkCallManager == null) {
                    networkCallManager = new NetworkCallManager();
                }
            }
        }
        return networkCallManager;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void MakeJsonGetRequest(final int requestType, final String url, final NetworkRequestCallback callback, String className) {
        String versionCode = "";
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode + "";
        } catch (Exception ignored) {

        }
        Calendar myCal = Calendar.getInstance();
        String addValuesToUrl = "app_version=" + versionCode + "&channel_name=android_app" + "&r=" + myCal.getTimeInMillis() + "&device_id=" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        final String completeUrl;
        StringBuilder sb = new StringBuilder(100);
        if (url.contains("?")) {
            sb.append(url).append("&").append(addValuesToUrl);
        } else {
            sb.append(url).append("?").append(addValuesToUrl);
        }
        completeUrl = NetworkURLs.BASE_URL + sb.toString();
        Log.i(TAG, completeUrl);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, completeUrl, (JSONObject) null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, completeUrl);
                        Log.i(TAG, response.toString());
                        callback.onSuccess(requestType, response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            Log.i(TAG, completeUrl);
                            Log.i(TAG, new String(error.networkResponse.data));
                            callback.onFailure(requestType, new String(error.networkResponse.data));
                            return;
                        }
                        if (error instanceof TimeoutError) {
                            Log.i(TAG, completeUrl);
                            Log.i(TAG, "TimeOutError");
                            callback.onFailure(requestType, "TimeOutError");
                            return;
                        }
                        else {
                            Log.i(TAG, completeUrl);
                            Log.i(TAG, error.toString());
                            callback.onFailure(requestType, error.toString());
                            return;
                        }
                    }
                });
        jsonObjectRequest.setShouldCache(false);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setTag(className);
        TApplication.networkRequestQueue.add(jsonObjectRequest);
    }
}

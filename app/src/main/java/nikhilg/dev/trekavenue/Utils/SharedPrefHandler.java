package nikhilg.dev.trekavenue.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nik on 4/12/16.
 */
public class SharedPrefHandler {

    private static SharedPrefHandler sharedPrefHandler;

    private Context mContext;
    SharedPreferences sharedpreferences;

    private SharedPrefHandler(Context c) {
        this.mContext = c;
        sharedpreferences = c.getSharedPreferences("application_data", c.MODE_PRIVATE);
    }

    public static SharedPrefHandler getInstance(Context c) {
        if (sharedPrefHandler == null) {
            synchronized (SharedPrefHandler.class) {
                if (sharedPrefHandler == null) {
                    sharedPrefHandler = new SharedPrefHandler(c);
                }
            }
        }
        return sharedPrefHandler;
    }

    public void addvalue(String keyname, String value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(keyname, value);
        editor.commit();
    }

    public String getvalue(String keyname) {
        return sharedpreferences.getString(keyname, null);
    }

    public void addBoolValue(String keyname, Boolean value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(keyname, value);
        editor.commit();
    }

    public boolean getBoolValue(String keyname) {
        return sharedpreferences.getBoolean(keyname, false);
    }


    public void addIntValue(String keyname, int value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(keyname, value);
        editor.commit();
    }

    public int getIntValue(String keyname) {
        return sharedpreferences.getInt(keyname, 0);
    }
}

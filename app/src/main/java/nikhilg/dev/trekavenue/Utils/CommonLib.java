package nikhilg.dev.trekavenue.Utils;

import android.text.TextUtils;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nik on 7/1/17.
 */
public class CommonLib {

    // tracking a custom event with fabric's answer
    public static void onKeyMetric(String eventName, HashMap<String, String> attributes) {
        if (TextUtils.isEmpty(eventName))
            return;
        // create an instance of answers
        Answers answers = Answers.getInstance();

        // create a customevent with the received eventname
        CustomEvent event = new CustomEvent(eventName);

        // add custom attributes if available
        if (attributes != null && attributes.size() > 0) {
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                event.putCustomAttribute(entry.getKey(), entry.getValue());
            }
        }

        // log custom event
        answers.logCustom(event);
    }

}

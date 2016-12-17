package nikhilg.dev.trekavenue.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by nik on 17/12/16.
 */
public class IconView extends TextView {
    public IconView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Trek-Avenue.ttf", context);
        setTypeface(customFont);
    }
}

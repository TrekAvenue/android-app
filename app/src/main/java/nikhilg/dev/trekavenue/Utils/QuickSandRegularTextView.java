package nikhilg.dev.trekavenue.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by nik on 16/12/16.
 */
public class QuickSandRegularTextView extends TextView {

    public QuickSandRegularTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public QuickSandRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public QuickSandRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Quicksand-Regular.ttf", context);
        setTypeface(customFont);
    }
}

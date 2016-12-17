package nikhilg.dev.trekavenue.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by nik on 17/12/16.
 */
public class QuickSandBoldTextView extends TextView {
    public QuickSandBoldTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public QuickSandBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public QuickSandBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Quicksand-Bold.ttf", context);
        setTypeface(customFont);
    }
}

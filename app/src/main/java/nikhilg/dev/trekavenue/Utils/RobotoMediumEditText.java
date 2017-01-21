package nikhilg.dev.trekavenue.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by nik on 21/1/17.
 */
public class RobotoMediumEditText extends EditText {

    public RobotoMediumEditText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public RobotoMediumEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public RobotoMediumEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Roboto-Medium.ttf", context);
        setTypeface(customFont);
    }
}

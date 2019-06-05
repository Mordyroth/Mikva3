package pl.droidsonroids.gif;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

class GifImageView extends View {
    public GifImageView(Context context) {
        this(context, null);
    }

    public GifImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GifImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}

package com.test;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class ScrollTextView extends TextView {

    public ScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ScrollTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}

package com.zy.ypro.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class AccountInputView extends EditText {
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private Drawable left;
    private Drawable right;
    private boolean isEmpty;

    public AccountInputView(Context context) {
        super(context);
    }

    public AccountInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AccountInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (left == null) {
            left = getCompoundDrawables()[DRAWABLE_LEFT];
        }
        if (right == null) {
            right = getCompoundDrawables()[DRAWABLE_RIGHT];
        }
        isEmpty = text.length() == 0;
        setRightIconVisible(!isEmpty);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused && !isEmpty) {
            setRightIconVisible(true);
        } else {
            setRightIconVisible(false);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
            if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                    && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    private void setRightIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(left, null, visible ? right : null, null);
    }
}
package com.zy.ypro.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import androidx.viewpager2.widget.ViewPager2;

import com.zy.ypro.R;

public class PasswordInputView extends EditText {
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_RIGHT = 2;
    private Drawable left;
    private Drawable right;
    private boolean showPassword;
    private boolean isFocus;
    private int rightIconColor;
    private int textLength;

    public PasswordInputView(Context context) {
        super(context);
    }

    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        textLength = text.length();
        if (left == null) {
            left = getCompoundDrawables()[DRAWABLE_LEFT];
        }
        if (right == null) {
            right = getCompoundDrawables()[DRAWABLE_RIGHT];
            rightIconColor = getResources().getColor(android.R.color.darker_gray);
        }
        if (isFocus) {
            setRightIconVisible(true);
        } else {
            setRightIconVisible(false);
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        isFocus = focused;
        if (focused) {
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
                if (showPassword) {
                    rightIconColor = getResources().getColor(R.color.common_bg);
                    setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                    rightIconColor = getResources().getColor(android.R.color.darker_gray);
                }
                right.setColorFilter(rightIconColor, PorterDuff.Mode.SRC_IN);
                setCompoundDrawablesWithIntrinsicBounds(left, null, right, null);
                showPassword = !showPassword;
                setSelection(textLength);
            }
        }
        return super.onTouchEvent(event);
    }

    private void setRightIconVisible(boolean visible) {
        right.setColorFilter(rightIconColor, PorterDuff.Mode.SRC_IN);
        setCompoundDrawablesWithIntrinsicBounds(left, null, visible ? right : null, null);
    }
}
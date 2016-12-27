package com.ternence.twidget.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.ternence.twidget.R;

/**
 * Project Name: ternence-utils
 * File Name:    SelectorImageView.java
 * ClassName:    SelectorImageView
 *
 * Description: image with status of click
 *
 */
public class SelectorImageView extends ImageView
{
    /**
     * 5.0.
     */
    private int mRippleColor = ContextCompat.getColor(getContext(), R.color.ripple_material_light);


    public SelectorImageView(Context context)
    {
        super(context);

        initView();
    }

    public SelectorImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        initView();
    }

    public SelectorImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SelectorImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initView()
    {
        if (Build.VERSION.SDK_INT >= 21)
        {
            if (Build.VERSION.SDK_INT < 23)
            {
                RippleDrawable rippleDrawable = new RippleDrawable(
                        ColorStateList.valueOf(mRippleColor), getDrawable(), null);

                super.setImageDrawable(rippleDrawable);
            }
            else
            {
                RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(mRippleColor), null, null);

                super.setForeground(rippleDrawable);
            }
        }
    }

    @Override
    public void setImageResource(int resId)
    {
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 23)
        {
            RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(mRippleColor), ContextCompat.getDrawable(getContext(),resId), null);
            super.setImageDrawable(rippleDrawable);
        }
        else
        {
            super.setImageResource(resId);
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable)
    {
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 23)
        {
            RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(mRippleColor), drawable, null);
            super.setImageDrawable(rippleDrawable);
        }
        else
        {
            super.setImageDrawable(drawable);
        }
    }


    @Override
    protected void drawableStateChanged()
    {
        if (Build.VERSION.SDK_INT < 21)
        {
            boolean enabled = false;
            boolean pressed = false;
            ColorFilter _pressedFilter = new LightingColorFilter(Color.LTGRAY, 0);

            for (int state : getDrawableState())
            {
                if (state == android.R.attr.state_enabled)
                {
                    enabled = true;
                }
                else if (state == android.R.attr.state_pressed)
                {
                    pressed = true;
                }
            }
            if (enabled && pressed)
            {
                setColorFilter(_pressedFilter);
            }
            else
            {
                setColorFilter(null);
            }
        }

        super.drawableStateChanged();
    }
}

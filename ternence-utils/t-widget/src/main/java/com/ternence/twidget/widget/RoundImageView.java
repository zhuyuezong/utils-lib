package com.ternence.twidget.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.ternence.twidget.R;

/**
 * Project Name: ternence-utils
 * File Name:    RoundImageView.java
 * ClassName:    RoundImageView
 *
 * Description: round image view
 *
 * @author Ternence.Zhu
 */
public class RoundImageView extends ImageView
{
    /**
     * default radius
     */
    private static final int BODER_RADIUS_DEFAULT = 10;
    /**
     * border radius
     */
    protected int mBorderRadius;

    private Paint mBitmapPaint;

    private Matrix mMatrix;

    private BitmapShader mBitmapShader;
    private RectF mRoundRect = new RectF();

    private boolean mIsDrawCover = false;

    private final Paint mCoverPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public RoundImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mBorderRadius = a.getDimensionPixelOffset(R.styleable.RoundImageView_borderRadius, dp2px(BODER_RADIUS_DEFAULT));
        mCoverPaint.setColor(Color.argb(255 / 2, 0, 0, 0));
    }

    public RoundImageView(Context context)
    {
        this(context, null);
    }

    /**
     * init BitmapShader
     */
    private void setUpShader()
    {
        Bitmap bmp = drawableToBitamp(getDrawable());
        if (bmp == null)
        {
            return;
        }
        mBitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if (!(bmp.getWidth() == getWidth() && bmp.getHeight() == getHeight()))
        {
            scale = Math.max(getWidth() * 1.0f / bmp.getWidth(),
                             getHeight() * 1.0f / bmp.getHeight());
        }
        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mBitmapPaint.setShader(mBitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (getDrawable() == null)
        {
            return;
        }
        if (mBorderRadius == 0)
        {
            super.onDraw(canvas);
            return;
        }
        setUpShader();
        canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius, mBitmapPaint);
        if (mIsDrawCover)
        {
            canvas.drawRoundRect(mRoundRect,mBorderRadius,mBorderRadius,mCoverPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mRoundRect.left = dp2px(getPaddingLeft());
        mRoundRect.top = dp2px(getPaddingTop());
        mRoundRect.right = w - dp2px(getPaddingRight());
        mRoundRect.bottom = h - dp2px(getPaddingBottom());
    }

    /**
     * drawable to bitmap
     *
     * @param drawable
     * @return bitmap
     */
    private Bitmap drawableToBitamp(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void drawableStateChanged()
    {
        super.drawableStateChanged();
        if (isClickable())
        {
            for (int state : getDrawableState())
            {
                if (state == android.R.attr.state_focused || state == android.R.attr.state_pressed || state == android.R.attr.state_selected)
                {
                    mIsDrawCover = true;
                    invalidate();
                    return;
                }
            }
            mIsDrawCover = false;
            invalidate();
        }
    }

    public void setBorderRadius(int borderRadius)
    {
        int pxVal = dp2px(borderRadius);
        if (this.mBorderRadius != pxVal)
        {
            this.mBorderRadius = pxVal;
            invalidate();
        }
    }

    public int dp2px(int dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                                               getResources().getDisplayMetrics());
    }
}

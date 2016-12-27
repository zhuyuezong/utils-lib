package com.ternence.tutils;

import android.content.Context;

/**
 * Project Name: ternence-utils
 * File Name:    DensityUtils.java
 * ClassName:    DensityUtils
 *
 * Description: density utils
 *
 * @author Ternence.Zhu
 */
public class DensityUtils
{
    private DensityUtils()
    {
    }

    public static int dip2px(Context context, float dpValue)
    {
        float scale = getDensity(context);
        return (int) (dpValue * scale + 0.5F);
    }

    public static int px2dip(Context context, float pxValue)
    {
        float scale = getDensity(context);
        return (int) (pxValue / scale + 0.5F);
    }

    public static float getDensity(Context context)
    {
        return context.getResources()
                      .getDisplayMetrics().density;
    }

    public static int sp2px(Context context, float spValue)
    {
        float fontScale = context.getResources()
                                 .getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5F);
    }

    public static int getDeviceWidthPixelsAbs(Context context)
    {
        int width = context.getResources()
                           .getDisplayMetrics().widthPixels;
        int height = context.getResources()
                            .getDisplayMetrics().heightPixels;
        return width < height ? width : height;
    }
}

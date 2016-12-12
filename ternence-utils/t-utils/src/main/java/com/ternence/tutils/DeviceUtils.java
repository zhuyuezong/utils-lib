package com.ternence.tutils;

import android.content.Context;

/**
 * Project Name: ternence-utils
 * File Name:    DeviceUtils.java
 * ClassName:    DeviceUtils
 *
 * Description: getWindowsWidth
 *
 * @author Ternence.Zhu
 * 2016-12-12 14:04
 *
 * Copyright (c) 2016年 ternence.zhu
 */
public class DeviceUtils
{
    public static int getDeviceWidthPixelsAbs(Context context) {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        return width < height?width:height;
    }
}

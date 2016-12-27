package com.ternence.tutils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Project Name: ternence-utils
 * File Name:    KeyboardUtils.java
 * ClassName:    KeyboardUtils
 *
 * Description: keyboard util
 *
 * @author Ternence.Zhu
 */
public class KeyboardUtils
{
    private KeyboardUtils()
    {
    }

    public static void showKeyboard(Context context)
    {
        ((InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0, 1);
    }

    public static void showKeyboard(View view, Context context)
    {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 2);
    }

    public static void toggleKeyboard(Context context)
    {
        ((InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE)).toggleSoftInput(1, 0);
    }

    public static void hideKeyboard(Context ctx, View view)
    {
        if (ctx != null && view != null)
        {
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) ctx.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isOpenInput(Context ctx, EditText etx)
    {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        return imm.isActive(etx);
    }
}

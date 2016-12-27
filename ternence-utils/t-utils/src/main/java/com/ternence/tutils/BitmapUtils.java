package com.ternence.tutils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Project Name: ternence-utils
 * File Name:    BitmapUtils.java
 * ClassName:    BitmapUtils
 *
 * Description: bitmap utils
 *
 * @author Ternence.Zhu
 */
public class BitmapUtils
{
    private BitmapUtils()
    {
    }

    public static Bitmap drawable2Bitmap(Drawable drawable)
    {
        if (null == drawable)
        {
            return null;
        }
        else
        {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                                                drawable.getIntrinsicHeight(),
                                                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }

    public static String getBitmapBase64Encode(String path)
    {
        Bitmap bitmap = null;
        String encode = "";

        try
        {
            bitmap = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream e = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, e);
            encode = Base64.encodeToString(e.toByteArray(), 0);
        }
        catch (Exception var7)
        {
            Log.e(BitmapUtils.class.getSimpleName(), var7.getMessage());
        }
        finally
        {
            if (bitmap != null && !bitmap.isRecycled())
            {
                bitmap.recycle();
            }
        }

        return encode;
    }

    public static void recycleBitmap(Bitmap bitmap)
    {
        if (bitmap != null && isAvailableBitmap(bitmap))
        {
            bitmap.recycle();
        }
    }

    public static boolean isAvailableBitmap(Bitmap bitmap)
    {
        return bitmap != null && !bitmap.isRecycled();
    }

    public static Bitmap getBitmapFromUrl(String url)
    {
        Bitmap bitmap = null;

        try
        {
            URL e = new URL(url);
            InputStream i = (InputStream) e.getContent();
            bitmap = BitmapFactory.decodeStream(i);
            i.close();
        }
        catch (Exception var4)
        {
            var4.printStackTrace();
        }

        return bitmap;
    }

    public static boolean saveBitmapToFile(Context context, Bitmap bitmap, String root,
                                           String fileName)
    {
        if (bitmap == null)
        {
            return false;
        }
        else if (fileName == null)
        {
            return false;
        }
        else
        {
            File file = FileUtils.getFile(context, root, fileName);
            String endName = fileName.substring(fileName.lastIndexOf(".") + 1);
            Bitmap.CompressFormat format;
            if ("png".equalsIgnoreCase(endName))
            {
                format = Bitmap.CompressFormat.PNG;
            }
            else
            {
                format = Bitmap.CompressFormat.JPEG;
            }

            return saveBitmapToFile(bitmap, file, format);
        }
    }

    public static boolean saveBitmapToFile(Bitmap bitmap, File file, Bitmap.CompressFormat format)
    {
        if (!isAvailableBitmap(bitmap))
        {
            return false;
        }
        else if (file == null)
        {
            return false;
        }
        else
        {
            if (file.exists())
            {
                file.delete();
            }

            FileOutputStream os = null;

            boolean var5;
            try
            {
                os = new FileOutputStream(file);
                Bitmap.CompressFormat e = format == null ? Bitmap.CompressFormat.JPEG : format;
                bitmap.compress(e, 50, os);
                os.flush();
                return true;
            }
            catch (Exception var15)
            {
                var5 = false;
            }
            finally
            {
                if (os != null)
                {
                    try
                    {
                        os.close();
                    }
                    catch (IOException var14)
                    {
                        var14.printStackTrace();
                    }
                }

            }

            return var5;
        }
    }

    @SuppressLint({"NewApi"})
    public static byte[] bitmap2Data(Bitmap bitmap)
    {
        if (isAvailableBitmap(bitmap))
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }
        else
        {
            return null;
        }
    }

    public static Bitmap getFitBitmap(Bitmap bitmap, long limtSize)
    {
        if (bitmap2Data(bitmap) != null)
        {
            long curSize = (long) bitmap2Data(bitmap).length;
            Matrix matrix = new Matrix();
            if (Math.ceil((double) (curSize / limtSize)) >= 2.0D)
            {
                if (Math.ceil((double) (curSize / limtSize)) >= 4.0D)
                {
                    matrix.postScale(0.25F, 0.25F);
                }
                else
                {
                    matrix.postScale(0.5F, 0.5F);
                }
            }
            else
            {
                matrix.postScale(0.9F, 0.9F);
            }

            if (curSize > limtSize)
            {
                Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                                       bitmap.getHeight(), matrix, true);
                recycleBitmap(bitmap);
                return getFitBitmap(newBitmap, limtSize);
            }
            else
            {
                return bitmap;
            }
        }
        else
        {
            return bitmap;
        }
    }
}

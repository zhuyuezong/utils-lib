package com.ternence.tutils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * Project Name: ternence-utils
 * File Name:    FileUtils.java
 * ClassName:    FileUtils
 *
 * Description: file utils
 *
 * @author Ternence.Zhu
 */
public class FileUtils
{

    public static boolean renameTo(String srcPath, String destPath)
    {
        if (!TextUtils.isEmpty(srcPath) && !TextUtils.isEmpty(destPath))
        {
            File src = new File(srcPath);
            if (src.exists())
            {
                File dest = new File(destPath);
                File parent = dest.getParentFile();
                if (parent.exists() && parent.canWrite())
                {
                    return src.renameTo(dest);
                }
            }
        }

        return false;
    }

    public static File getFile(Context context, String root, String fileName)
    {
        File dirFile = getDir(context, root);
        if (dirFile != null && dirFile.exists())
        {
            return new File(dirFile, fileName);
        }
        return null;
    }

    public static File getDir(Context context, String root)
    {
        if (root == null)
        {
            root = "";
        }

        File file = null;
        StringBuilder path;
        boolean result;
        if (SDCardUtils.checkSDCard())
        {
            path = new StringBuilder(Environment.getExternalStorageDirectory()
                                                .getAbsolutePath());
            if (!TextUtils.isEmpty(root))
            {
                path.append(File.separatorChar);
                path.append(root);
            }
            file = new File(path.toString());
            if (!file.exists())
            {
                result = file.mkdirs();
                if (result)
                {
                    Log.d(FileUtils.class.getSimpleName(), "创建目录成功");
                }
                else
                {
                    Log.d(FileUtils.class.getSimpleName(), "创建目录失败");
                }
            }
        }
        else if (!TextUtils.isEmpty(context.getCacheDir()
                                           .getAbsolutePath()))
        {
            path = new StringBuilder(context.getCacheDir()
                                            .getAbsolutePath());
            if (!TextUtils.isEmpty(root))
            {
                path.append(File.separatorChar);
                path.append(root);
            }
            file = new File(path.toString());
            if (!file.exists())
            {
                result = file.mkdirs();
                if (result)
                {
                    Log.d(FileUtils.class.getSimpleName(), "创建目录成功");
                }
                else
                {
                    Log.d(FileUtils.class.getSimpleName(), "创建目录失败");
                }
            }
        }

        return file;
    }
}

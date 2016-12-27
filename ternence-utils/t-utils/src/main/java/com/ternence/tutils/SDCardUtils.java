package com.ternence.tutils;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Project Name: ternence-utils
 * File Name:    SDCardUtils.java
 * ClassName:    SDCardUtils
 *
 * Description: sd utils
 *
 * @author Ternence.Zhu
 */
public class SDCardUtils
{
    private static final String TAG = "SDCardUtils";
    private static final String APP_SD_M4399_DIR_BROKEN = "app_sd_m4399_dir_broken";
    private static boolean sIsSendedUmeng = false;
    private static String mAppCachePath;
    private static String[] mSdcardPath = checkSDCardPath();
    private static int mSdcardType;
    private static final int APPPATH_MKDIR_MAX_COUNT = 100;

    public SDCardUtils()
    {
    }

    public static boolean checkSDCard()
    {
        return getSDCardAvailable() > 0L;
    }

    public static String getSDCardPath()
    {
        return mSdcardPath[1];
    }


    public static String[] checkSDCardPath()
    {
        String externalSdcardPath = getExternalSdcardPath();
        File sdcardDir = null;
        if (TextUtils.isEmpty(externalSdcardPath))
        {
            sdcardDir = Environment.getExternalStorageDirectory();
            mSdcardType = 1;
        }
        else
        {
            File path = new File(externalSdcardPath);
            boolean mountSDPathValid = path.exists() && path.isDirectory() && path.canRead() && path.canWrite();
            if (mountSDPathValid)
            {
                mountSDPathValid = checkSDCardPathAllowWrite(externalSdcardPath);
            }

            boolean isHasExternalSD = false;
            if (mountSDPathValid)
            {
                StatFs sf = new StatFs(externalSdcardPath);
                isHasExternalSD = sf.getAvailableBlocks() > 1024;
            }

            if (isHasExternalSD)
            {
                sdcardDir = new File(externalSdcardPath);
                mSdcardType = 2;
            }
            else
            {
                sdcardDir = Environment.getExternalStorageDirectory();
                mSdcardType = 1;
            }
        }

        return new String[]{sdcardDir.getPath(), sdcardDir.getAbsolutePath()};
    }

    private static boolean checkSDCardPathAllowWrite(String path)
    {
        boolean result = false;
        RandomAccessFile buffer = null;
        try
        {
            File e = new File(path + "/test.txt");
            if (!e.exists())
            {
                buffer = new RandomAccessFile(e, "rw");
                buffer.writeChars("ok");
                buffer.close();
                result = true;
            }
        }
        catch (Exception var4)
        {
            result = false;
            var4.printStackTrace();
        }

        return result;
    }

    public static long getSDCardAvailable()
    {
        long size = 0L;

        StatFs sf;
        long bSize;
        long availBlocks;
        try
        {
            sf = new StatFs(mSdcardPath[0]);
            bSize = (long) sf.getBlockSize();
            availBlocks = (long) sf.getAvailableBlocks();
            size = bSize * availBlocks;
        }
        catch (IllegalArgumentException var8)
        {
            var8.printStackTrace();
        }

        if (mSdcardType == 2 && size == 0L)
        {
            File sdcardDir = Environment.getExternalStorageDirectory();
            sf = new StatFs(sdcardDir.getPath());
            bSize = (long) sf.getBlockSize();
            availBlocks = (long) sf.getAvailableBlocks();
            size = bSize * availBlocks;
        }

        return size;
    }

    private static String getExternalSdcardPath()
    {
        String sdcardPath = "";

        try
        {
            File e = new File("/system/etc/vold.fstab");
            if (e.exists())
            {
                Scanner scanner = new Scanner(e);
                ArrayList<String> sdcardPaths = new ArrayList();

                while (scanner.hasNext())
                {
                    String line = scanner.nextLine();
                    if (line.startsWith("dev_mount"))
                    {
                        String[] lineElements = line.split(" ");
                        String element = lineElements[2];
                        if (lineElements[1].contains("sdcard"))
                        {
                            sdcardPaths.add(element);
                        }
                    }
                }

                if (sdcardPaths.size() == 2)
                {
                    sdcardPath = sdcardPaths.get(1);
                }
                else if (sdcardPaths.size() == 1)
                {
                    sdcardPath = sdcardPaths.get(0);
                }

                if (sdcardPath.equals(Environment.getExternalStorageDirectory()
                                                 .getPath()))
                {
                    sdcardPath = "";
                }
            }

            if (TextUtils.isEmpty(sdcardPath))
            {
                sdcardPath = getExternalSdcardPathSomeMachine();
            }
        }
        catch (Exception var7)
        {
            var7.printStackTrace();
        }

        return sdcardPath;
    }

    private static String getExternalSdcardPathSomeMachine()
    {
        String externalSdcardPath = "";
        Map map = System.getenv();
        Set set = map.keySet();
        Iterator keys = set.iterator();

        while (keys.hasNext())
        {
            String key = (String) keys.next();
            String value = (String) map.get(key);
            if ("SECONDARY_STORAGE".equals(key))
            {
                externalSdcardPath = value;
            }
        }

        return externalSdcardPath;
    }
}

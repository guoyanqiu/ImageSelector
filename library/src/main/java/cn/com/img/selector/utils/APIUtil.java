package cn.com.img.selector.utils;

import android.os.Build;

/**
 * Created by mac on 18/3/21.
 */

public class APIUtil {

    public static boolean isSupport(int apiNo) {
        return Build.VERSION.SDK_INT >= apiNo;
    }
}

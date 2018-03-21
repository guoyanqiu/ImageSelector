package cn.com.img.selector.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by mac on 18/3/21.
 */

public class UIUtil {
    public static int dip2px(@Nullable Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getScreenHeight(@Nullable Context context) {
        Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(@Nullable Context context) {
        Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getWidth();
    }
}

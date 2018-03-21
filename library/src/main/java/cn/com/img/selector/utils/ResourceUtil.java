package cn.com.img.selector.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by mac on 18/3/21.
 */

public class ResourceUtil {
    public static int getDrawableId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "drawable", paramContext.getPackageName());
    }

    public static Drawable getDrawable(Context mContext, String imgName) {
        Drawable drawable = mContext
                .getResources().getDrawable(
                        ResourceUtil.getDrawableOrmipmapId(mContext, imgName));
        return drawable;

    }

    public static int getDrawableOrmipmapId(Context paramContext,
                                            String paramString) {
        int status = getDrawableId(paramContext, paramString);
        if (status != 0)
            return status;
        else
            return getMipmapId(paramContext, paramString);
    }

    public static int getMipmapId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "mipmap",
                paramContext.getPackageName());
    }
}

package cn.com.img.selector.interf;

import android.database.Cursor;

/**
 * Created by mac on 18/2/24.
 */

public interface IImageMediaCallback {
    void onImageLoad(Cursor cursor);

    void onImageReset();
}

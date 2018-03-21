package cn.com.img.selector.interf;

import android.support.annotation.Nullable;

/**
 * Created by mac on 18/3/21.
 */

public interface IWidgetClickListener<Info> {
    void onClick(@Nullable Info info);
}

package cn.com.img.selector.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import cn.com.img.selector.interf.IImageView;

/**
 * Created by mac on 18/3/1.
 */

public class DefaultImageView extends ImageView implements IImageView {
    public DefaultImageView(Context context) {
        super(context);
    }

    public View getImageView() {
        return this;
    }
}

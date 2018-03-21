package cn.com.img.selector.factory;

import android.content.Context;

import java.lang.reflect.Constructor;

import cn.com.img.selector.interf.IImageView;
import cn.com.img.selector.provider.RegisterLibsManager;
import cn.com.img.selector.view.DefaultImageView;

/**
 * Created by mac on 18/3/21.
 */

public class ImageViewFactory {
    public static IImageView createImage(Context context) {
        Class<? extends IImageView> clas = RegisterLibsManager.getImageViewLib();
        if (clas != null) {
            Constructor constructor = null;
            try {
                constructor = clas.getDeclaredConstructor(Context.class);
                return (IImageView) constructor.newInstance(context);
            } catch (Exception e) {
                return new DefaultImageView(context);
            }

        }

        return new DefaultImageView(context);
    }
}

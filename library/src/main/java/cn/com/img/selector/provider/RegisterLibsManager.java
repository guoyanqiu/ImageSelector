package cn.com.img.selector.provider;

import cn.com.img.selector.interf.IImageLoader;
import cn.com.img.selector.interf.IImageView;

/**
 * Created by mac on 18/3/21.
 */

public class RegisterLibsManager {
    private static Class<? extends IImageLoader> sImageLoaderClass;
    private static Class<? extends IImageView> sImageViewClass;

    public static Class<? extends IImageLoader> getImageLoaderLib() {
        return sImageLoaderClass;
    }

    public static void registerImageViewLib(Class<? extends IImageView> imageViewLib) {
        if (sImageViewClass == null) {
            sImageViewClass = imageViewLib;
        }
    }

    public static Class<? extends IImageView> getImageViewLib() {
        return sImageViewClass;
    }


    public static void registerImageLoaderLib(Class<? extends IImageLoader> imageLoaderLib) {
        if (sImageLoaderClass == null) {
            sImageLoaderClass = imageLoaderLib;
        }
    }

}

package cn.com.img.selector.factory;

import java.lang.ref.WeakReference;

import cn.com.img.selector.interf.IImageLoader;
import cn.com.img.selector.provider.RegisterLibsManager;

/**
 * Created by mac on 18/3/21.
 */

public class ImageLoaderFactory {
    private static WeakReference<IImageLoader> sImageLoaderReference;

    public static IImageLoader getImageLoader() {
        try {
            IImageLoader imageLoader = null;
            if (sImageLoaderReference == null) {
                imageLoader = RegisterLibsManager.getImageLoaderLib().newInstance();
                sImageLoaderReference = new WeakReference<>(imageLoader);
            } else {
                imageLoader = sImageLoaderReference.get();
                if (imageLoader == null) {
                    if (RegisterLibsManager.getImageLoaderLib() != null) {
                        imageLoader = RegisterLibsManager.getImageLoaderLib().newInstance();
                        sImageLoaderReference = new WeakReference<>(imageLoader);
                    }
                }
            }
            return imageLoader;
        } catch (Exception e) {
        }
        return null;
    }


}

package cn.com.img.selector.interf;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import cn.com.img.selector.bean.ImageOptions;

/**
 * Created by yanjiangbo on 2017/5/2.
 */

public interface IImageLoader {
    void loadImage(WeakReference<? extends IImageView> imageView, ImageOptions imageOptions, @Nullable IImageLoaderCallback result);
}

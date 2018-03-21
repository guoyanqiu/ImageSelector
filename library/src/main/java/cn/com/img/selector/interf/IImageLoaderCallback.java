package cn.com.img.selector.interf;


import java.lang.ref.WeakReference;

/**
 * Created by yanjiangbo on 2017/6/6.
 */

public interface IImageLoaderCallback {

    void loadSuccess(WeakReference<? extends IImageView> imageView, String url);

    void loadFailure(WeakReference<? extends IImageView> imageView, String url, Exception e);
}

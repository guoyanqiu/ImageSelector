package cn.com.img.selector.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import cn.com.img.selector.bean.ImageOptions;
import cn.com.img.selector.factory.ImageLoaderFactory;
import cn.com.img.selector.factory.ImageViewFactory;
import cn.com.img.selector.interf.IImageLoader;
import cn.com.img.selector.interf.IImageLoaderCallback;
import cn.com.img.selector.interf.IImageView;

/**
 * Created by mac on 18/3/21.
 */

public class ImageContentView extends FrameLayout implements IImageView {

    protected IImageLoader mImageLoader;
    private IImageView mImageView;

    public ImageContentView(Context context) {
        super(context);
        initImageLoader();
        initImageView();
    }


    private void initImageLoader() {
        mImageLoader = ImageLoaderFactory.getImageLoader();
    }

    private void initImageView() {
        mImageView = ImageViewFactory.createImage(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImageView.setLayoutParams(params);
        if (mImageView instanceof View) {
            addView((View) mImageView);
        }
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        mImageView.setImageDrawable(drawable);
    }

    @Override
    public void setImageURI(Uri uri) {
        mImageView.setImageURI(uri);
    }

    public void setScaleType(ImageView.ScaleType type) {
        mImageView.setScaleType(type);
    }

    @Override
    public ImageView.ScaleType getScaleType() {
        return mImageView.getScaleType();
    }

    @Override
    public View getImageView() {
        return mImageView.getImageView();
    }

    @Override
    public void setImageResource(int resourceId) {
        mImageView.setImageResource(resourceId);
    }


    public void loadImage(@NonNull String url) {
        ImageOptions venvyImageInfo =
                new ImageOptions.Builder().setUrl(url).build();
        loadImage(venvyImageInfo);
    }

    public void loadImage(@NonNull String url, @Nullable IImageLoaderCallback result) {
        ImageOptions imageOptions =
                new ImageOptions.Builder().setUrl(url).build();
        loadImage(imageOptions, result);
    }

    public void loadImage(@NonNull ImageOptions imageOptions) {
        loadImage(imageOptions, null);
    }

    public void loadImage(@NonNull ImageOptions imageOptions, IImageLoaderCallback result) {
        if (mImageLoader != null) {
            mImageLoader.loadImage(new WeakReference<>(mImageView), imageOptions, result);
        }
    }
}

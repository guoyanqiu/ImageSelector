package cn.com.img.selector.glide;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SquaringDrawable;
import com.bumptech.glide.request.target.Target;

import java.lang.ref.WeakReference;

import cn.com.img.selector.bean.ImageOptions;
import cn.com.img.selector.interf.IImageLoader;
import cn.com.img.selector.interf.IImageLoaderCallback;
import cn.com.img.selector.interf.IImageView;


public class GlideImageLoader implements IImageLoader {

    @Override
    public void loadImage(WeakReference<? extends IImageView> imageViewReference, final ImageOptions
            imageOptions, IImageLoaderCallback result) {
        IImageView iImageView = imageViewReference.get();
        if (iImageView == null) {
            return;
        }

        Context context = iImageView.getContext();
        if (context == null) {
            return;
        }
        if (iImageView.getContext() instanceof Activity) {
            Activity activity = (Activity) iImageView.getContext();
            if (activity.isFinishing()) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 17) {
                if (activity.isDestroyed()) {
                    return;
                }
            }
        } else {
            context = context.getApplicationContext();
        }
        if (!(iImageView.getImageView() instanceof ImageView)) {
            return;
        }
        ImageView imageView = (ImageView) iImageView.getImageView();
        CustomImageTarget customImageTarget = new CustomImageTarget(imageView);
        final String url = imageOptions.getUrl();
        DrawableRequestBuilder drawableRequestBuilder;
        drawableRequestBuilder = Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate().listener(new ImageRequestListener(imageViewReference, imageOptions, result, customImageTarget));


        if (imageOptions.getPlaceHolderImage() != null) {
            drawableRequestBuilder.placeholder(imageOptions.getPlaceHolderImage());
        }

        if (imageOptions.getFailedImage() != null) {
            drawableRequestBuilder.error(imageOptions.getFailedImage());
        }

        if (imageOptions.getResizeWidth() > 0 && imageOptions.getResizeHeight() > 0) {
            drawableRequestBuilder.override(imageOptions.getResizeWidth(),
                    imageOptions.getResizeHeight());
        }

        drawableRequestBuilder.into(customImageTarget);
    }




    private class ImageRequestListener implements RequestListener<String, GlideDrawable> {
        WeakReference<? extends IImageView> mImageViewRef;
        ImageOptions mImageOptions;
        String mUrl = null;
        IImageLoaderCallback mResult;
        CustomImageTarget mCustomImageTarget;

        ImageRequestListener(WeakReference<? extends IImageView> imageRef, ImageOptions imageOptions,
                             IImageLoaderCallback result, CustomImageTarget customImageTarget) {
            mImageViewRef = imageRef;
            mImageOptions = imageOptions;
            mUrl = mImageOptions.getUrl();
            mResult = result;
            this.mCustomImageTarget = customImageTarget;
        }

        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                   boolean isFirstResource) {
            if (mResult != null) {
                mResult.loadFailure(mImageViewRef, mUrl, e);
            }
            return false;
        }

        /***
         * 该函数在transForm方法之后调用
         */
        @Override
        public boolean onResourceReady(GlideDrawable glideDrawable, String model,
                                       Target<GlideDrawable> target,
                                       boolean isFromMemoryCache, boolean isFirstResource) {
            if (mResult != null) {
                mResult.loadSuccess(mImageViewRef, mUrl);
            }
            if (glideDrawable instanceof GifDrawable) {
                GifDrawable gifDrawable = (GifDrawable) glideDrawable;
                int count = gifDrawable.getDecoder().getLoopCount();
                //此处是为了兼容Glide低于3.8.0 版本的gif 循环次数Bug
                if (count > 1) {
                    count += 1;
                } else if (count == 0) {
                    count = Integer.MAX_VALUE;
                }
                if (mCustomImageTarget != null) {
                    mCustomImageTarget.setMaxLoopCount(count);
                }
            }
            mResult = null;
            return false;
        }

    }



    private class CustomImageTarget extends ImageViewTarget<GlideDrawable> {
        private int maxLoopCount;
        private GlideDrawable resource;

        CustomImageTarget(ImageView view) {
            this(view, -1);
        }

        CustomImageTarget(ImageView view, int maxLoopCount) {
            super(view);
            this.maxLoopCount = maxLoopCount;
        }

        void setMaxLoopCount(int count) {
            if (count > 0) {
                maxLoopCount = count;
            }
        }

        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
            if (!resource.isAnimated()) {
                float viewRatio = (float) this.view.getWidth() / (float) this.view.getHeight();
                float drawableRatio = (float) resource.getIntrinsicWidth() / (float) resource.getIntrinsicHeight();
                if (Math.abs(viewRatio - 1.0F) <= 0.05F && Math.abs(drawableRatio - 1.0F) <= 0.05F) {
                    resource = new SquaringDrawable(resource, this.view.getWidth());
                }
            }

            super.onResourceReady(resource, animation);
            this.resource = resource;
            resource.setLoopCount(this.maxLoopCount);
            resource.start();
        }

        protected void setResource(GlideDrawable resource) {
            this.view.setImageDrawable(resource);
        }

        public void onStart() {
            if (this.resource != null) {
                this.resource.start();
            }

        }

        public void onStop() {
            if (this.resource != null) {
                this.resource.stop();
            }

        }
    }
}

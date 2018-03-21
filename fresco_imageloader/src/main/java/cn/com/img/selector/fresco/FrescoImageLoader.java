package cn.com.img.selector.fresco;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.lang.ref.WeakReference;

import cn.com.img.selector.bean.ImageOptions;
import cn.com.img.selector.interf.IImageLoader;
import cn.com.img.selector.interf.IImageLoaderCallback;
import cn.com.img.selector.interf.IImageView;


/**
 * Created by yanjiangbo on 2017/5/2.
 */

public class FrescoImageLoader implements IImageLoader {


    @Override
    public void loadImage(final WeakReference<? extends IImageView> imageViewReference, ImageOptions imageOptions, final  IImageLoaderCallback result) {
        IImageView iImageView = imageViewReference.get();
        if (iImageView == null) {
            return;
        }
        if (!Fresco.hasBeenInitialized()) {
            Fresco.initialize(iImageView.getContext());
        }
        if (imageOptions == null) {
            return;
        }
        final String url = imageOptions.getUrl();
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!(iImageView.getImageView() instanceof SimpleDraweeView)) {
            return;
        }

        SimpleDraweeView imageView = (SimpleDraweeView) iImageView.getImageView();

        ImageRequest imageRequest = buildImageRequest(imageView, imageOptions);
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setControllerListener(new BaseControllerListener<ImageInfo>() {
                            @Override
                            public void onFailure(String id, Throwable throwable) {
                                super.onFailure(id, throwable);

                                Exception exception = throwable != null ? new Exception
                                        (throwable) : new Exception();
                                if (result != null) {
                                    result.loadFailure(imageViewReference, url, exception);
                                }

                            }

                            @Override
                            public void onFinalImageSet(String id, ImageInfo imageInfo,
                                                        Animatable animatable) {
                                super.onFinalImageSet(id, imageInfo, animatable);
                                if (result == null) {
                                    return;
                                }
                                if (imageInfo != null) {//需要获取图片的大小
                                    result.loadSuccess(imageViewReference, url);
                                }
                            }
                        })
                        .setImageRequest(imageRequest)
                        .setOldController(imageView.getController())
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        imageView.setController(draweeController);
    }


    private ImageRequest buildImageRequest( SimpleDraweeView imageView, final ImageOptions
            imageOptions) {
        ImageRequestBuilder builder;
        builder = ImageRequestBuilder.newBuilderWithSource(Uri.parse
                ("file://" + imageOptions.getUrl()));

        if (imageView != null) {
            GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
            if (hierarchy == null) {
                GenericDraweeHierarchyBuilder hierarchyBuilder =
                        new GenericDraweeHierarchyBuilder(imageView.getContext().getResources());
                hierarchy = hierarchyBuilder.build();
                imageView.setHierarchy(hierarchy);
            }
            hierarchy = imageView.getHierarchy();
            if (hierarchy != null) {
                hierarchy.setFadeDuration(300);
                hierarchy.setPlaceholderImage(imageOptions.getPlaceHolderImage());
                hierarchy.setFailureImage(imageOptions.getFailedImage());
                hierarchy.setBackgroundImage(imageOptions.getBackgroundImage());
                hierarchy.setRetryImage(imageOptions.getRetryImage());

            }

            if (imageOptions.getResizeHeight() > 0 && imageOptions.getResizeWidth() > 0) {
                builder.setResizeOptions(new ResizeOptions(imageOptions.getResizeWidth(),
                        imageOptions.getResizeHeight()));
            }
        }
        return builder.build();
    }

}

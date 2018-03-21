package cn.com.img.selector.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by mac on 18/3/21.
 */

public class ImageOptions {
    private final String url;  //加载url
    private final Drawable placeHolderImage;  //加载图
    private final Drawable failedImage;     // 失败图
    private final Drawable backgroundImage;  // 背景图
    private final Drawable retryImage;  // 重试图
    private final int resizeWidth;
    private final int resizeHeight;
    private Builder builder;


    private ImageOptions(Builder builder) {
        url = builder.url;
        placeHolderImage = builder.placeHolderImage;
        failedImage = builder.failedImage;
        backgroundImage = builder.backgroundImage;
        retryImage = builder.retryImage;
        resizeWidth = builder.resizeWidth;
        resizeHeight = builder.resizeHeight;
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

    public String getUrl() {
        return url;
    }


    public Drawable getPlaceHolderImage() {
        return placeHolderImage;
    }

    public Drawable getFailedImage() {
        return failedImage;
    }

    public Drawable getBackgroundImage() {
        return backgroundImage;
    }

    public Drawable getRetryImage() {
        return retryImage;
    }

    public int getResizeWidth() {
        return resizeWidth;
    }

    public int getResizeHeight() {
        return resizeHeight;
    }

    public static final class Builder {
        private String url;
        private Drawable placeHolderImage;
        private Drawable failedImage;
        private Drawable backgroundImage;
        private Drawable retryImage;
        private int resizeWidth;
        private int resizeHeight;

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setPlaceHolderImage(Drawable placeHolderImage) {
            this.placeHolderImage = placeHolderImage;
            return this;
        }

        public Builder setFailedImage(Drawable failedImage) {
            this.failedImage = failedImage;
            return this;
        }

        public Builder setBackgroundImage(Drawable backgroundImage) {
            this.backgroundImage = backgroundImage;
            return this;
        }

        public Builder setRetryImage(Drawable retryImage) {
            this.retryImage = retryImage;
            return this;
        }

        public Builder setResizeHeight(int resizeHeight) {
            this.resizeHeight = resizeHeight;
            return this;
        }

        public Builder setResizeWidth(int resizeWidth) {
            this.resizeWidth = resizeWidth;
            return this;
        }

        public ImageOptions build() {
            return new ImageOptions(this);
        }
    }
}

package cn.com.img.selector.interf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import static android.widget.ImageView.ScaleType;

/**
 * Created by mac on 18/2/28.
 */

public interface IImageView {
    void setImageBitmap(Bitmap bitmap);

    void setImageDrawable(Drawable drawable);

    void setImageURI(Uri uri);

    void setScaleType(ScaleType scaleType);

    void setLayoutParams(ViewGroup.LayoutParams params);

    Context getContext();

    ScaleType getScaleType();

    View getImageView();

    void setImageResource(int resourceId);
}

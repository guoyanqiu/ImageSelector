package cn.com.img.selector.bean;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import cn.com.img.selector.loader.ImageFolderSelector;

/**
 * 某路径下图片的数量，第一个图片的路径、和 图片所在的路径
 * Created by mac on 17/12/12.
 */

public class ImageFolder implements Parcelable {
    public static final Creator<ImageFolder> CREATOR = new Creator<ImageFolder>() {
        @Nullable
        @Override
        public ImageFolder createFromParcel(Parcel source) {
            return new ImageFolder(source);
        }

        @Override
        public ImageFolder[] newArray(int size) {
            return new ImageFolder[size];
        }
    };


    ImageFolder(Parcel source) {
        floderId = source.readString();
        firstImagePath = source.readString();
        floderName = source.readString();
        count = source.readInt();
    }

    public static ImageFolder valueOf(Cursor cursor) {
        return new ImageFolder(
                cursor.getString(cursor.getColumnIndex("bucket_id")),
                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),
                cursor.getString(cursor.getColumnIndex("bucket_display_name")),
                cursor.getInt(cursor.getColumnIndex(ImageFolderSelector.COLUMN_COUNT)));
    }


    ImageFolder(String id, String coverPath, String albumName, int count) {
        floderId = id;
        firstImagePath = coverPath;
        floderName = albumName;
        this.count = count;
    }

    /**
     * 某文件夹下第一张图片的路径
     */
    public String firstImagePath;

    /**
     * 文件夹的名称
     */
    public String floderName;

    public String floderId;

    /**
     * 图片的数量
     */
    public int count;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(floderId);
        dest.writeString(firstImagePath);
        dest.writeString(floderName);
        dest.writeInt(count);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof ImageFolder)) {
            return false;
        }
        ImageFolder imageFolder = (ImageFolder) o;
        return TextUtils.equals(imageFolder.floderId, floderId);
    }
}

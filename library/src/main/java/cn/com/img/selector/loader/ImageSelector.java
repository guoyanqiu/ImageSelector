package cn.com.img.selector.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;
import android.provider.MediaStore;

import cn.com.img.selector.bean.ImageFolder;


/**
 * Created by mac on 18/2/24.
 */

public class ImageSelector extends CursorLoader {
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA};

    // ===============================================================

    private static final String SELECTION_ALBUM_FOR_SINGLE_MEDIA_TYPE =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND "
                    + " bucket_id=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0";

    private static String[] getSelectionAlbumArgsForSingleMediaType(int mediaType, String folderId) {
        return new String[]{String.valueOf(mediaType), folderId};
    }
    // ===============================================================

    private static final String ORDER_BY = MediaStore.Images.Media.DATE_TAKEN + " DESC";

    private ImageSelector(Context context, String selection, String[] selectionArgs) {
        super(context, QUERY_URI, PROJECTION, selection, selectionArgs, ORDER_BY);
    }

    public static CursorLoader newInstance(Context context, ImageFolder imageFolder) {
        String[] selectionArgs = getSelectionAlbumArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE,
                imageFolder.floderId);
        return new ImageSelector(context, SELECTION_ALBUM_FOR_SINGLE_MEDIA_TYPE, selectionArgs);
    }


    @Override
    public void onContentChanged() {
        //
    }
}
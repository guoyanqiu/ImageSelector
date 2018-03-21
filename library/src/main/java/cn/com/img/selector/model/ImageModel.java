package cn.com.img.selector.model;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import cn.com.img.selector.bean.ImageFolder;
import cn.com.img.selector.interf.IImageMediaCallback;
import cn.com.img.selector.loader.ImageSelector;

/**
 * Created by mac on 18/2/24.
 */

public class ImageModel implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 2;
    private static final String ARGS_ALBUM = "args_album";
    private WeakReference<Context> mContext;
    private LoaderManager mLoaderManager;
    private IImageMediaCallback mCallbacks;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }
        ImageFolder imageFolder = args.getParcelable(ARGS_ALBUM);
        if (imageFolder == null) {
            return null;
        }

        return ImageSelector.newInstance(context, imageFolder);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }

        mCallbacks.onImageLoad(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }

        mCallbacks.onImageReset();
    }

    public void onCreate(@NonNull Context context, @NonNull IImageMediaCallback callbacks) {
        mContext = new WeakReference<Context>(context);
        mLoaderManager = ((Activity) context).getLoaderManager();
        mCallbacks = callbacks;
    }

    public void onDestroy() {
        mLoaderManager.destroyLoader(LOADER_ID);
        mLoaderManager = null;
        mCallbacks = null;
    }


    public void load(@NonNull ImageFolder target) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_ALBUM, target);
        mLoaderManager.initLoader(LOADER_ID, args, this);
    }
}

package cn.com.img.selector.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.img.selector.bean.ImageFolder;
import cn.com.img.selector.bean.ImageOptions;
import cn.com.img.selector.interf.IWidgetClickListener;
import cn.com.img.selector.utils.UIUtil;
import cn.com.img.selector.view.ImageContentView;
import cn.com.img.selector.view.ImageScannerListItemView;

/**
 * Created by mac on 17/12/13.
 */
public class ImageFloderAdapter extends
        RecyclerViewCursorAdapter<RecyclerView.ViewHolder> {
    public IWidgetClickListener<ImageFolder> mOnItemClickListener;
    private int height;

    public ImageFloderAdapter(Context context) {
        super(null);
        height = UIUtil.dip2px(context, 80);
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        ListViewHolder listViewHolder = (ListViewHolder) holder;
        final ImageFolder imageFolder = ImageFolder.valueOf(cursor);
        ViewGroup viewGroup = listViewHolder.mItemView;
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(imageFolder);
                }
            }
        });
        listViewHolder.mNameView.setText(imageFolder.floderName);
        listViewHolder.mCountView.setText("" + imageFolder.count);
        ImageOptions imageOptions =
                new ImageOptions.Builder()
                        .setResizeWidth(70)
                        .setResizeHeight(70)
                        .setUrl(imageFolder.firstImagePath).build();
        listViewHolder.mImageView.loadImage(imageOptions);
    }

    @Override
    protected int getItemViewType(int position, Cursor cursor) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageScannerListItemView imageScannerListItemView = new ImageScannerListItemView(parent.getContext(), height);
        return new ListViewHolder(imageScannerListItemView);
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {

        final ImageScannerListItemView mItemView;
        final ImageContentView mImageView;
        final TextView mNameView;
        final TextView mCountView;

        ListViewHolder(ImageScannerListItemView itemView) {
            super(itemView);
            mItemView = (ImageScannerListItemView) itemView;
            mImageView = mItemView.mImageView;
            mNameView = mItemView.mNameView;
            mCountView = mItemView.mCountView;
        }
    }
}

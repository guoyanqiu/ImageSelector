package cn.com.img.selector.view;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.img.selector.adapter.ImageFloderAdapter;
import cn.com.img.selector.adapter.ImageItemAdapter;
import cn.com.img.selector.bean.ImageFolder;
import cn.com.img.selector.interf.IImageMediaCallback;
import cn.com.img.selector.interf.IWidgetClickListener;
import cn.com.img.selector.model.ImageFolderModel;
import cn.com.img.selector.model.ImageModel;
import cn.com.img.selector.utils.ResourceUtil;
import cn.com.img.selector.utils.UIUtil;


/**
 * Created by mac on 17/12/12.
 */

public class ImageScannerDialogLayout extends FrameLayout implements IImageMediaCallback {


    private FrameLayout mTopLayout;
    private RecyclerView mListView;

    private TextView mCancelView;
    private int mWidth;
    private int mHeight;
    private int mTopLayoutHeight;
    private Context mContext;
    TextView mTitleView;
    public IWidgetClickListener<String> mCropImageResultListener;
    public IWidgetClickListener mDismissDialogListener;

    private RecyclerView mGridView;
    private ImageItemAdapter imageItemAdapter;
    private ImageModel imageModel;

    private ImageFloderAdapter imageFloderAdapter;
    private ImageFolderModel imageFolderModel;

    public ImageScannerDialogLayout(@NonNull Context context) {
        super(context);

        mWidth = UIUtil.getScreenWidth(context);
        mTopLayoutHeight = UIUtil.dip2px(context, 44);
        mHeight = UIUtil.getScreenHeight(context);
        setBackgroundColor(Color.BLACK);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        mContext = context;
        initTopView();

        initListView();
    }


    private void initTopView() {
        mTopLayout = new FrameLayout(mContext);
        LayoutParams params = new LayoutParams(mWidth, mTopLayoutHeight);
        params.gravity = Gravity.TOP;
        mTopLayout.setLayoutParams(params);
        mTopLayout.setBackgroundColor(Color.parseColor("#2896F0"));

        mTopLayout.addView(createBackView());
        mTopLayout.addView(createTitleView());

        initCancelView();
        mTopLayout.addView(mCancelView);
        addView(mTopLayout);
    }

    public void initCancelView() {
        mCancelView = new TextView(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        params.rightMargin = UIUtil.dip2px(mContext, 16);
        mCancelView.setLayoutParams(params);
        mCancelView.setTextColor(Color.WHITE);
        mCancelView.setGravity(Gravity.CENTER);
        mCancelView.setText("取消");
        mCancelView.setTextSize(18);
        mCancelView.setVisibility(GONE);
        mCancelView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCancelView.setVisibility(GONE);
                if (mGridView != null) {
                    removeView(mGridView);
                    imageModel.onDestroy();
                    imageModel = null;
                    imageItemAdapter = null;
                    mGridView = null;
                }
                mTitleView.setText("选择相册");
                mListView.setVisibility(VISIBLE);
            }
        });
    }

    private View createTitleView() {
        mTitleView = new TextView(mContext);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        mTitleView.setLayoutParams(params);
        mTitleView.setTextColor(Color.WHITE);
        mTitleView.setGravity(Gravity.CENTER);
        mTitleView.setText("选择相册");
        mTitleView.setTextSize(20);
        return mTitleView;

    }

    private View createBackView() {
        ImageView backView = new ImageView(mContext);
        backView.setImageDrawable(ResourceUtil.getDrawable(mContext, "img_scanner_btn_back"));
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
        params.leftMargin = UIUtil.dip2px(mContext, 10);

        backView.setLayoutParams(params);

        backView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGridView != null) {
                    removeView(mGridView);
                    imageModel.onDestroy();
                    imageModel = null;
                    mGridView = null;
                    imageItemAdapter = null;
                    mListView.setVisibility(VISIBLE);
                    mCancelView.setVisibility(GONE);
                    mTitleView.setText("选择相册");
                } else {//关闭diaolog

                    removeAllViews();
                    if (imageModel != null) {
                        imageModel.onDestroy();
                        imageModel = null;
                    }
                    mGridView = null;
                    imageItemAdapter = null;
                    mListView = null;
                    if (imageFolderModel != null) {
                        imageFolderModel.onDestroy();
                        imageFolderModel = null;
                    }
                    imageFloderAdapter = null;
                    if (mDismissDialogListener != null) {
                        mDismissDialogListener.onClick(null);
                    }
                }

            }
        });
        return backView;

    }


    private void initGridView() {
        if (mGridView == null) {
            mGridView = new RecyclerView(mContext);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mHeight);
            params.topMargin = mTopLayoutHeight;
            mGridView.setLayoutParams(params);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
            gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            mGridView.addItemDecoration(new GridItemDecoration(getContext()));
            mGridView.setLayoutManager(gridLayoutManager);
            imageItemAdapter = new ImageItemAdapter(mContext);
            imageItemAdapter.mOnItemClickListener = new IWidgetClickListener<String>() {
                @Override
                public void onClick(@Nullable String uri) {
                    //gridView 点击事件 
                    ///// TODO: 18/3/21  
                }
            };
            mGridView.setAdapter(imageItemAdapter);
            addView(mGridView);

        }

    }


    private void initListView() {


        mListView = new RecyclerView(mContext);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(mContext);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutmanager);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = mTopLayoutHeight;
        mListView.setLayoutParams(params);
        mListView.setBackgroundColor(Color.WHITE);

        imageFloderAdapter = new ImageFloderAdapter(mContext);
        imageFloderAdapter.mOnItemClickListener = new IWidgetClickListener<ImageFolder>() {
            @Override
            public void onClick(ImageFolder imageFolder) {
                initGridView();

                imageModel = new ImageModel();
                imageModel.onCreate(mContext, ImageScannerDialogLayout.this);
                imageModel.load(imageFolder);

                mListView.setVisibility(GONE);
                mTitleView.setText("选择图片");
                mCancelView.setVisibility(VISIBLE);
            }
        };
        mListView.setAdapter(imageFloderAdapter);

        imageFolderModel = new ImageFolderModel();
        imageFolderModel.onCreate(mContext, new IImageMediaCallback() {
            @Override
            public void onImageLoad(Cursor cursor) {
                imageFloderAdapter.swapCursor(cursor);
            }

            @Override
            public void onImageReset() {
                imageFloderAdapter.swapCursor(null);
            }
        });
        imageFolderModel.loadImageFolders();

        addView(mListView);
    }

    @Override
    public void onImageLoad(Cursor cursor) {
        imageItemAdapter.swapCursor(cursor);
    }

    @Override
    public void onImageReset() {
        imageItemAdapter.swapCursor(null);
    }
}

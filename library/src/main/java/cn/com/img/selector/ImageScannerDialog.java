package cn.com.img.selector;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import cn.com.img.selector.interf.IWidgetClickListener;
import cn.com.img.selector.permission.PermissionCheckHelper;
import cn.com.img.selector.permission.PermissionRequestInfo;
import cn.com.img.selector.utils.APIUtil;
import cn.com.img.selector.view.ImageScannerDialogLayout;


/**
 * Created by mac on 17/12/12.
 */

public class ImageScannerDialog {
    private AlertDialog mDialog;
    private Context mContext;
    public ImageScannerDialogLayout mImageScannerDialogLayout;
    private IWidgetClickListener<String> mCropImageResultListener;

    public ImageScannerDialog(Context context) {
        mContext = context;
        initDialogView();
    }

    public void show() {

        if (mDialog != null && mDialog.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        mDialog = builder.create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.show();

        if (mImageScannerDialogLayout == null) {
            initDialogView();
        }
        //设置dialog全屏幕
        Window window = mDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.CENTER);
        window.setContentView(mImageScannerDialogLayout);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    protected void initDialogView() {
        if (APIUtil.isSupport(23) &&
                !PermissionCheckHelper.isPermissionGranted(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (mContext instanceof Activity) {
                PermissionRequestInfo requestInfo = new PermissionRequestInfo.Builder()
                        .setRequestCode(0)
                        .setRequestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE})
                        .setTipMessages(new String[]{"read pic"})
                        .setCallbackListener(new PermissionCheckHelper.PermissionCallbackListener() {
                            @Override
                            public void onPermissionCheckCallback(int requestCode, String[] permissions, int[] grantResults) {
                                if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                    show();
                                }
                            }
                        })
                        .build();
                PermissionCheckHelper.instance().requestPermissions(mContext, requestInfo);

                return;
            }
        }
        mImageScannerDialogLayout = new ImageScannerDialogLayout(mContext);

        mImageScannerDialogLayout.mDismissDialogListener = new IWidgetClickListener() {
            @Override
            public void onClick(@Nullable Object o) {
                dismiss();
            }
        };

        mImageScannerDialogLayout.mCropImageResultListener = new IWidgetClickListener<String>() {
            @Override
            public void onClick(@Nullable String imgPath) {

                if (mCropImageResultListener != null) {
                    mCropImageResultListener.onClick(imgPath);
                }
                dismiss();
            }
        };
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mImageScannerDialogLayout = null;
        }
    }


}

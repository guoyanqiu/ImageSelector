package cn.com.img.selector;

import android.app.Activity;
import android.os.Bundle;

import cn.com.img.selector.fresco.FrescoImageLoader;
import cn.com.img.selector.fresco.FrescoImageView;
import cn.com.img.selector.glide.GlideImageLoader;
import cn.com.img.selector.provider.RegisterLibsManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //使用Glide加载方式
        RegisterLibsManager.registerImageLoaderLib(GlideImageLoader.class);
        //使用Fresco加载方式
        RegisterLibsManager.registerImageLoaderLib(FrescoImageLoader.class);
        RegisterLibsManager.registerImageViewLib(FrescoImageView.class);

        ImageScannerDialog imageScannerDialog = new ImageScannerDialog(this);
        imageScannerDialog.show();

        //如果需要GridView的点击事件，可以在ImageScannerDialogLayout的initGridView方法设置item的点击事件
    }
}

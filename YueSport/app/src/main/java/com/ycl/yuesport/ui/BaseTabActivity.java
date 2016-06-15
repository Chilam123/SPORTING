package com.ycl.yuesport.ui;

import android.app.TabActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ycl.yuesport.utils.Logger;

/**
 * 继承TabActivity，让MainFrameActivity继承
 */
public class BaseTabActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Logger.d("版本大于4.4");
            Window window = getWindow();
            // Translucent status bar 版本大于4.4，设置状态栏透明
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }



}

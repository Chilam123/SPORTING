package com.ycl.yuesport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.ycl.yuesport.R;
import com.ycl.yuesport.config.AppConfig;
import com.ycl.yuesport.utils.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.switch_show_age_in_yue_settings)
    SwitchCompat switchShowAgeInYue;
    @Bind(R.id.switch_show_age_in_mine_settings)
    SwitchCompat switchShowAgeInMine;
    @Bind(R.id.relativelayout_change_phone_settings)
    RelativeLayout relativelayoutChangePhone;
    @Bind(R.id.relativelayout_change_password_settings)
    RelativeLayout relativelayoutChangePassword;
    @Bind(R.id.relativelayout_about_settings)
    RelativeLayout relativelayoutAbout;
    @Bind(R.id.relativelayout_exit_settings)
    RelativeLayout relativelayoutExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        ButterKnife.bind(this);


        initComponent();
        initToolBar();
        addListener();

        Logger.d("创建了设置activity");
    }

    /**
     * 初始化组件
     */
    private void initComponent() {
        switchShowAgeInYue.setChecked(AppConfig.getInstance().getInt("showAgeInYue", 1) == 1);
        switchShowAgeInMine.setChecked(AppConfig.getInstance().getInt("showAgeInMine", 1) == 1);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        myToolbar.setTitle("设置");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 添加监听事件
     */
    private void addListener() {
        relativelayoutChangePhone.setOnClickListener(this);
        relativelayoutChangePassword.setOnClickListener(this);
        relativelayoutAbout.setOnClickListener(this);
        relativelayoutExit.setOnClickListener(this);

        switchShowAgeInYue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                     //网络请求 传到服务器+返回成功写进sp 待处理..........................................
                    //b为true写1，b为假写0
                    //这里先写进sp 应该在请求网络后写进onSucceed里
                    AppConfig.getInstance().putInt("showAgeInYue",b?1:0);
            }
        });

        switchShowAgeInMine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                //网络请求 传到服务器+返回成功写进sp 待处理..........................................
                //b为true写1，b为假写0
                //这里先写进sp 应该在请求网络后写进onSucceed里
                AppConfig.getInstance().putInt("showAgeInMine",b?1:0);
            }
        });
    }

    /**
     * 添加点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            //待处理各跳转事件.....................................................................
            case R.id.relativelayout_change_phone_settings:
                intent = new Intent(SettingsActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_change_password_settings:
                intent = new Intent(SettingsActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_about_settings:
                intent = new Intent(SettingsActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_exit_settings:
                intent = new Intent(SettingsActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}


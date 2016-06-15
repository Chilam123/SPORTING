package com.ycl.sportsing.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.ycl.sportsing.R;
import com.ycl.sportsing.app.AppContext;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestQueue;

public class MainActivity extends TabActivity implements View.OnClickListener{
    private TabHost host;
    private final static String SHOUYE_STRING = "SHOUYE_STRING";//首页
    private final static String HUODONG_STRING = "HUODONG_STRING";//活动
    private final static String MYSELF_STRING = "MYSELF_STRING";//我的

    private ImageView img_shouye;
    private ImageView img_huodong;
    private ImageView img_mine;
    private TextView text_shouye;
    private TextView text_huodong;
    private TextView  text_mine;
    private LinearLayout linearlayout_shouye;
    private LinearLayout linearlayout_huodong;
    private LinearLayout linearlayout_mine;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getScreenDisplay();

        this.initView();
        host = getTabHost();
        host.setup();
        setShouyeTab();
        setHuodongTab();
        setMyselfTab();
        host.setCurrentTabByTag(SHOUYE_STRING);//默认团购页面

    }

    public void initView(){
        img_shouye =(ImageView) findViewById(R.id.img_shouye);
        img_huodong=(ImageView) findViewById(R.id.img_sports);
        img_mine=(ImageView) findViewById(R.id.img_user);
        img_shouye.setOnClickListener(this);
        img_huodong.setOnClickListener(this);
        img_mine.setOnClickListener(this);

        text_shouye=(TextView) findViewById(R.id.text_shouye);
        text_huodong =(TextView) findViewById(R.id.text_merchant);
        text_mine=(TextView) findViewById(R.id.text_user);

        linearlayout_shouye =(LinearLayout) findViewById(R.id.linearlayout_shouye);
        linearlayout_huodong =(LinearLayout) findViewById(R.id.linearlayout_sports);
        linearlayout_mine=(LinearLayout) findViewById(R.id.linearlayout_user);

        linearlayout_shouye.setOnClickListener(this);
        linearlayout_huodong.setOnClickListener(this);
        linearlayout_mine.setOnClickListener(this);
    }

    private void setShouyeTab() {
        TabHost.TabSpec tabSpec = host.newTabSpec(SHOUYE_STRING);
        tabSpec.setIndicator(SHOUYE_STRING);
        Intent intent = new Intent(MainActivity.this, ShouYeActivity.class);
        tabSpec.setContent(intent);
        host.addTab(tabSpec);
    }

    private void setHuodongTab() {
        TabHost.TabSpec tabSpec = host.newTabSpec(HUODONG_STRING);
        tabSpec.setIndicator(HUODONG_STRING);
        Intent intent = new Intent(MainActivity.this, HuoDongActivity.class);
        tabSpec.setContent(intent);
        host.addTab(tabSpec);
    }

    private void setMyselfTab() {
        TabHost.TabSpec tabSpec = host.newTabSpec(MYSELF_STRING);
        tabSpec.setIndicator(MYSELF_STRING);
        Intent intent = new Intent(MainActivity.this, MineActivity.class);
        tabSpec.setContent(intent);
        host.addTab(tabSpec);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout_shouye:
            case R.id.img_shouye:
                host.setCurrentTabByTag(SHOUYE_STRING);
                img_shouye.setBackgroundResource(R.mipmap.ic_menu_shouye_on);
                text_shouye.setTextColor(getResources().getColor(R.color.green));
                img_huodong.setBackgroundResource(R.mipmap.ic_menu_sports_off);
                text_huodong.setTextColor(getResources().getColor(R.color.textgray));
                img_mine.setBackgroundResource(R.mipmap.ic_menu_user_off);
                text_mine.setTextColor(getResources().getColor(R.color.textgray));
                break;

            case R.id.linearlayout_sports:
            case R.id.img_sports:
                host.setCurrentTabByTag(HUODONG_STRING);
                img_shouye.setBackgroundResource(R.mipmap.ic_menu_shouye_off);
                text_shouye.setTextColor(getResources().getColor(R.color.textgray));
                img_huodong.setBackgroundResource(R.mipmap.ic_menu_sports_on);
                text_huodong.setTextColor(getResources().getColor(R.color.green));
                img_mine.setBackgroundResource(R.mipmap.ic_menu_user_off);
                text_mine.setTextColor(getResources().getColor(R.color.textgray));
                break;

            case R.id.linearlayout_user:
            case R.id.img_user:
                host.setCurrentTabByTag(MYSELF_STRING);
                img_shouye.setBackgroundResource(R.mipmap.ic_menu_shouye_off);
                text_shouye.setTextColor(getResources().getColor(R.color.textgray));
                img_huodong.setBackgroundResource(R.mipmap.ic_menu_sports_off);
                text_huodong.setTextColor(getResources().getColor(R.color.textgray));
                img_mine.setBackgroundResource(R.mipmap.ic_menu_user_on);
                text_mine.setTextColor(getResources().getColor(R.color.green));
                break;

            default:
                break;
        }
    }

    private void getScreenDisplay(){
        Display display=this.getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight=display.getHeight();

        AppContext appContext=(AppContext) getApplicationContext();
        appContext.setScreenWidth(screenWidth);
        appContext.setScreenHeight(screenHeight);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        appContext.setPreferences(preferences);
        NoHttp.init(appContext);
        RequestQueue requestQueue= NoHttp.newRequestQueue();
        appContext.setRequestQueue(requestQueue);
    }
}

package com.ycl.yuesport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.ycl.yuesport.R;
import com.ycl.yuesport.app.AppContext;
import com.ycl.yuesport.config.AppConfig;
import com.ycl.yuesport.utils.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainFrameActivity extends BaseTabActivity implements View.OnClickListener {

    @Bind(R.id.img_yue)
    ImageView imgYue;
    @Bind(R.id.text_yue)
    TextView textYue;
    @Bind(R.id.linearlayout_yue)
    LinearLayout linearlayoutYue;
    @Bind(R.id.img_contact)
    ImageView imgContact;
    @Bind(R.id.text_contact)
    TextView textContact;
    @Bind(R.id.linearlayout_contact)
    LinearLayout linearlayoutContact;
    @Bind(R.id.img_find)
    ImageView imgFind;
    @Bind(R.id.text_find)
    TextView textFind;
    @Bind(R.id.linearlayout_find)
    LinearLayout linearlayoutFind;
    @Bind(R.id.img_mine)
    ImageView imgMine;
    @Bind(R.id.text_mine)
    TextView textMine;
    @Bind(R.id.linearlayout_mine)
    LinearLayout linearlayoutMine;
    @Bind(android.R.id.tabhost)
    TabHost tabHost;

    private final static String YUE_STRING = "YUE_STRING";//约
    private final static String CONTACT_STRING = "CONTACT_STRING";//联系人+消息
    private final static String FIND_STRING = "FIND_STRING";//发现
    private final static String MINE_STRING = "MINE_STRING";//我的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainframe);
        ButterKnife.bind(this);

        init();
    }

    /**
     * 进行初始化
     */
    private void init() {

        getScreenDisplay();

        addListener();
        tabHost = getTabHost();
        tabHost.setup();
        setYueTab();
        setContactTab();
        setFindTab();
        setMineTab();
        tabHost.setCurrentTabByTag(YUE_STRING);//默认约页面

        tempSetUser(); //暂时设置用户
    }

    /**
     * 添加监听事件
     */
    private void addListener() {
        imgYue.setOnClickListener(this);
        imgContact.setOnClickListener(this);
        imgFind.setOnClickListener(this);
        imgMine.setOnClickListener(this);

        textYue = (TextView) findViewById(R.id.text_yue);
        textContact = (TextView) findViewById(R.id.text_contact);
        textFind = (TextView) findViewById(R.id.text_find);
        textMine = (TextView) findViewById(R.id.text_mine);

        linearlayoutYue.setOnClickListener(this);
        linearlayoutContact.setOnClickListener(this);
        linearlayoutFind.setOnClickListener(this);
        linearlayoutMine.setOnClickListener(this);
    }

    private void setYueTab() {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(YUE_STRING);
        tabSpec.setIndicator(YUE_STRING);
        Intent intent = new Intent(MainFrameActivity.this, YueActivity.class);
        tabSpec.setContent(intent);
        tabHost.addTab(tabSpec);
    }

    private void setContactTab() {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(CONTACT_STRING);
        tabSpec.setIndicator(CONTACT_STRING);
        Intent intent = new Intent(MainFrameActivity.this, ContactActivity.class);
        tabSpec.setContent(intent);
        tabHost.addTab(tabSpec);
    }

    private void setFindTab() {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(FIND_STRING);
        tabSpec.setIndicator(FIND_STRING);
        Intent intent = new Intent(MainFrameActivity.this, FindActivity.class);
        tabSpec.setContent(intent);
        tabHost.addTab(tabSpec);
    }

    private void setMineTab() {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(MINE_STRING);
        tabSpec.setIndicator(MINE_STRING);
        Intent intent = new Intent(MainFrameActivity.this, MineActivity.class);
        tabSpec.setContent(intent);
        tabHost.addTab(tabSpec);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout_yue:
            case R.id.img_yue:
                tabHost.setCurrentTabByTag(YUE_STRING);
                imgYue.setBackgroundResource(R.mipmap.ic_menu_yue_on);
                textYue.setTextColor(getResources().getColor(R.color.green));
                imgContact.setBackgroundResource(R.mipmap.ic_menu_contact_off);
                textContact.setTextColor(getResources().getColor(R.color.textgray));
                imgFind.setBackgroundResource(R.mipmap.ic_menu_find_off);
                textFind.setTextColor(getResources().getColor(R.color.textgray));
                imgMine.setBackgroundResource(R.mipmap.ic_menu_mine_off);
                textMine.setTextColor(getResources().getColor(R.color.textgray));

                break;

            case R.id.linearlayout_contact:
            case R.id.img_contact:
                tabHost.setCurrentTabByTag(CONTACT_STRING);
                imgYue.setBackgroundResource(R.mipmap.ic_menu_yue_off);
                textYue.setTextColor(getResources().getColor(R.color.textgray));
                imgContact.setBackgroundResource(R.mipmap.ic_menu_contact_on);
                textContact.setTextColor(getResources().getColor(R.color.green));
                imgFind.setBackgroundResource(R.mipmap.ic_menu_find_off);
                textFind.setTextColor(getResources().getColor(R.color.textgray));
                imgMine.setBackgroundResource(R.mipmap.ic_menu_mine_off);
                textMine.setTextColor(getResources().getColor(R.color.textgray));
                break;

            case R.id.linearlayout_find:
            case R.id.img_find:
                tabHost.setCurrentTabByTag(FIND_STRING);
                imgYue.setBackgroundResource(R.mipmap.ic_menu_yue_off);
                textYue.setTextColor(getResources().getColor(R.color.textgray));
                imgContact.setBackgroundResource(R.mipmap.ic_menu_contact_off);
                textContact.setTextColor(getResources().getColor(R.color.textgray));
                imgFind.setBackgroundResource(R.mipmap.ic_menu_find_on);
                textFind.setTextColor(getResources().getColor(R.color.green));
                imgMine.setBackgroundResource(R.mipmap.ic_menu_mine_off);
                textMine.setTextColor(getResources().getColor(R.color.textgray));
                break;

            case R.id.linearlayout_mine:
            case R.id.img_mine:
                tabHost.setCurrentTabByTag(MINE_STRING);
                imgYue.setBackgroundResource(R.mipmap.ic_menu_yue_off);
                textYue.setTextColor(getResources().getColor(R.color.textgray));
                imgContact.setBackgroundResource(R.mipmap.ic_menu_contact_off);
                textContact.setTextColor(getResources().getColor(R.color.textgray));
                imgFind.setBackgroundResource(R.mipmap.ic_menu_find_off);
                textFind.setTextColor(getResources().getColor(R.color.textgray));
                imgMine.setBackgroundResource(R.mipmap.ic_menu_mine_on);
                textMine.setTextColor(getResources().getColor(R.color.green));
                break;

            default:
                break;
        }
    }

    /**
     * 获取手机屏幕分辨率
     */
    private void getScreenDisplay() {
        Display display = this.getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        AppContext.getInstance().setScreenHeight(screenHeight);
        AppContext.getInstance().setScreenWidth(screenWidth);
    }

    /**
     * 暂时设置用户信息，测试用
     */
    private void tempSetUser(){
        AppConfig.getInstance().putString("userPhone","18813090601");
        AppConfig.getInstance().putString("userPassword","123456");
        AppConfig.getInstance().putString("userNickname","Chilam");
        AppConfig.getInstance().putString("userYueAccount","Land1016");
        AppConfig.getInstance().putInt("userSex",0);
        AppConfig.getInstance().putInt("userAge",22);
        AppConfig.getInstance().putInt("userTag",0);
        AppConfig.getInstance().putInt("userCartState",0);
        AppConfig.getInstance().putInt("userKeepfitArea",5);
        AppConfig.getInstance().putString("userFriendsPurpose","开朗女");
        AppConfig.getInstance().putString("userTouxiang","http://img02.taobaocdn.com/imgextra/i2/686412666/TB23awZaFXXXXaOXXXXXXXXXXXX_!!686412666-0-fleamarket.jpg_728x728.jpg");
        AppConfig.getInstance().putInt("showAgeInYue",1);
        AppConfig.getInstance().putInt("showAgeInMine",1);
    }


    //测试
    @Override
    protected void onDestroy() {
        Logger.d("我销毁了MainFrameActivity");
        super.onDestroy();
    }

    //测试
    @Override
    public void onBackPressed() {
        Logger.d("我按了后退键");
        super.onBackPressed();
    }


}

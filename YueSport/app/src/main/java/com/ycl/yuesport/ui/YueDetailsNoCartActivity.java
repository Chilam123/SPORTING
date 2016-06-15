package com.ycl.yuesport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.yuesport.R;
import com.ycl.yuesport.http.HttpListener;
import com.yolanda.nohttp.Response;

import butterknife.Bind;
import butterknife.ButterKnife;

public class YueDetailsNoCartActivity extends BaseActivity implements HttpListener, View.OnClickListener {

    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.image_publisher_touxiang_yue_details)
    ImageView imagePublisherTouxiang;
    @Bind(R.id.text_publisher_name_yue_details)
    TextView textPublisherName;
    @Bind(R.id.text_publisher_sex_yue_details)
    TextView textPublisherSex;
    @Bind(R.id.text_publisher_tag_yue_details)
    TextView textPublisherTag;
    @Bind(R.id.text_publisher_cartstate_yue_details)
    TextView textPublisherCartstate;
    @Bind(R.id.text_publisher_age_yue_details)
    TextView textPublisherAge;
    @Bind(R.id.text_jianshenfangname_yue_details)
    TextView textJianshenfangname;
    @Bind(R.id.text_yue_people_yue_details)
    TextView textYuePeople;
    @Bind(R.id.text_yue_time_yue_details)
    TextView textYueTime;
    @Bind(R.id.text_price_yue_details)
    TextView textPrice;
    @Bind(R.id.text_yue_areadetails_yue_details)
    TextView textYueAreadetails;
    @Bind(R.id.text_intent_yue_details)
    TextView textIntent;
    @Bind(R.id.button_yue_yue_details)
    Button buttonYue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yue_details_nocart_activity);
        ButterKnife.bind(this);

        initComponent();
        initToolBar();
        addListener();
    }

    /**
     * 初始化组件
     */
    private void initComponent() {

    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        myToolbar.setTitle("约详情");
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
        buttonYue.setOnClickListener(this);
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
            case R.id.relativelayout_touxiang_account_details:

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    }

    @Override
    public void onSucceed(int what, Response response) {


    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {


    }
}

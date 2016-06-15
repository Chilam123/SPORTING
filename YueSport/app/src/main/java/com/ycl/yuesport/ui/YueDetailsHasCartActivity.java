package com.ycl.yuesport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.yuesport.R;
import com.ycl.yuesport.adapter.ImageAdapter;
import com.ycl.yuesport.http.HttpListener;
import com.ycl.yuesport.pager.HackyViewPager;
import com.yolanda.nohttp.Response;

import butterknife.Bind;
import butterknife.ButterKnife;

public class YueDetailsHasCartActivity extends BaseActivity implements View.OnClickListener, HttpListener {

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
    @Bind(R.id.text_cart_description_yue_details)
    TextView textCartDescriptionYueDetails;
    @Bind(R.id.text_yue_people_hint_yue_details)
    TextView textYuePeopleHintYueDetails;
    @Bind(R.id.text_yue_time_hint_yue_details)
    TextView textYueTimeHintYueDetails;
    @Bind(R.id.gridview_jianshenfang_thumb_pic_yue_details)
    public GridView gridviewJianshenfangThumbPic;
    @Bind(R.id.viewpager_expanded_image_yue_details)
    HackyViewPager viewpagerExpandedImage;

    public int[] mThumbIds = { R.mipmap.test_gym1,  R.mipmap.test_gym2, R.mipmap.test_gym3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yue_details_hascart_activity);
        ButterKnife.bind(this);

        initToolBar();
        addListener();

//        //请求约详细信息界面的数据
//        Intent intent = getIntent();
//        String yueID = intent.getStringExtra("yue_id");
//        Request<String> requestYueDetails = NoHttp.createStringRequest("", RequestMethod.POST);
//        requestYueDetails.add("yue_id", yueID);
//        CallServer.getRequestInstance().add(this, 0, requestYueDetails, this, false, false);

        //健身房图片显示，3列，每列80dp,间隔10dp
        gridviewJianshenfangThumbPic.setNumColumns(3);
        gridviewJianshenfangThumbPic.setColumnWidth(80);
        gridviewJianshenfangThumbPic.setHorizontalSpacing(10);
        gridviewJianshenfangThumbPic.setAdapter(new ImageAdapter(this,viewpagerExpandedImage,R.id.container,gridviewJianshenfangThumbPic,mThumbIds));

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
        textYueAreadetails.setOnClickListener(this);
        imagePublisherTouxiang.setOnClickListener(this);
        textPublisherName.setOnClickListener(this);
        textCartDescriptionYueDetails.setOnClickListener(this);
        textPublisherSex.setOnClickListener(this);
        textPublisherTag.setOnClickListener(this);
        textPublisherCartstate.setOnClickListener(this);
        textPublisherAge.setOnClickListener(this);
    }

    /**
     * 添加点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            //待处理各跳转事件.....................................................................

            //点击发布者信息，跳转到查看发布者个人信息界面
            case R.id.image_publisher_touxiang_yue_details:
            case R.id.text_publisher_name_yue_details:
            case R.id.text_cart_description_yue_details:
            case R.id.text_publisher_sex_yue_details:
            case R.id.text_publisher_tag_yue_details:
            case R.id.text_publisher_cartstate_yue_details:
            case R.id.text_publisher_age_yue_details:
                intent = new Intent(YueDetailsHasCartActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            //点击健身房详细地址，跳转到地图界面
            case R.id.text_yue_areadetails_yue_details:
                intent = new Intent(YueDetailsHasCartActivity.this, TestActivity.class);
                startActivity(intent);
                break;

            //点击约按钮
            case R.id.button_yue_yue_details:
                intent = new Intent(YueDetailsHasCartActivity.this, TestActivity.class);
                startActivity(intent);
                break;

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

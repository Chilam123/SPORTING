package com.ycl.yuesport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycl.yuesport.R;
import com.ycl.yuesport.adapter.ImageAdapter;
import com.ycl.yuesport.adapter.ParticipantAdapter;
import com.ycl.yuesport.http.HttpListener;
import com.ycl.yuesport.pager.HackyViewPager;
import com.ycl.yuesport.utils.DensityUtil;
import com.ycl.yuesport.utils.Toast;
import com.yolanda.nohttp.Response;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyYueDetailsActivity extends BaseActivity implements HttpListener, View.OnClickListener {


    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.image_publisher_touxiang_my_yue_details)
    ImageView imagePublisherTouxiang;
    @Bind(R.id.text_publisher_name_my_yue_details)
    TextView textPublisherName;
    @Bind(R.id.text_cart_description_my_yue_details)
    TextView textCartDescription;
    @Bind(R.id.text_publisher_sex_my_yue_details)
    TextView textPublisherSex;
    @Bind(R.id.text_publisher_tag_my_yue_details)
    TextView textPublisherTag;
    @Bind(R.id.text_publisher_cartstate_my_yue_details)
    TextView textPublisherCartstate;
    @Bind(R.id.text_publisher_age_my_yue_details)
    TextView textPublisherAge;
    @Bind(R.id.text_jianshenfangname_my_yue_details)
    TextView textJianshenfangname;
    @Bind(R.id.text_yue_date_my_yue_details)
    TextView textYueDate;
    @Bind(R.id.text_yue_time_my_yue_details)
    TextView textYueTime;
    @Bind(R.id.text_yue_people_my_yue_details)
    TextView textYuePeople;
    @Bind(R.id.text_price_my_yue_details)
    TextView textPrice;
    @Bind(R.id.gridview_jianshenfang_thumb_pic_my_yue_details)
    GridView gridviewJianshenfangThumbPic;
    @Bind(R.id.text_yue_areadetails_my_yue_details)
    TextView textYueAreadetails;
    @Bind(R.id.text_intent_my_yue_details)
    TextView textIntent;
    @Bind(R.id.text_no_participant_my_yue_details)
    TextView textNoParticipant;
    @Bind(R.id.gridview_participant_list_my_yue_details)
    GridView gridviewParticipantList;
    @Bind(R.id.viewpager_expanded_image_my_yue_details)
    HackyViewPager viewpagerExpandedImage;
    @Bind(R.id.container)
    RelativeLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_yue_details_activity);
        ButterKnife.bind(this);

        initToolBar();
        addListener();

//        //请求我的约详细信息界面的数据
//        Intent intent = getIntent();
//        String yueID = intent.getStringExtra("yue_id");
//        Request<String> requestYueDetails = NoHttp.createStringRequest("", RequestMethod.POST);
//        requestYueDetails.add("yue_id", yueID);
//        CallServer.getRequestInstance().add(this, 0, requestYueDetails, this, false, false);


        //模拟健身房图片
        int[] mThumbIds = {R.mipmap.test_gym1, R.mipmap.test_gym2, R.mipmap.test_gym3};

        //健身房图片显示，3列，每列80dp,间隔10dp
        gridviewJianshenfangThumbPic.setNumColumns(3);
        //dip转px
        gridviewJianshenfangThumbPic.setColumnWidth(DensityUtil.dip2px(this, 80f));
        gridviewJianshenfangThumbPic.setStretchMode(GridView.NO_STRETCH);
        gridviewJianshenfangThumbPic.setHorizontalSpacing(10);
        gridviewJianshenfangThumbPic.setAdapter(new ImageAdapter(this, viewpagerExpandedImage, R.id.container, gridviewJianshenfangThumbPic, mThumbIds));

        //在yue中看有没有参加者，有的话显示参加者列表,没有的话设置参加者列表gone,提示语句invisible,否则相反
        //这里模拟
        gridviewParticipantList.setColumnWidth(DensityUtil.dip2px(this, 40f));
        gridviewParticipantList.setHorizontalSpacing(10);
        gridviewParticipantList.setVerticalSpacing(8);
        gridviewParticipantList.setStretchMode(GridView.NO_STRETCH);
        gridviewParticipantList.setNumColumns(GridView.AUTO_FIT);
        gridviewParticipantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyYueDetailsActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
        //模拟用户头像图片
        int[] mThumbIds2 = {R.mipmap.test_ic_touxiang, R.mipmap.test_touxiang_girl,
                R.mipmap.test_touxiang_man, R.mipmap.test_ic_touxiang, R.mipmap.test_touxiang_girl,
                R.mipmap.test_touxiang_man, R.mipmap.test_ic_touxiang, R.mipmap.test_touxiang_girl,
                R.mipmap.test_touxiang_man, R.mipmap.test_ic_touxiang, R.mipmap.test_touxiang_girl,
                R.mipmap.test_touxiang_man, R.mipmap.test_ic_touxiang, R.mipmap.test_touxiang_girl};
        //设置gridview图片高度，layoutparams长宽默认是像素px,转dp
        AbsListView.LayoutParams participantLP = new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,DensityUtil.dip2px(this, 40f));
        gridviewParticipantList.setAdapter(new ParticipantAdapter(this, mThumbIds2, participantLP));
        //隐藏没有参与者提示
        textNoParticipant.setVisibility(View.GONE);
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
        myToolbar.setTitle("我的约");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                deleteMyYue();
                return true;
            }
        });
    }

    /**
     * 设置memu菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 使用inflate方法来把布局文件中的定义的菜单 加载给 第二个参数所对应的menu对象
        getMenuInflater().inflate(R.menu.menu_delete_my_yue, menu);
        return true;
    }

    /**
     * 删除我的约
     */
    private void deleteMyYue(){
        //待处理，删除我的约，请求网络看有没有人参加了，没有人参加才能删
        Toast.show("删除了");

    }

    /**
     * 添加监听事件
     */
    private void addListener() {
        textYueAreadetails.setOnClickListener(this);
        imagePublisherTouxiang.setOnClickListener(this);
        textPublisherName.setOnClickListener(this);
        textCartDescription.setOnClickListener(this);
        textPublisherSex.setOnClickListener(this);
        textPublisherTag.setOnClickListener(this);
        textPublisherCartstate.setOnClickListener(this);
        textPublisherAge.setOnClickListener(this);
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

            //点击发布者信息，跳转到查看发布者个人信息界面
            case R.id.image_publisher_touxiang_my_yue_details:
            case R.id.text_publisher_name_my_yue_details:
            case R.id.text_cart_description_my_yue_details:
            case R.id.text_publisher_sex_my_yue_details:
            case R.id.text_publisher_tag_my_yue_details:
            case R.id.text_publisher_cartstate_my_yue_details:
            case R.id.text_publisher_age_my_yue_details:
                intent = new Intent(MyYueDetailsActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            //点击健身房详细地址，跳转到地图界面
            case R.id.text_yue_areadetails_my_yue_details:
                intent = new Intent(MyYueDetailsActivity.this, TestActivity.class);
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


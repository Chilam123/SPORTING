package com.ycl.yuesport.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.ycl.yuesport.R;
import com.ycl.yuesport.adapter.YueListAdapter;
import com.ycl.yuesport.config.AppConfig;
import com.ycl.yuesport.domain.Yue;
import com.ycl.yuesport.http.CallServer;
import com.ycl.yuesport.http.HttpListener;
import com.ycl.yuesport.myview.ExpandTabView;
import com.ycl.yuesport.myview.ViewArea;
import com.ycl.yuesport.myview.ViewPrice;
import com.ycl.yuesport.myview.ViewSex;
import com.ycl.yuesport.myview.ViewTime;
import com.ycl.yuesport.parser.YueListParser;
import com.ycl.yuesport.utils.Logger;
import com.ycl.yuesport.utils.XListView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;
import com.yolanda.nohttp.cache.CacheMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class YueActivity extends AppCompatActivity implements View.OnClickListener, XListView.IXListViewListener, HttpListener {

    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.expandtab_hascart_view)
    ExpandTabView expandtabViewHasCart;
    @Bind(R.id.expandtab_nocart_view)
    ExpandTabView expandtabViewNoCart;
    @Bind(R.id.relativelayout_hascart_yue)
    RelativeLayout relativelayoutHasCartYue;
    @Bind(R.id.relativelayout_nocart_yue)
    RelativeLayout relativelayoutNoCartYue;
    @Bind(R.id.xlistview_yue_list)
    XListView xlistviewYueList;
    @Bind(R.id.text_hascart_yue)
    TextView textHascartYue;
    @Bind(R.id.text_nocart_yue)
    TextView textNocartYue;
    @Bind(R.id.image_hascart_choose)
    ImageView imageHascartChoose;
    @Bind(R.id.image_nocart_choose)
    ImageView imageNocartChoose;
    @Bind(R.id.relativelayout_nonetwork_yue)
    RelativeLayout relativelayoutNonetworkYue;

    private ViewArea viewArea;
    private ViewTime viewTime;
    private ViewSex viewSex;
    private ViewPrice viewPrice;
    private ViewArea viewAreaNoCart;
    private ViewTime viewTimeNoCart;
    private ViewSex viewSexNoCart;
    private ArrayList<View> mHasCartViewArray = new ArrayList<View>();
    private ArrayList<View> mNoCartViewArray = new ArrayList<View>();
    private static boolean isHasCart = true; //记录是有卡选项状态还是无卡选项状态
    ArrayList<Integer> selectedTimeArrayListHasCart;
    ArrayList<Integer> selectedTimeArrayListNoCart;
    private YueListParser yueListParser;
    public static ArrayList<Yue> yueArrayList=new ArrayList<>();
    private YueListAdapter yueListAdapter=null;

//    private HasCartFragment hasCartFragment;
//    private NoCartFragment noCartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yue);
        ButterKnife.bind(this);

        initToolBar();
        // initViewPager();
        initComponent();
        addListener();
        initHasCartClassifyView(); //初始化有卡分类栏
        initNoCartClassifyView();//初始化无卡分类栏
        initXListView();//初始化xListView
        requestYueFromNet();//请求约消息列表的网络请求

    }

    /**
     * 初始化ToolBar
     */
    void initToolBar() {
        myToolbar.setTitle("约");
        setSupportActionBar(myToolbar);
        //设置点击发布事件
        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                publishYueHandle();
                return false;
            }
        });
    }

    /**
     * 设置toolbar菜单栏
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 使用inflate方法来把布局文件中的定义的菜单 加载给 第二个参数所对应的menu对象
        getMenuInflater().inflate(R.menu.menu_publish_yue, menu);
        return true;
    }



    /**
     * 点击发布约处理事件
     */
    private void publishYueHandle() {
          Intent intent=new Intent(YueActivity.this,PublishYueActivity.class);
        startActivity(intent);

    }

//    /**
//     * 初始化ViewPager和TabLayout
//     */
//    private void initViewPager(){
//        tablayoutYue.addTab(tablayoutYue.newTab().setText("有卡"));
//        tablayoutYue.addTab(tablayoutYue.newTab().setText("无卡"));
//        tablayoutYue.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        YuePagerAdapter yuePagerAdapter=new YuePagerAdapter(getSupportFragmentManager(),tablayoutYue.getTabCount());
//        viewpagerYue.setAdapter(yuePagerAdapter);
//
//        viewpagerYue.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayoutYue));
//
//        tablayoutYue.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewpagerYue.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                //待处理  终止网络请求...........................................
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {}
//        });
//    }

    /**
     * 初始化组件,默认是有卡，显示有卡分类栏
     */
    private void initComponent() {
        //默认选择有卡选项
        textHascartYue.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        textNocartYue.setTextColor(getResources().getColor(R.color.textgraydark));
        relativelayoutHasCartYue.setClickable(false);
        relativelayoutNoCartYue.setClickable(true);
        imageHascartChoose.setVisibility(View.VISIBLE);
        imageNocartChoose.setVisibility(View.INVISIBLE);
        //默认显示有卡分类栏
        expandtabViewHasCart.setVisibility(View.VISIBLE);
        expandtabViewNoCart.setVisibility(View.INVISIBLE);
    }


    /**
     * 添加监听事件
     */
    private void addListener() {
        relativelayoutHasCartYue.setOnClickListener(this);
        relativelayoutNoCartYue.setOnClickListener(this);
    }

    /**
     * 初始化有卡分类栏,默认是有卡，4个选项
     */
    private void initHasCartClassifyView() {
        viewArea = new ViewArea(this, R.mipmap.choosearea_bg_hascart_area);
        viewTime = new ViewTime(this, R.mipmap.choosearea_bg_hascart_time);
        viewSex = new ViewSex(this, R.mipmap.choosearea_bg_hascart_sex);
        viewPrice = new ViewPrice(this, R.mipmap.choosearea_bg_hascart_price);

        mHasCartViewArray.add(viewArea);
        mHasCartViewArray.add(viewTime);
        mHasCartViewArray.add(viewSex);
        mHasCartViewArray.add(viewPrice);
        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("地区");
        mTextArray.add("时间");
        mTextArray.add("性别");
        mTextArray.add("价格");
        int[] photo = {};
        expandtabViewHasCart.setValue(mTextArray, mHasCartViewArray, photo);
        expandtabViewHasCart.setTitle(viewArea.getShowText(), 0);
        expandtabViewHasCart.setTitle(viewTime.getShowText(), 1);
        expandtabViewHasCart.setTitle(viewSex.getShowText(), 2);
        expandtabViewHasCart.setTitle(viewPrice.getShowText(), 3);
        //  expandtabViewHasCart.setPhoto(photo[0], 0);

        viewArea.setOnSelectListener(new ViewArea.OnSelectListener() {

            public void getValue(int choosePosition, String showText) {
                onRefresh(viewArea, showText);
            }
        });
        viewTime.setOnSelectListener(new ViewTime.OnSelectListener() {

            public void getValue(ArrayList<Integer> selectedTimeArrayList) {
                selectedTimeArrayListHasCart = selectedTimeArrayList;
                onRefresh(viewTime, "");
            }

            public void getPhoto(int showPhoto) {
                onRefreshPhoto(viewTime, showPhoto);
            }

        });

        viewSex.setOnSelectListener(new ViewSex.OnSelectListener() {

            public void getValue(int choosePosition, String showText) {
                onRefresh(viewSex, showText);
            }
        });

        viewPrice.setOnSelectListener(new ViewPrice.OnSelectListener() {

            public void getValue(int choosePosition, String showText) {
                onRefresh(viewPrice, showText);
            }
        });
    }

    /**
     * 初始化无卡分类栏,3个选项
     */
    private void initNoCartClassifyView() {
        viewAreaNoCart = new ViewArea(this);
        viewTimeNoCart = new ViewTime(this);
        viewSexNoCart = new ViewSex(this);

        mNoCartViewArray.add(viewAreaNoCart);
        mNoCartViewArray.add(viewTimeNoCart);
        mNoCartViewArray.add(viewSexNoCart);
        ArrayList<String> mNoCartTextArray = new ArrayList<String>();
        mNoCartTextArray.add("地区");
        mNoCartTextArray.add("时间");
        mNoCartTextArray.add("性别");
        int[] photo = {};
        expandtabViewNoCart.setValue(mNoCartTextArray, mNoCartViewArray, photo);
        expandtabViewNoCart.setTitle(viewArea.getShowText(), 0);
        expandtabViewNoCart.setTitle(viewTime.getShowText(), 1);
        expandtabViewNoCart.setTitle(viewSex.getShowText(), 2);

        viewAreaNoCart.setOnSelectListener(new ViewArea.OnSelectListener() {

            public void getValue(int choosePosition, String showText) {
                onRefresh(viewAreaNoCart, showText);
            }
        });

        viewTimeNoCart.setOnSelectListener(new ViewTime.OnSelectListener() {

            public void getValue(ArrayList<Integer> selectedTimeArrayList) {
                selectedTimeArrayListNoCart = selectedTimeArrayList;
                onRefresh(viewTimeNoCart, "");

            }

            public void getPhoto(int showPhoto) {
                onRefreshPhoto(viewTimeNoCart, showPhoto);
            }

        });

        viewSexNoCart.setOnSelectListener(new ViewSex.OnSelectListener() {

            public void getValue(int choosePosition, String showText) {
                onRefresh(viewSexNoCart, showText);
            }
        });

    }


    private void onRefresh(View view, String showText) {
        if (isHasCart) {
            expandtabViewHasCart.onPressBack();
//            int position = getPositon(view);
//        if (position >= 0 && !expandtabViewHasCart.getTitle(position).equals(showText)) {
//            expandtabViewHasCart.setTitle(showText, position);
//        }

            //待处理，更新下面显示的数据......................................................0.......
        } else {
            expandtabViewNoCart.onPressBack();
            //待处理，更新下面显示的数据......................................................0.......
        }
        Toast.makeText(YueActivity.this, showText, Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * 刷新分类栏左侧的图片
     */
    private void onRefreshPhoto(View view, int showPhoto) {
        if (isHasCart) {
            expandtabViewHasCart.onPressBack();
            int position = getPositon(view);
            if (position >= 0
                    && !expandtabViewHasCart.getPhoto(position).equals(showPhoto)) {
                expandtabViewHasCart.setPhoto(showPhoto, position);
            }
        } else {
            expandtabViewNoCart.onPressBack();
            int position = getPositon(view);
            if (position >= 0
                    && !expandtabViewNoCart.getPhoto(position).equals(showPhoto)) {
                expandtabViewNoCart.setPhoto(showPhoto, position);
            }
        }
    }

    private int getPositon(View tView) {
        if (isHasCart) {
            for (int i = 0; i < mHasCartViewArray.size(); i++) {
                if (mHasCartViewArray.get(i) == tView) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < mNoCartViewArray.size(); i++) {
                if (mNoCartViewArray.get(i) == tView) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void onBackPressed() {
        if (isHasCart) {
            if (!expandtabViewHasCart.onPressBack()) {
                finish();
            }
        } else {
            if (!expandtabViewNoCart.onPressBack()) {
                finish();
            }
        }
    }


    /**
     * 设置点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativelayout_hascart_yue:
                expandtabViewNoCart.onPressBack();
                //设置状态为有卡
                isHasCart = true;
                //更改选项卡为有卡
                textHascartYue.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                textNocartYue.setTextColor(getResources().getColor(R.color.textgraydark));
                relativelayoutHasCartYue.setClickable(false);
                relativelayoutNoCartYue.setClickable(true);
                imageHascartChoose.setVisibility(View.VISIBLE);
                imageNocartChoose.setVisibility(View.INVISIBLE);
                //显示有卡分类栏
                expandtabViewHasCart.setVisibility(View.VISIBLE);
                expandtabViewNoCart.setVisibility(View.INVISIBLE);
                Logger.d("点击了有卡");
                break;
            case R.id.relativelayout_nocart_yue:
                expandtabViewHasCart.onPressBack();
                //设置状态为无卡
                isHasCart = false;
                //更改选项卡为无卡
                textNocartYue.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                textHascartYue.setTextColor(getResources().getColor(R.color.textgraydark));
                relativelayoutHasCartYue.setClickable(true);
                relativelayoutNoCartYue.setClickable(false);
                imageHascartChoose.setVisibility(View.INVISIBLE);
                imageNocartChoose.setVisibility(View.VISIBLE);
                //显示无卡分类栏
                expandtabViewHasCart.setVisibility(View.INVISIBLE);
                expandtabViewNoCart.setVisibility(View.VISIBLE);
                Logger.d("点击了无卡");
                break;
            default:
                break;
        }

    }


    /**
     * 初始化XListView
     */
    private void initXListView() {
        xlistviewYueList.setPullLoadEnable(true);
        xlistviewYueList.setPullRefreshEnable(true);
        xlistviewYueList.setXListViewListener(this);
        //设置item点击事件
        xlistviewYueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 测试
                Logger.d("点击了ListView:" + i);
                Intent intent=null;
                if(isHasCart){
                   intent=new Intent(YueActivity.this,YueDetailsHasCartActivity.class);
                }else
                    intent=new Intent(YueActivity.this,YueDetailsNoCartActivity.class);

                intent.putExtra("yue_id",yueArrayList.get(i-1).getYue_id());
                startActivity(intent);

                //待处理，跳进activity里，又点击了约，约成功后，给个提示“你已经约成功了，快和对方打声招呼吧”，然后跳到聊天界面。。。。。。。。。。。。
                //跳到聊天界面前销毁掉进去的约界面

            }
        });

        xlistviewYueList.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(),true,true));
    }

    /**
     * 加载数据后的操作,停止刷新
     */
    private void onLoad() {
        xlistviewYueList.stopRefresh();
        xlistviewYueList.stopLoadMore();

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        setLastRefreshTime();
        //停止刷新
        onLoad();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

        //停止刷新
        onLoad();
    }

    /**
     * 设置上次刷新时间
     */
    private void setLastRefreshTime(){
        //设置上次刷新的时间
        Long lastTime= AppConfig.getInstance().getLong("last_refresh_time",0);
        Date date=new Date();
        Long nowTime=date.getTime();

        if(lastTime!=0){
            Long spanTime=nowTime-lastTime;
            //时间差大于两小时，显示上次刷新时间的完整格式，如2016.05.25 12:30
            if(spanTime>2*3600*1000){
                Date date2=new Date(lastTime);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                xlistviewYueList.setRefreshTime(dateFormat.format(date2));
            }
            //一小时到两小时之间，显示上次刷新时间为一小时前
            else if(spanTime>1*3600*1000)
                xlistviewYueList.setRefreshTime("一小时前");
                //半小时到一小时之间，显示上次刷新时间为30分钟前
            else if(spanTime>0.5*3600*1000)
                xlistviewYueList.setRefreshTime("30分钟前");
                //10分钟到30分钟之间，显示上次刷新时间为10分钟前
            else if(spanTime>0.1*3600*1000)
                xlistviewYueList.setRefreshTime("10分钟前");
                //10分钟内，显示上次刷新时间为刚刚
            else
                xlistviewYueList.setRefreshTime("刚刚");
        }

        AppConfig.getInstance().putLong("last_refresh_time",nowTime);
    }



    /**
     * 向服务器请求网路数据
     */
    private void requestYueFromNet() {
        Request<String> request = NoHttp.createStringRequest("45646", RequestMethod.POST);
        request.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);//请求失败时读取缓存数据
        //待处理，加个人信息进request.......................................................

        CallServer.getRequestInstance().add(this, 0, request, this, false, false);
    }

    @Override
    public void onSucceed(int what, Response response) {
        //请求yueListView数据
        if (what == 0) {
            //如果是缓存中的数据，说明网络连接不可用,显示无网络提示
            if (response.isFromCache()) {
                relativelayoutNonetworkYue.setVisibility(View.VISIBLE);
            }else
                relativelayoutNonetworkYue.setVisibility(View.GONE);

            String yueResponseString= (String)response.get();
            yueListParser=new YueListParser();
            yueArrayList=yueListParser.getYueList(yueResponseString);
            yueListAdapter=new YueListAdapter(this,yueArrayList);
            xlistviewYueList.setAdapter(yueListAdapter);
            //更新数据后的操作
            onLoad();

        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

        //请求网络失败，显示网络连接不可用提示
        relativelayoutNonetworkYue.setVisibility(View.VISIBLE);
        Logger.d("请求数据失败了");


        //真实时把下面都删除
        //下面单机模拟
        String[] touxiangTest={"http://cdn.duitang.com/uploads/item/201506/25/20150625231500_jynTZ.thumb.224_0.jpeg","http://awb.img.xmtbang.com/img/uploadnew/201510/24/d2becf3378f14e8eaec8019c6b2bd898.jpg",
                "http://cdn.duitang.com/uploads/item/201507/21/20150721204106_Kzndt.thumb.224_0.jpeg","http://img.wzfzl.cn/uploads/allimg/150719/co150G9101303-24.jpg"};
        for(int i=0;i<100;i++){
            Yue yue=new Yue();
            yue.setYue_id(String.valueOf(i));
            int random=(int)(0+Math.random()*(3-0+1));
            yue.setPublisher_touxiang(touxiangTest[random]);
            yue.setPublisher_sex("男");
            yue.setYue_time("13:30");
            yue.setYue_jianshenfang_name("大众健身房俱乐部"+i);
            yue.setYue_price(i+1);
            yueArrayList.add(yue);
        }

        yueListAdapter=new YueListAdapter(this,yueArrayList);
        xlistviewYueList.setAdapter(yueListAdapter);
        onLoad();
    }


}

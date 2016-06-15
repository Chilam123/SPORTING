package com.ycl.yuesport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.ycl.yuesport.R;
import com.ycl.yuesport.adapter.MyYueListAdapter;
import com.ycl.yuesport.domain.Yue;
import com.ycl.yuesport.http.CallServer;
import com.ycl.yuesport.http.HttpListener;
import com.ycl.yuesport.parser.MyYueListParser;
import com.ycl.yuesport.utils.Logger;
import com.ycl.yuesport.utils.XListView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;
import com.yolanda.nohttp.cache.CacheMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyYueActivity extends BaseActivity implements XListView.IXListViewListener, HttpListener {

    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.xlistview_yue_list)
    XListView xlistviewMyYueList;
    @Bind(R.id.relativelayout_nonetwork_yue)
    RelativeLayout relativelayoutNonetworkYue;

    private MyYueListParser myYueListParser;
    public static ArrayList<Yue> myYueArrayList=new ArrayList<>();
    private MyYueListAdapter myYueListAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_yue_activity);
        ButterKnife.bind(this);

        initToolBar();
        initXListView();

        requestMyYueFromNet();
    }

    /**
     * 向服务器请求网路数据
     */
    private void requestMyYueFromNet() {
        Request<String> request = NoHttp.createStringRequest("45646", RequestMethod.POST);
        request.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);//请求失败时读取缓存数据
        //待处理，加个人信息进request.......................................................

        CallServer.getRequestInstance().add(this, 0, request, this, false, false);
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
    }

    /**
     * 初始化XListView
     */
    private void initXListView() {
        xlistviewMyYueList.setPullLoadEnable(true);
        xlistviewMyYueList.setXListViewListener(this);
        //设置item点击事件
        xlistviewMyYueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 测试
                Logger.d("点击了ListView:" + i);
                Intent intent=new Intent(MyYueActivity.this, MyYueDetailsActivity.class);
                startActivity(intent);

            }
        });

        xlistviewMyYueList.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
    }

    /**
     * 加载数据后的操作,停止刷新
     */
    private void onLoad() {
        xlistviewMyYueList.stopRefresh();
        xlistviewMyYueList.stopLoadMore();

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {

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



    @Override
    public void onSucceed(int what, Response response) {
        //请求yueListView数据
        if (what == 0) {
            //如果是缓存中的数据，说明网络连接不可用,显示无网络提示
            if (response.isFromCache()) {
                relativelayoutNonetworkYue.setVisibility(View.VISIBLE);
            } else
                relativelayoutNonetworkYue.setVisibility(View.GONE);

            String myYueResponseString = (String) response.get();
            myYueListParser = new MyYueListParser();
            myYueArrayList = myYueListParser.getMyYueList(myYueResponseString);
            myYueListAdapter = new MyYueListAdapter(this, myYueArrayList);
            xlistviewMyYueList.setAdapter(myYueListAdapter);
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
        for (int i = 0; i < 20; i++) {
            Yue yue = new Yue();
            yue.setYue_id(String.valueOf(i));
            yue.setYue_date("2016/6/23");
            yue.setYue_time("13:30");
            yue.setYue_jianshenfang_name("大众健身房俱乐部" + i);
            yue.setYue_price(i + 1);
            if(i%2==0)
                yue.setCart_state(0);
            else
               yue.setCart_state(1);
            yue.setYue_need_people(5);
            yue.setYue_now_people(2);
            yue.setYue_intent("我们几个女生想找个比较好看的男生和我们一起健身。");
            myYueArrayList.add(yue);
        }

        myYueListAdapter = new MyYueListAdapter(this, myYueArrayList);
        xlistviewMyYueList.setAdapter(myYueListAdapter);
        onLoad();
    }
}

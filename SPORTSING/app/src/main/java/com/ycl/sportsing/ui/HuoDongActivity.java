package com.ycl.sportsing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.ycl.sportsing.R;
import com.ycl.sportsing.adapter.SportAllAdapter;
import com.ycl.sportsing.app.AppContext;
import com.ycl.sportsing.common.NetRequestConstant;
import com.ycl.sportsing.common.NetUrlConstant;
import com.ycl.sportsing.domain.Sport;
import com.ycl.sportsing.domain.User;
import com.ycl.sportsing.interfaces.Netcallback;
import com.ycl.sportsing.myview.ExpandTabView;
import com.ycl.sportsing.myview.ViewLeft;
import com.ycl.sportsing.myview.ViewMiddle;
import com.ycl.sportsing.myview.ViewRight;
import com.ycl.sportsing.parser.SportParser;
import com.ycl.sportsing.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HuoDongActivity extends BaseActivity {

    @Bind(R.id.imagebutton_publish_huodong)
    ImageButton imagebuttonPublishHuodong;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewLeft viewLeft;
    private ViewMiddle viewMiddle;
    private ViewRight viewRight;
    public ListView aListView;
    public static ArrayList<Sport> allList = new ArrayList<Sport>();
    private SportAllAdapter sportAllAdapter;
    private ExpandTabView expandTabView;

    private static String SPORT_CITY = "不限城市";
    private static String SPORT_XUECHANG = "不限雪场";
    private static String SPORT_DATE = "不限时间";

    private static final int CHANGE_JOIN_STATE = 10;
    private static final int PUBLISH_HUODONG = 11;
    private static final int PUBLISH_SUCCESS = 12;
    private final int REFRESHSPORT=20;

    public  Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_JOIN_STATE:
                    int sportID = (int) msg.obj;
                    Logger.i("我收到了要改变参加状态信息,参加活动ID：" + sportID);
                    //接下来向服务器同步活动信息
                    joinSport(sportID);
                    break;
                case REFRESHSPORT:
                    initAllSport();
                    Logger.i("登录后重新修改活动列表");
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huo_dong);
        ButterKnife.bind(this);

        initClassifyView();
        initSportsListView();

        imagebuttonPublishHuodong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HuoDongActivity.this, PublishHuodongActivity.class);
                startActivityForResult(intent, PUBLISH_HUODONG);
            }
        });

        ((AppContext)getApplicationContext()).setHandler(handler);

    }

    @Override
    void init() {
    }

    /*
     * 初始化分类栏
	 */
    private void initClassifyView() {
        expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
        viewLeft = new ViewLeft(this);
        viewMiddle = new ViewMiddle(this);
        viewRight = new ViewRight(this);

        mViewArray.add(viewLeft);
        mViewArray.add(viewMiddle);
        mViewArray.add(viewRight);
        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("城市");
        mTextArray.add("雪场");
        mTextArray.add("日期");
        int[] photo = {R.mipmap.ic_category_all, R.mipmap.ic_addr,
                R.mipmap.ic_order};
        expandTabView.setValue(mTextArray, mViewArray, photo);
        expandTabView.setTitle(viewLeft.getShowText(), 0);
        expandTabView.setTitle(viewMiddle.getShowText(), 1);
        expandTabView.setTitle(viewRight.getShowText(), 2);
        expandTabView.setPhoto(photo[0], 0);

        viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

            public void getValue(String distance, String showText) {
                onRefresh(viewLeft, showText);
                SPORT_CITY = showText;
                onRefreshSport();
            }
        });
        viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

            public void getValue(String distance, String showText) {
                onRefresh(viewMiddle, showText);
                SPORT_XUECHANG = showText;
                onRefreshSport();

            }

        });

        viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

            public void getValue(String distance, String showText) {
                onRefresh(viewRight, showText);
                SPORT_DATE = showText;
                onRefreshSport();
            }
        });


    }

    private void onRefreshSport() {
        ArrayList<Sport> tempList = new ArrayList<Sport>();
        boolean flag = false;  //判断是否已经过了筛选
        if (!SPORT_CITY.equals("不限城市")) {
            for (int i = 0; i < allList.size(); i++) {
                if (allList.get(i).getSport_city().equals(SPORT_CITY))
                    tempList.add(allList.get(i));
            }
            flag = true;
        }
        if (!SPORT_XUECHANG.equals("不限雪场")) {
            if (flag) {  //有筛选了城市,直接在筛选后的list中找符合条件的
                ArrayList<Sport> tempList2 = (ArrayList<Sport>) tempList.clone();
                tempList.clear();
                for (int i = 0; i < tempList2.size(); i++) {
                    if (tempList2.get(i).getSport_xuechang().equals(SPORT_XUECHANG))
                        tempList.add(tempList2.get(i));
                }
            } else {  //没筛选城市,直接在allList中找符合条件的
                for (int i = 0; i < allList.size(); i++) {
                    if (allList.get(i).getSport_xuechang().equals(SPORT_XUECHANG))
                        tempList.add(allList.get(i));
                }
            }
            flag = true;
        }
        if (!SPORT_DATE.equals("不限时间")) {
            int timeToNow = 0;
            switch (SPORT_DATE) {
                case "一周内":
                    timeToNow = 7;
                    break;
                case "一个月内":
                    timeToNow = 30;
                    break;
                case "三个月内":
                    timeToNow = 90;
                    break;
                default:
                    break;
            }
            if (flag) { //城市和雪场至少有一个筛选了，直接从tempList中获取
                ArrayList<Sport> tempList2 = (ArrayList<Sport>) tempList.clone();
                tempList.clear();
                for (int i = 0; i < tempList2.size(); i++) {
                    if (tempList2.get(i).getTime_to_now() < timeToNow)
                        tempList.add(tempList2.get(i));
                }
            } else { //城市和雪场都没筛选,从allList中获取
                for (int i = 0; i < allList.size(); i++) {
                    if (allList.get(i).getTime_to_now() < timeToNow)
                        tempList.add(allList.get(i));
                }
            }
            flag = true;
        }
        if (!flag)  //三个都不限
            tempList = allList;

        sportAllAdapter.setData(tempList);
    }

    //刷新TabItem栏为你选中的那个选项的名字
    private void onRefresh(View view, String showText) {

        expandTabView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
            expandTabView.setTitle(showText, position);
        }
        Toast.makeText(HuoDongActivity.this, showText, Toast.LENGTH_SHORT)
                .show();
    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }

    public void onBackPressed() {

        if (!expandTabView.onPressBack()) {
            finish();
        }
    }

    private void initSportsListView() {
        aListView = (ListView) findViewById(R.id.listview_sportsall);

        initAllSport();

//        //模拟服务器的allList
//        Sport sport1 = new Sport();
//        sport1.setSport_id(123);
//        sport1.setPublisher_id("Chilam");
//        sport1.setSport_title("本周六南山滑雪场大型Party活动");
//        sport1.setSport_city("北京");
//        sport1.setSport_xuechang("南山滑雪场");
//        sport1.setSport_date("2016年5月15日");
//        sport1.setSport_picture("http://img2.imgtn.bdimg.com/it/u=3942745545,1280402487&fm=21&gp=0.jpg");
//        sport1.setIs_join(true);
//        sport1.setSport_join_list("Chilam&Merry");
//        //下面本该服务器计算时间，现在先模拟
//        sport1.setTime_to_now(5);
//        allList.add(sport1);
//        //123
//
//        Sport sport2 = new Sport();
//        sport2.setSport_id(1232);
//        sport2.setPublisher_id("Chilam");
//        sport2.setSport_title("周五怀渔阳雪场约起~~~~");
//        sport2.setSport_city("北京");
//        sport2.setSport_xuechang("渔阳滑雪场");
//        sport2.setSport_date("2016年6月10日");
//        sport2.setSport_picture("http://img0.imgtn.bdimg.com/it/u=1911760539,2751271597&fm=21&gp=0.jpg");
//        sport2.setIs_join(false);
//        sport2.setTime_to_now(35);
//        //sport2.setSport_join_list("Chilam&Merry");
//        allList.add(sport2);
//
//        Sport sport3 = new Sport();
//        sport3.setSport_id(1233);
//        sport3.setPublisher_id("Chilam");
//        sport3.setSport_title("周五怀柔北雪场约起~~~~");
//        sport3.setSport_city("北京");
//        sport3.setSport_xuechang("怀北滑雪场");
//        sport3.setSport_date("2016年5月20日");
//        sport3.setSport_picture("http://img0.imgtn.bdimg.com/it/u=1911760539,2751271597&fm=21&gp=0.jpg");
//        sport3.setIs_join(false);
//        sport3.setTime_to_now(10);
//        //sport2.setSport_join_list("Chilam&Merry");
//        allList.add(sport3);
//
//        Sport sport4 = new Sport();
//        sport4.setSport_id(1234);
//        sport4.setPublisher_id("Chilam");
//        sport4.setSport_title("本周五怀柔滑雪场约起~~~~");
//        sport4.setSport_city("北京");
//        sport4.setSport_xuechang("怀柔滑雪场");
//        sport4.setSport_date("2016年8月10日");
//        sport4.setSport_picture("http://img0.imgtn.bdimg.com/it/u=1911760539,2751271597&fm=21&gp=0.jpg");
//        sport4.setIs_join(false);
//        //sport2.setSport_join_list("Chilam&Merry");
//        sport4.setTime_to_now(60);
//        allList.add(sport4);

//        sportAllAdapter = new SportAllAdapter(HuoDongActivity.this, allList, handler);
//        aListView.setAdapter(sportAllAdapter);

    }

    private void initAllSport() {
        allList.clear();
        NetRequestConstant nrc = new NetRequestConstant();
        NetRequestConstant.requestUrl = NetUrlConstant.SPORTURL;
        NetRequestConstant.context = this;
        nrc.setType(HttpRequestType.GET);
        getServer(new Netcallback() {

            public void preccess(Object res, boolean flag) {

                if (res != null) {
                    try {
                        SportParser sportParer = new SportParser();
                        ArrayList<Sport> sport = sportParer.getSport(res);

                        if (sport != null && !sport.isEmpty()) {
                            allList.addAll(sport);
                        }
                        AppContext appContext=(AppContext)getApplicationContext();
                        appContext.setSports(allList);
                        sportAllAdapter = new SportAllAdapter(HuoDongActivity.this, allList,handler);
                        aListView.setAdapter(sportAllAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, nrc);

        sportAllAdapter = new SportAllAdapter(HuoDongActivity.this, allList, handler);
        aListView.setAdapter(sportAllAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PUBLISH_HUODONG && resultCode == PUBLISH_SUCCESS) {
            //重新向服务器加载活动列表
            Log.v("TAG", "发布成功，正在更新运动信息");
            initAllSport();
        }
    }

    private void joinSport(int sportID) {
        //把发布信息传到服务器
        AppContext appContext=(AppContext) getApplicationContext();
        User user=appContext.getUser();
        if(user!=null) {
            NetRequestConstant nrc = new NetRequestConstant();
            // post请求
            nrc.setType(HttpRequestType.POST);
            NetRequestConstant.requestUrl = NetUrlConstant.JOIN_SPORT;
            NetRequestConstant.context = this;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sportID", String.valueOf(sportID));
            map.put("userID", user.getUsername());
            NetRequestConstant.map = map;

            getServer(new Netcallback() {
                public void preccess(Object res, boolean flag) {
                    if (res != null) {
                        try {
                            JSONObject object = new JSONObject((String) res);
                            String success = object.optString("success");
                            if (success.equals("1")) {
                                  initAllSport(); //再次更新运动信息
                                Toast.makeText(HuoDongActivity.this, "参加成功！",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HuoDongActivity.this, "参加失败！",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }, nrc);
    }
    }


}

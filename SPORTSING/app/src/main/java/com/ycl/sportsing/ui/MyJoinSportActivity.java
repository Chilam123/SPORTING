package com.ycl.sportsing.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
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
import com.ycl.sportsing.parser.SportParser;
import com.ycl.sportsing.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyJoinSportActivity extends BaseActivity {

    public static ArrayList<Sport> allList = new ArrayList<Sport>();
    @Bind(R.id.imageview_back)
    ImageView imageviewBack;
    private SportAllAdapter sportAllAdapter;
    public ListView aListView;
    private static final int CHANGE_JOIN_STATE = 10;

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_JOIN_STATE:
                    int sportID = (int) msg.obj;
                    Logger.i("我收到了要改变参加状态信息,参加活动ID：" + sportID);
                    //接下来向服务器同步活动信息
                    joinSport(sportID);
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
        setContentView(R.layout.activity_my_join);
        ButterKnife.bind(this);
        initSportsListView();
        imageviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyJoinSportActivity.this.finish();
            }
        });

    }

    @Override
    void init() {
    }


    private void initSportsListView() {
        aListView = (ListView) findViewById(R.id.listview_myjoinsport);

        initAllSport();

    }

    private void initAllSport() {
        NetRequestConstant nrc = new NetRequestConstant();
        NetRequestConstant.requestUrl = NetUrlConstant.MYJOINSPORTURL;
        NetRequestConstant.context = this;
        nrc.setType(HttpRequestType.POST);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_Name",((AppContext)getApplicationContext()).getUser().getUsername());
        NetRequestConstant.map = map;
        getServer(new Netcallback() {

            public void preccess(Object res, boolean flag) {

                if (res != null) {
                    try {
                        SportParser sportParer = new SportParser();
                        ArrayList<Sport> sport = sportParer.getSport(res);

                        if (sport != null && !sport.isEmpty()) {
                            allList.addAll(sport);
                        }
                        AppContext appContext = (AppContext) getApplicationContext();
                        appContext.setSports(allList);
                        sportAllAdapter = new SportAllAdapter(MyJoinSportActivity.this, allList, handler);
                        aListView.setAdapter(sportAllAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, nrc);

        sportAllAdapter = new SportAllAdapter(MyJoinSportActivity.this, allList, handler);
        aListView.setAdapter(sportAllAdapter);

    }

    private void joinSport(int sportID) {
        //把发布信息传到服务器
        AppContext appContext = (AppContext) getApplicationContext();
        User user = appContext.getUser();
        if (user != null) {
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
                                Toast.makeText(MyJoinSportActivity.this, "参加成功！",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MyJoinSportActivity.this, "参加失败！",
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


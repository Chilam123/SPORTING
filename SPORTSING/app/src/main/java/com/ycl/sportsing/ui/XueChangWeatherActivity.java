package com.ycl.sportsing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ycl.sportsing.R;
import com.ycl.sportsing.adapter.XueChangWeatherAdapter;
import com.ycl.sportsing.app.AppContext;
import com.ycl.sportsing.common.NetRequestConstant;
import com.ycl.sportsing.common.NetUrlConstant;
import com.ycl.sportsing.domain.Weather;
import com.ycl.sportsing.interfaces.Netcallback;
import com.ycl.sportsing.parser.WeatherParser;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class XueChangWeatherActivity extends BaseActivity {


    @Bind(R.id.imageview_back)
    ImageView imageviewBack;
    @Bind(R.id.listview_weather)
    ListView listviewWeather;
    @Bind(R.id.addweather_layout)
    RelativeLayout addweatherLayout;

    private XueChangWeatherAdapter xueChangWeatherAdapter;
    private ArrayList<Weather> allList = new ArrayList<>();//从服务器返回的所有天气
    private ArrayList<Weather> myList = new ArrayList<>();//我自己添加的天气
    private ListView listView;

    @Override
    void init() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xue_chang_weather);
        ButterKnife.bind(this);

        imageviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XueChangWeatherActivity.this.finish();
            }
        });

        addweatherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里添加天气事件
                Log.v("TAG", "添加事件");
                Intent intent = new Intent(XueChangWeatherActivity.this, SelectWeatherActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("weathers", allList);
                intent.putExtra("bundle", bundle);
                startActivityForResult(intent, 0);

            }
        });


//        //下面是模拟服务器返回的所有天气
//        Weather weather=new Weather();
//        weather.setXuechang("怀柔滑雪场");
//        weather.setImageweather("qing");
//        weather.setJianshuiliang("0.0mm");
//        weather.setTianqi("晴");
//        weather.setWendu("23℃");
//        allList.add(weather);
//
//        Weather weather1=new Weather();
//        weather1.setXuechang("南山滑雪场");
//        weather1.setImageweather("duoyun");
//        weather1.setJianshuiliang("0.0mm");
//        weather1.setTianqi("多云");
//        weather1.setWendu("17℃");
//        allList.add(weather1);
//
//        Weather weather12=new Weather();
//        weather12.setXuechang("五棵松滑雪场");
//        weather12.setImageweather("xiayu");
//        weather12.setJianshuiliang("17mm");
//        weather12.setTianqi("下雨");
//        weather12.setWendu("12℃");
//        allList.add(weather12);
//
//        Weather weather14=new Weather();
//        weather14.setXuechang("河北滑雪场");
//        weather14.setImageweather("duoyun");
//        weather14.setJianshuiliang("0.0mm");
//        weather14.setTianqi("多云");
//        weather14.setWendu("15℃");
//        allList.add(weather14);
//
//        Weather weather41=new Weather();
//        weather41.setXuechang("万科滑雪场");
//        weather41.setImageweather("xiayu");
//        weather41.setJianshuiliang("13mm");
//        weather41.setTianqi("下雨");
//        weather41.setWendu("10℃");
//        allList.add(weather41);
//
//        Weather weather144=new Weather();
//        weather144.setXuechang("密苑滑雪场");
//        weather144.setImageweather("qing");
//        weather144.setJianshuiliang("0.0mm");
//        weather144.setTianqi("晴");
//        weather144.setWendu("23℃");
//        allList.add(weather144);
//
//        Weather weather145=new Weather();
//        weather145.setXuechang("亚布力滑雪场");
//        weather145.setImageweather("xiayu");
//        weather145.setJianshuiliang("23mm");
//        weather145.setTianqi("下雨");
//        weather145.setWendu("11℃");
//        allList.add(weather145);
//
//        Weather weather1454=new Weather();
//        weather1454.setXuechang("万龙滑雪场");
//        weather1454.setImageweather("duoyun");
//        weather1454.setJianshuiliang("0.0mm");
//        weather1454.setTianqi("多云");
//        weather1454.setWendu("16℃");
//        allList.add(weather1454);
//
//        Weather weather1546=new Weather();
//        weather1546.setXuechang("军都山滑雪场");
//        weather1546.setImageweather("duoyun");
//        weather1546.setJianshuiliang("0.0mm");
//        weather1546.setTianqi("多云");
//        weather1546.setWendu("17℃");
//        allList.add(weather1546);
//
//        Weather weather2=new Weather();
//        weather2.setXuechang("渔阳滑雪场");
//        weather2.setImageweather("xiayu");
//        weather2.setJianshuiliang("14mm");
//        weather2.setTianqi("下雨");
//        weather2.setWendu("13℃");
//        allList.add(weather2);
//
//        Weather weather3=new Weather();
//        weather3.setXuechang("多乐美滑雪场");
//        weather3.setImageweather("xiayu");
//        weather3.setJianshuiliang("20mm");
//        weather3.setTianqi("下雨");
//        weather3.setWendu("15℃");
//        allList.add(weather3);

        AppContext appContext = (AppContext) getApplicationContext();
        myList = appContext.getMyList();
        xueChangWeatherAdapter = new XueChangWeatherAdapter(XueChangWeatherActivity.this, myList);
        listView = (ListView) this.findViewById(R.id.listview_weather);
        listView.setAdapter(xueChangWeatherAdapter);

        if (myList.size() == 0) {
            NetRequestConstant nrc = new NetRequestConstant();
            NetRequestConstant.requestUrl = NetUrlConstant.WEATHERURL;
            NetRequestConstant.context = this;
            nrc.setType(BaseActivity.HttpRequestType.GET);
            getServer(new Netcallback() {

                public void preccess(Object res, boolean flag) {

                    if (res != null) {
                        try {
                            WeatherParser weatherParser = new WeatherParser();
                            ArrayList<Weather> weathers = weatherParser.getWeather(res);

                            if (weathers != null && !weathers.isEmpty()) {
                                allList.clear();//把原来的清空掉
                                allList.addAll(weathers);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, nrc);
        }else{
            //退出这页面后，再点击进去，并且之前有选择过用户的天气
            allList.clear();//清空
            allList=appContext.getAllList();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 0:  //按了返回键，不处理
                break;
            case 1: //选择了某个天气
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    myList.add(allList.get(position));
                    allList.remove(position);
                    xueChangWeatherAdapter.setData(myList);
                    AppContext appContext = (AppContext) getApplicationContext();
                    appContext.setMyList(myList);
                    appContext.setAllList(allList);
                }
        }
    }
}

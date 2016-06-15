package com.ycl.sportsing.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.ycl.sportsing.R;
import com.ycl.sportsing.adapter.DongTaiAdapter;
import com.ycl.sportsing.common.NetRequestConstant;
import com.ycl.sportsing.common.NetUrlConstant;
import com.ycl.sportsing.domain.DongTai;
import com.ycl.sportsing.interfaces.Netcallback;
import com.ycl.sportsing.parser.DongTaiParser;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DongTaiActivity extends BaseActivity {

    @Bind(R.id.imageview_back)
    ImageView imageviewBack;
    private ListView aListView;
    public ArrayList<DongTai> allList = new ArrayList<DongTai>();
    private DongTaiAdapter dongTaiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_tai);
        ButterKnife.bind(this);

        initSecondHandListView();

        imageviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DongTaiActivity.this.finish();
            }
        });
    }

    @Override
    void init() {
    }

    private void initSecondHandListView() {

        aListView = (ListView) this.findViewById(R.id.listview_dongtai);
        NetRequestConstant nrc = new NetRequestConstant();
        NetRequestConstant.requestUrl = NetUrlConstant.DONGTAIURL;
        NetRequestConstant.context = this;
        nrc.setType(BaseActivity.HttpRequestType.GET);
        getServer(new Netcallback() {

            public void preccess(Object res, boolean flag) {

                if (res != null) {
                    try {
                        DongTaiParser dongTaiParser = new DongTaiParser();
                        ArrayList<DongTai> dongTais = dongTaiParser.getDongTai(res);

                        if (dongTais != null && !dongTais.isEmpty()) {
                            allList.addAll(dongTais);

                        }
                        dongTaiAdapter = new DongTaiAdapter(DongTaiActivity.this, allList);
                        aListView.setAdapter(dongTaiAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, nrc);


//        //模拟服务器动态信息对象
//        DongTai dongTai = new DongTai();
//        dongTai.setDongtai_details("今天去南山滑雪场滑雪了，好开心！");
//        dongTai.setPublisher_id("Chilam");
//        dongTai.setDongtai_id(15204);
//        dongTai.setDongtai_picture("http://news.fjsen.com/images/attachement/jpg/site2/20160205/00e06212dca7181e421558.jpg");
//        allList.add(dongTai);
//
//        DongTai dongTai1 = new DongTai();
//        dongTai1.setDongtai_details("今天滑雪碰到了一枚帅哥，好激动啊！！！！！！");
//        dongTai1.setPublisher_id("Chilam");
//        dongTai1.setDongtai_id(1504);
//        dongTai1.setDongtai_picture("http://i2.sinaimg.cn/travel/2012/1218/U7116P704DT20121218103905.jpg");
//        allList.add(dongTai1);
//
//        DongTai dongTai2 = new DongTai();
//        dongTai2.setDongtai_details("第二次去滑雪，求偶遇~~~");
//        dongTai2.setPublisher_id("Chilam");
//        dongTai2.setDongtai_id(1544);
//        dongTai2.setDongtai_picture("http://heilongjiang.sinaimg.cn/2013/0225/U7398P1274DT20130225101837.jpg");
//        allList.add(dongTai2);
//
//        dongTaiAdapter = new DongTaiAdapter(DongTaiActivity.this, allList);
//        aListView = (ListView) this.findViewById(R.id.listview_dongtai);
//        aListView.setAdapter(dongTaiAdapter);
    }

}

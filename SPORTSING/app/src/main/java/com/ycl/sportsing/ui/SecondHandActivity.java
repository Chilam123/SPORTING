package com.ycl.sportsing.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.ycl.sportsing.R;
import com.ycl.sportsing.adapter.SecondHandAdapter;
import com.ycl.sportsing.common.NetRequestConstant;
import com.ycl.sportsing.common.NetUrlConstant;
import com.ycl.sportsing.domain.SecondHand;
import com.ycl.sportsing.interfaces.Netcallback;
import com.ycl.sportsing.parser.SecondHandParser;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SecondHandActivity extends BaseActivity {

    @Bind(R.id.imageview_back)
    ImageView imageviewBack;
    private ListView aListView;
    public static ArrayList<SecondHand> allList = new ArrayList<SecondHand>();
    private SecondHandAdapter secondHandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_hand);
        ButterKnife.bind(this);

        initSecondHandListView();

        imageviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondHandActivity.this.finish();
            }
        });
    }

    @Override
    void init() {
    }

    private void initSecondHandListView() {

        aListView = (ListView) this.findViewById(R.id.listview_secondhand);
        NetRequestConstant nrc = new NetRequestConstant();
        NetRequestConstant.requestUrl = NetUrlConstant.SECONDHANDURL;
        NetRequestConstant.context = this;
        nrc.setType(BaseActivity.HttpRequestType.GET);
        getServer(new Netcallback() {

            public void preccess(Object res, boolean flag) {

                if (res != null) {
                    try {
                        SecondHandParser secondHandParser = new SecondHandParser();
                        ArrayList<SecondHand> secondHands = secondHandParser.getSecondHand(res);

                        if (secondHands != null && !secondHands.isEmpty()) {
                            allList.addAll(secondHands);

                        }
                        secondHandAdapter = new SecondHandAdapter(SecondHandActivity.this, allList);
                        aListView.setAdapter(secondHandAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }, nrc);


//        //模拟服务器二手信息对象
//        SecondHand secondHand = new SecondHand();
//        secondHand.setSecondhand_title("卖二手单板啦~~~");
//        secondHand.setSecondhand_price("150元");
//        secondHand.setSecondhand_details("这是我在15年10月买的单板，没用过几次，现在还很新，现在5折转让出去，有兴趣的朋友请联系我");
//        secondHand.setPublisher_id("Chilam");
//        secondHand.setSecondhand_id(15204);
//        secondHand.setSecondhand_picture("http://3.pic.58control.cn/p1/big/n_s12263392886228238035.jpg");
//        allList.add(secondHand);
//
//        secondHandAdapter = new SecondHandAdapter(SecondHandActivity.this, allList);
//        aListView = (ListView) this.findViewById(R.id.listview_secondhand);
//        aListView.setAdapter(secondHandAdapter);
    }

}

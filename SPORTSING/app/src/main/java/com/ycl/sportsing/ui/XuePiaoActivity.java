package com.ycl.sportsing.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.ycl.sportsing.R;
import com.ycl.sportsing.adapter.XuePiaoAdapter;
import com.ycl.sportsing.domain.XuePiao;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class XuePiaoActivity extends Activity implements View.OnClickListener {

    public ArrayList<XuePiao> allList = new ArrayList<>();
    @Bind(R.id.imageview_back)
    ImageView imageviewBack;
    private XuePiaoAdapter xuePiaoAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xue_piao);
        ButterKnife.bind(this);
        imageviewBack.setOnClickListener(this);

        XuePiao nanshan = new XuePiao();
        nanshan.setPrice("180元");
        nanshan.setXuechang("南山滑雪场");
        allList.add(nanshan);

        XuePiao jundushan = new XuePiao();
        jundushan.setPrice("230元");
        jundushan.setXuechang("军都山滑雪场");
        allList.add(jundushan);

        XuePiao wanlong = new XuePiao();
        wanlong.setPrice("250元");
        wanlong.setXuechang("万龙滑雪场");
        allList.add(wanlong);

        XuePiao yunfoshan = new XuePiao();
        yunfoshan.setPrice("180元");
        yunfoshan.setXuechang("云佛山滑雪场");
        allList.add(yunfoshan);

        XuePiao yunding = new XuePiao();
        yunding.setPrice("320元");
        yunding.setXuechang("云顶滑雪场");
        allList.add(yunding);

        XuePiao yabuli = new XuePiao();
        yabuli.setPrice("280元");
        yabuli.setXuechang("亚布力滑雪场");
        allList.add(yabuli);


        XuePiao badaling = new XuePiao();
        badaling.setPrice("300元");
        badaling.setXuechang("八达岭滑雪场");
        allList.add(badaling);

        XuePiao beida = new XuePiao();
        beida.setPrice("150元");
        beida.setXuechang("北大壶滑雪场");
        allList.add(beida);


        XuePiao beida12345679801 = new XuePiao();
        beida12345679801.setPrice("170元");
        beida12345679801.setXuechang("哈尔滨滑雪场");
        allList.add(beida12345679801);


        XuePiao beida1234567980 = new XuePiao();
        beida1234567980.setPrice("200元");
        beida1234567980.setXuechang("河北滑雪场");
        allList.add(beida1234567980);


        XuePiao beida123456798 = new XuePiao();
        beida123456798.setPrice("120元");
        beida123456798.setXuechang("多乐美滑雪场");
        allList.add(beida123456798);

        XuePiao beida12345679 = new XuePiao();
        beida12345679.setPrice("170元");
        beida12345679.setXuechang("美高滑雪场");
        allList.add(beida12345679);


        XuePiao beida1234567 = new XuePiao();
        beida1234567.setPrice("130元");
        beida1234567.setXuechang("长隆滑雪场");
        allList.add(beida1234567);

        XuePiao beida123456 = new XuePiao();
        beida123456.setPrice("150元");
        beida123456.setXuechang("万达滑雪场");
        allList.add(beida123456);

        XuePiao beida12345 = new XuePiao();
        beida12345.setPrice("195元");
        beida12345.setXuechang("万科滑雪场");
        allList.add(beida12345);

        XuePiao beida1234 = new XuePiao();
        beida1234.setPrice("225元");
        beida1234.setXuechang("真龙滑雪场");
        allList.add(beida1234);

        XuePiao beida123 = new XuePiao();
        beida123.setPrice("180元");
        beida123.setXuechang("人人滑雪场");
        allList.add(beida123);

        XuePiao beida12 = new XuePiao();
        beida12.setPrice("250元");
        beida12.setXuechang("五棵松滑雪场");
        allList.add(beida12);

        Log.v("TAG", allList.size() + "长度");

        xuePiaoAdapter = new XuePiaoAdapter(XuePiaoActivity.this, allList);
        listView = (ListView) this.findViewById(R.id.listview_xuepiao);
        listView.setAdapter(xuePiaoAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_back:
                XuePiaoActivity.this.finish();
                break;

            default:
                break;
        }

    }
}

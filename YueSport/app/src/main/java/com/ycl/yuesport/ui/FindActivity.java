package com.ycl.yuesport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.ycl.yuesport.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.relativelayout_jianshenquan_find)
    RelativeLayout relativelayoutJianshenquan;
    @Bind(R.id.relativelayout_zhuanka_find)
    RelativeLayout relativelayoutZhuanka;
    @Bind(R.id.relativelayout_instalment_pay_find)
    RelativeLayout relativelayoutInstalmentPay;
    @Bind(R.id.relativelayout_nearby_people_find)
    RelativeLayout relativelayoutNearbyPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        ButterKnife.bind(this);

        initToolBar();
        addListener();

    }

    /**
     * 初始化ToolBar
     */
    void initToolBar() {
        myToolbar.setTitle("发现");
        setSupportActionBar(myToolbar);
    }

    /**
     * 添加监听事件
     */
    private void addListener() {
        relativelayoutJianshenquan.setOnClickListener(this);
        relativelayoutZhuanka.setOnClickListener(this);
        relativelayoutInstalmentPay.setOnClickListener(this);
        relativelayoutNearbyPeople.setOnClickListener(this);
    }

    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            //待处理.....................................................................
            case R.id.relativelayout_jianshenquan_find:
                intent = new Intent(FindActivity.this, Test2Activity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_zhuanka_find:
                intent = new Intent(FindActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_instalment_pay_find:
                intent = new Intent(FindActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_nearby_people_find:
                intent = new Intent(FindActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

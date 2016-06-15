package com.ycl.sportsing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.sportsing.R;
import com.ycl.sportsing.domain.XueChang;

import butterknife.Bind;
import butterknife.ButterKnife;

public class XuechangActivity extends AppCompatActivity {


    @Bind(R.id.text_title_xuechang)
    TextView textTitleXuechang;
    @Bind(R.id.text_details_xuechang)
    TextView textDetailsXuechang;
    @Bind(R.id.text_location_xuechang)
    TextView textLocationXuechang;
    @Bind(R.id.text_transportation_xuechang)
    TextView textTransportationXuechang;
    @Bind(R.id.text_price_xuechang)
    TextView textPriceXuechang;
    @Bind(R.id.image_xuechang)
    ImageView imageXuechang;
    @Bind(R.id.text_phone_xuechang)
    TextView textPhoneXuechang;
    @Bind(R.id.imageview_back)
    ImageView imageviewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuechang);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        XueChang xueChang = (XueChang) bundle.getSerializable("xuechang");

        textTitleXuechang.setText(xueChang.getTitle());
        textDetailsXuechang.setText(xueChang.getDetails());
        textLocationXuechang.setText(xueChang.getLocation());
        textTransportationXuechang.setText(xueChang.getTransportation());
        textPriceXuechang.setText(xueChang.getPrice());
        textPhoneXuechang.setText(xueChang.getPhone());
        imageviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XuechangActivity.this.finish();
            }
        });
    }
}

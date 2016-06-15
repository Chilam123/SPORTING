package com.ycl.sportsing.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.ycl.sportsing.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShipinActivity extends AppCompatActivity {

    @Bind(R.id.imageview_back)
    ImageView imageviewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipin);
        ButterKnife.bind(this);

        imageviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShipinActivity.this.finish();
            }
        });

    }
}

package com.ycl.sportsing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ycl.sportsing.R;
import com.ycl.sportsing.adapter.SelectWeatherAdapter;
import com.ycl.sportsing.domain.Weather;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectWeatherActivity extends AppCompatActivity {

    @Bind(R.id.imageview_back)
    ImageView imageviewBack;
    @Bind(R.id.select_weather_listview)
    ListView selectWeatherListview;

    private ArrayList<Weather> allList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_weather);
        ButterKnife.bind(this);

        imageviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });


        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        allList=bundle.getParcelableArrayList("weathers");

        SelectWeatherAdapter selectWeatherAdapter=new SelectWeatherAdapter(this,allList);
        selectWeatherListview.setAdapter(selectWeatherAdapter);

        selectWeatherListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2=new Intent();
                intent2.putExtra("position",position);
                setResult(1,intent2);
                finish();
            }
        });


    }
}

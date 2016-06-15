package com.ycl.sportsing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ycl.sportsing.R;

public class NewBieGuideActivity extends Activity implements View.OnClickListener {

    private Intent intent;
    private ImageView imageViewBack,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bie_guide);


        imageViewBack=(ImageView)this.findViewById(R.id.imageview_back);
        imageView1=(ImageView)this.findViewById(R.id.ImageView1);
        imageView2=(ImageView)this.findViewById(R.id.ImageView2);
        imageView3=(ImageView)this.findViewById(R.id.ImageView3);
        imageView4=(ImageView)this.findViewById(R.id.ImageView4);
        imageView5=(ImageView)this.findViewById(R.id.ImageView5);
        imageView6=(ImageView)this.findViewById(R.id.ImageView6);
        imageView7=(ImageView)this.findViewById(R.id.ImageView7);
        imageView8=(ImageView)this.findViewById(R.id.ImageView8);
        imageViewBack.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        imageView7.setOnClickListener(this);
        imageView8.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imageview_back:
                NewBieGuideActivity.this.finish();
                break;
            case R.id.ImageView1:
                intent=new Intent(NewBieGuideActivity.this, WebViewActivity.class);
                intent.putExtra("title","滑雪新手指南");
                intent.putExtra("webUrl","http://xw.qq.com/js/20141121048059");
                startActivity(intent);
                break;
            case R.id.ImageView2:
                intent=new Intent(NewBieGuideActivity.this, WebViewActivity.class);
                intent.putExtra("title","滑雪新手指南");
                intent.putExtra("webUrl","http://zhidao.baidu.com/question/1575246277251451260.html");
                startActivity(intent);
                break;
            case R.id.ImageView3:
                intent=new Intent(NewBieGuideActivity.this, WebViewActivity.class);
                intent.putExtra("title","滑雪新手指南");
                intent.putExtra("webUrl","http://jingyan.baidu.com/article/2a1383286d22c2074a134fbe.html");
                startActivity(intent);
                break;
            case R.id.ImageView4:
                intent=new Intent(NewBieGuideActivity.this, WebViewActivity.class);
                intent.putExtra("title","滑雪新手指南");
                intent.putExtra("webUrl","http://m.bbaqw.com/cs/6813.htm");
                startActivity(intent);
                break;
            case R.id.ImageView5:
                intent=new Intent(NewBieGuideActivity.this, WebViewActivity.class);
                intent.putExtra("title","滑雪新手指南");
                intent.putExtra("webUrl","http://jingyan.baidu.com/article/c1465413aa85bd0bfcfc4c98.html");
                startActivity(intent);
                break;
            case R.id.ImageView6:
                intent=new Intent(NewBieGuideActivity.this, WebViewActivity.class);
                intent.putExtra("title","滑雪新手指南");
                intent.putExtra("webUrl","http://jingyan.baidu.com/article/afd8f4de4e26a734e286e9dc.html");
                startActivity(intent);
                break;
            case R.id.ImageView7:
                intent=new Intent(NewBieGuideActivity.this, WebViewActivity.class);
                intent.putExtra("title","滑雪新手指南");
                intent.putExtra("webUrl","http://m.8264.com/viewnews-72315-page-1.html");
                startActivity(intent);
                break;
            case R.id.ImageView8:
                intent=new Intent(NewBieGuideActivity.this, WebViewActivity.class);
                intent.putExtra("title","滑雪新手指南");
                intent.putExtra("webUrl","http://jingyan.baidu.com/article/9f7e7ec0779aee6f281554eb.html");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

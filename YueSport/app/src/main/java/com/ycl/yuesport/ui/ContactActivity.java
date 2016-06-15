package com.ycl.yuesport.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.ycl.yuesport.R;
import com.ycl.yuesport.http.CallServer;
import com.ycl.yuesport.http.HttpListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;
import com.yolanda.nohttp.cache.CacheMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactActivity extends AppCompatActivity implements HttpListener {

    @Bind(R.id.test)
    ImageView test;
    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        Request<Bitmap> request = NoHttp.createImageRequest("http://img02.taobaocdn.com/imgextra/i2/686412666/TB23awZaFXXXXaOXXXXXXXXXXXX_!!686412666-0-fleamarket.jpg_728x728.jpg", RequestMethod.GET);
        request.setCacheMode(CacheMode.IF_NONE_CACHE_REQUEST);
        CallServer.getRequestInstance().add(this, 0, request, this, false, true);

        initToolBar();

        Log.v("TAG", "是否来自缓123" );


    }

    /**
     * 初始化ToolBar
     */
    void initToolBar() {
        myToolbar.setTitle("消息");
        setSupportActionBar(myToolbar);
    }

    @Override
    public void onSucceed(int what, Response response) {
        Bitmap bitmap = (Bitmap) response.get();
        Log.v("TAG", "是否来自缓存" + response.isFromCache() + "相应时间" + response.getNetworkMillis() + "请求头" + response.getHeaders());

        test.setImageBitmap(bitmap);
    }


    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

    }
}

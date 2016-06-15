package com.ycl.yuesport.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycl.yuesport.R;
import com.ycl.yuesport.config.AppConfig;
import com.ycl.yuesport.http.CallServer;
import com.ycl.yuesport.http.HttpListener;
import com.ycl.yuesport.http.NetRequestConstant;
import com.ycl.yuesport.utils.IntentConstant;
import com.ycl.yuesport.utils.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;
import com.yolanda.nohttp.cache.CacheMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MineActivity extends AppCompatActivity implements View.OnClickListener, HttpListener {

    @Bind(R.id.relativelayout_account_mine)
    RelativeLayout relativelayoutAccountMine;
    @Bind(R.id.relativelayout_yue_mine)
    RelativeLayout relativelayoutYueMine;
    @Bind(R.id.relativelayout_album_mine)
    RelativeLayout relativelayoutAlbumMine;
    @Bind(R.id.relativelayout_my_wallet_mine)
    RelativeLayout relativelayoutWalletMine;
    @Bind(R.id.relativelayout_my_zhuanka_mine)
    RelativeLayout relativelayoutZhuankaMine;
    @Bind(R.id.relativelayout_settings_mine)
    RelativeLayout relativelayoutSettingsMine;
    @Bind(R.id.image_ic_erweima_mine)
    ImageView imageIcErweimaMine;
    @Bind(R.id.image_user_touxiang_mine)
    ImageView imageUserTouxiangMine;
    @Bind(R.id.text_user_name_mine)
    TextView textUserNameMine;
    @Bind(R.id.text_user_yueaccount_mine)
    TextView textUserYueaccountMine;
    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.bind(this);

        initToolBar();
        addListener();

    }

    /**
     * 初始化ToolBar
     */
    void initToolBar() {
        myToolbar.setTitle("个人");
        setSupportActionBar(myToolbar);
    }

    /**
     * 添加监听事件
     */
    private void addListener() {
        imageIcErweimaMine.setOnClickListener(this);
        relativelayoutAccountMine.setOnClickListener(this);
        relativelayoutYueMine.setOnClickListener(this);
        relativelayoutAlbumMine.setOnClickListener(this);
        relativelayoutWalletMine.setOnClickListener(this);
        relativelayoutZhuankaMine.setOnClickListener(this);
        relativelayoutSettingsMine.setOnClickListener(this);
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            //待处理.....................................................................
            case R.id.image_ic_erweima_mine:      //二维码，弹出二维码的dialog
                intent = new Intent(MineActivity.this, Test2Activity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_account_mine:
                intent = new Intent(MineActivity.this, AccountDetailsActivity.class);
                startActivityForResult(intent, IntentConstant.ACCOUNT_DETAILS);
                break;
            case R.id.relativelayout_yue_mine:
                intent = new Intent(MineActivity.this, MyYueActivity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_album_mine:
                intent = new Intent(MineActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_my_wallet_mine:
                intent = new Intent(MineActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_my_zhuanka_mine:
                intent = new Intent(MineActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_settings_mine:
                intent = new Intent(MineActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //有修改用户头像/昵称/约账号，进行页面更新
        if (requestCode == IntentConstant.ACCOUNT_DETAILS && resultCode == IntentConstant.HAS_CHANGE_DETAILS) {
            if(data.getBooleanExtra("changeNickname",false)) {
                //用户修改了昵称
                textUserNameMine.setText(AppConfig.getInstance().getString("userNickname", ""));
            }
            if(data.getBooleanExtra("changeTouXiang",false)){
                //用户修改了头像
                //向网络请求用户头像
                Request<Bitmap> requestUserTouxiang = NoHttp.createImageRequest(AppConfig.getInstance().getString("userTouxiang", "未知"), RequestMethod.GET);
                requestUserTouxiang.setCacheMode(CacheMode.IF_NONE_CACHE_REQUEST);
                CallServer.getRequestInstance().add(this, NetRequestConstant.REQUEST_USER_TOUXIANG, requestUserTouxiang, this, false, false);
            }
            if(data.getBooleanExtra("changeYueAccount",false)){
                //用户修改了约健身号
                textUserYueaccountMine.setText(AppConfig.getInstance().getString("userYueAccount", ""));
            }
        }
    }

    @Override
    public void onSucceed(int what, Response response) {

        if (what == NetRequestConstant.REQUEST_USER_TOUXIANG) {
            Logger.d("请求用户头像成功");

            //对头像大小等进行处理，待处理............................................................................
            Bitmap userTouxiang = (Bitmap) response.get();
            imageUserTouxiangMine.setImageBitmap(userTouxiang);
        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

        if (what == NetRequestConstant.REQUEST_USER_TOUXIANG) {
            //网络请求失败时设置系统默认头像
            imageUserTouxiangMine.setImageResource(R.mipmap.user_default_touxiang);
        }
    }
}

package com.ycl.yuesport.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
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

public class AccountDetailsActivity extends BaseActivity implements View.OnClickListener, HttpListener {

    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.relativelayout_touxiang_account_details)
    RelativeLayout relativelayoutTouxiangAccountDetails;
    @Bind(R.id.relativelayout_nickname_account_details)
    RelativeLayout relativelayoutNicknameAccountDetails;
    @Bind(R.id.relativelayout_yueaccount_account_details)
    RelativeLayout relativelayoutYueaccountAccountDetails;
    @Bind(R.id.relativelayout_phone_account_details)
    RelativeLayout relativelayoutPhoneAccountDetails;
    @Bind(R.id.relativelayout_sex_account_details)
    RelativeLayout relativelayoutSexAccountDetails;
    @Bind(R.id.relativelayout_age_account_details)
    RelativeLayout relativelayoutAgeAccountDetails;
    @Bind(R.id.relativelayout_tag_account_details)
    RelativeLayout relativelayoutTagAccountDetails;
    @Bind(R.id.relativelayout_cartstate_account_details)
    RelativeLayout relativelayoutCartstateAccountDetails;
    @Bind(R.id.relativelayout_keepfitarea_account_details)
    RelativeLayout relativelayoutKeepfitareaAccountDetails;
    @Bind(R.id.relativelayout_friendspurpose_account_details)
    RelativeLayout relativelayoutFriendspurposeAccountDetails;
    @Bind(R.id.image_user_touxiang_account_details)
    ImageView imageUserTouxiang;
    @Bind(R.id.text_nickname_account_details)
    TextView textNickname;
    @Bind(R.id.text_yueaccount_account_details)
    TextView textYueaccount;
    @Bind(R.id.text_phone_account_details)
    TextView textPhone;
    @Bind(R.id.text_sex_account_details)
    TextView textSex;
    @Bind(R.id.text_age_account_details)
    TextView textAge;
    @Bind(R.id.text_tag_account_details)
    TextView textTag;
    @Bind(R.id.text_cartstate_account_details)
    TextView textCartstate;
    @Bind(R.id.text_keepfitarea_account_details)
    TextView textKeepfitarea;
    @Bind(R.id.text_friendspurpose_account_details)
    TextView textFriendspurpose;

    final String[] tagArray = {"健身小白", "健身学徒", "健身达人", "健身大咖"};
    final String[] sexArray = {"男", "女"};
    final String[] cartArray = {"有卡", "无卡"};
    final String[] areaArray = {"东城区", "西城区", "崇文区", "宣武区", "朝阳区", "海淀区", "丰台区", "石景山区", "门头沟区", "房山区", "通州区", "顺义区", "昌平区", "大兴区", "怀柔区", "平谷区", "延庆县", "密云县"};

    private static boolean hasChangeNickname = false;
    private static boolean hasChangeTouXiang = false;
    private static boolean hasChangeYueAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_details);
        ButterKnife.bind(this);

        initComponent();
        initToolBar();
        addListener();

        Logger.d("创建了用户信息activity");
    }

    /**
     * 初始化组件
     */
    private void initComponent() {
        textNickname.setText(AppConfig.getInstance().getString("userNickname", "未知"));
        textYueaccount.setText(AppConfig.getInstance().getString("userYueAccount", "未知"));
        textPhone.setText(AppConfig.getInstance().getString("userPhone", "未知"));
        textSex.setText(sexArray[AppConfig.getInstance().getInt("userSex", 0)]);
        textAge.setText(String.valueOf(AppConfig.getInstance().getInt("userAge", 18)));
        textTag.setText(tagArray[AppConfig.getInstance().getInt("userTag", 0)]);
        textCartstate.setText(cartArray[AppConfig.getInstance().getInt("userCartState", 1)]);
        textKeepfitarea.setText(areaArray[AppConfig.getInstance().getInt("userKeepfitArea", 5)]);
        textFriendspurpose.setText(AppConfig.getInstance().getString("userFriendsPurpose", "未知"));

        //请求用户头像
        Request<Bitmap> requestUserTouxiang = NoHttp.createImageRequest(AppConfig.getInstance().getString("userTouxiang", "未知"), RequestMethod.GET);
        requestUserTouxiang.setCacheMode(CacheMode.IF_NONE_CACHE_REQUEST);
        CallServer.getRequestInstance().add(this, NetRequestConstant.REQUEST_USER_TOUXIANG, requestUserTouxiang, this, false, false);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        myToolbar.setTitle("个人信息");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 添加监听事件
     */
    private void addListener() {
        relativelayoutTouxiangAccountDetails.setOnClickListener(this);
        relativelayoutNicknameAccountDetails.setOnClickListener(this);
        relativelayoutYueaccountAccountDetails.setOnClickListener(this);
        relativelayoutPhoneAccountDetails.setOnClickListener(this);
        relativelayoutSexAccountDetails.setOnClickListener(this);
        relativelayoutAgeAccountDetails.setOnClickListener(this);
        relativelayoutTagAccountDetails.setOnClickListener(this);
        relativelayoutCartstateAccountDetails.setOnClickListener(this);
        relativelayoutKeepfitareaAccountDetails.setOnClickListener(this);
        relativelayoutFriendspurposeAccountDetails.setOnClickListener(this);
    }

    /**
     * 添加点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            //待处理各跳转事件.....................................................................
            case R.id.relativelayout_touxiang_account_details:
                intent = new Intent(AccountDetailsActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_nickname_account_details:
                intent = new Intent(AccountDetailsActivity.this, ChangeNicknameActivity.class);
                startActivityForResult(intent, IntentConstant.CHANGE_NICKNAME);
                break;
            case R.id.relativelayout_yueaccount_account_details:
                intent = new Intent(AccountDetailsActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            //点击手机号不处理，在设置里面才能修改手机号
//            case R.id.relativelayout_phone_account_details:
//                intent = new Intent(AccountDetailsActivity.this, TestActivity.class);
//                startActivity(intent);
//                break;
            case R.id.relativelayout_sex_account_details:
                //获取用户性别
                int mySex = AppConfig.getInstance().getInt("userSex", 0);

                //创建选择性别窗口
                new AlertDialog.Builder(this).setTitle("性别").setSingleChoiceItems(sexArray, mySex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //同步到服务器+返回成功写入sp+更新界面,待处理..............................................
                        //暂时写入sp，测试
                        AppConfig.getInstance().putInt("userSex", i);
                        //更新界面,测试
                        textSex.setText(sexArray[i]);

                        dialogInterface.dismiss();
                    }
                }).show();
                break;
            case R.id.relativelayout_tag_account_details:
                //获取用户标签
                int myTag = AppConfig.getInstance().getInt("userTag", 0);

                //创建选择健身标签窗口
                new AlertDialog.Builder(this).setTitle("健身标签").setSingleChoiceItems(tagArray, myTag, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //同步到服务器+返回成功写入sp+更新界面,待处理..............................................
                        //暂时写入sp，测试
                        AppConfig.getInstance().putInt("userTag", i);
                        //更新界面,测试
                        textTag.setText(tagArray[i]);

                        dialogInterface.dismiss();
                    }
                }).show();
                break;
            case R.id.relativelayout_age_account_details:
                //获取用户年龄
                int myAge = AppConfig.getInstance().getInt("userAge", 0);
                final NumberPicker agePicker = new NumberPicker(this);
                agePicker.setMinValue(0);
                agePicker.setMaxValue(120);
                agePicker.setValue(myAge);
                agePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                //创建选择年龄窗口
                new AlertDialog.Builder(this).setTitle("年龄").setView(agePicker)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int newAge = agePicker.getValue();

                                //同步到服务器+返回成功写入sp+更新界面,待处理..............................................
                                //暂时写入sp，测试
                                AppConfig.getInstance().putInt("userAge", newAge);
                                //更新界面,测试
                                textAge.setText(String.valueOf(newAge));

                                dialogInterface.dismiss();
                            }
                        }).show();
                break;
            case R.id.relativelayout_cartstate_account_details:
                //获取用户持卡状态
                int myCart = AppConfig.getInstance().getInt("userCartState", 0);

                //创建选择健身标签窗口
                new AlertDialog.Builder(this).setTitle("持卡状态").setSingleChoiceItems(cartArray, myCart, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //同步到服务器+返回成功写入sp+更新界面,待处理..............................................
                        //暂时写入sp，测试
                        AppConfig.getInstance().putInt("userCartState", i);
                        //更新界面,测试
                        textCartstate.setText(cartArray[i]);

                        dialogInterface.dismiss();
                    }
                }).show();
                break;
            case R.id.relativelayout_keepfitarea_account_details:
                //获取用户健身区域
                int myArea = AppConfig.getInstance().getInt("userKeepfitArea", 5);
                final NumberPicker areaPicker = new NumberPicker(this);
                areaPicker.setDisplayedValues(areaArray);
                areaPicker.setValue(myArea);
                areaPicker.setMinValue(0);
                areaPicker.setMaxValue(areaArray.length - 1);
                areaPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                //创建选择健身区域窗口
                new AlertDialog.Builder(this).setTitle("健身区域").setView(areaPicker)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int newArea = areaPicker.getValue();

                                //同步到服务器+返回成功写入sp+更新界面,待处理..............................................
                                //暂时写入sp，测试
                                AppConfig.getInstance().putInt("userKeepfitArea", newArea);
                                //更新界面,测试
                                textKeepfitarea.setText(areaArray[newArea]);

                                dialogInterface.dismiss();
                            }
                        }).show();
                break;
            case R.id.relativelayout_friendspurpose_account_details:
                intent = new Intent(AccountDetailsActivity.this, FriendsPurposeActivity.class);
                startActivityForResult(intent, IntentConstant.CHANGE_FRIENDS_PURPOSE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //用户修改昵称成功
        if (requestCode == IntentConstant.CHANGE_NICKNAME && resultCode == IntentConstant.CHANGE_NICKNAME_SUCCESS) {
            textNickname.setText(AppConfig.getInstance().getString("userNickname", ""));
            //设置已修改过昵称
            hasChangeNickname = true;
        }

        //用户修改交友意向成功
        if (requestCode == IntentConstant.CHANGE_FRIENDS_PURPOSE && resultCode == IntentConstant.CHANGE_FRIENDS_PURPOSE_SUCCESS) {
            textFriendspurpose.setText(AppConfig.getInstance().getString("userFriendsPurpose", ""));
        }
    }

    @Override
    public void onSucceed(int what, Response response) {

        if (what == NetRequestConstant.REQUEST_USER_TOUXIANG) {
            Logger.d("请求用户头像成功");

            //对头像大小等进行处理，待处理............................................................................
            Bitmap userTouxiang = (Bitmap) response.get();
            imageUserTouxiang.setImageBitmap(userTouxiang);
        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

        if (what == NetRequestConstant.REQUEST_USER_TOUXIANG) {
            //网络请求失败时设置系统默认头像
            imageUserTouxiang.setImageResource(R.mipmap.user_default_touxiang);
        }
    }

    /**
     * 重写onBackPressed方法，返回时通知MineActivity修改昵称/约账号/头像
     */
    @Override
    public void onBackPressed() {
        if (hasChangeNickname || hasChangeTouXiang || hasChangeYueAccount) {
            Intent intent = new Intent();
            intent.putExtra("changeNickname", hasChangeNickname);
            intent.putExtra("changeTouXiang", hasChangeTouXiang);
            intent.putExtra("changeYueAccount", hasChangeYueAccount);
            setResult(IntentConstant.HAS_CHANGE_DETAILS, intent);
            hasChangeNickname = false;
            hasChangeTouXiang = false;
            hasChangeYueAccount = false;
        } else {
            setResult(IntentConstant.NOT_CHANGE_DETAILS);
        }
        Logger.d("进入onpressedback");
        super.onBackPressed();
    }
}

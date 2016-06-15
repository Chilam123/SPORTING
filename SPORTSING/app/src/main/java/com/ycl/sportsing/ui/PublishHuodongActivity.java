package com.ycl.sportsing.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ycl.sportsing.R;
import com.ycl.sportsing.app.AppContext;
import com.ycl.sportsing.common.NetRequestConstant;
import com.ycl.sportsing.common.NetUrlConstant;
import com.ycl.sportsing.domain.User;
import com.ycl.sportsing.interfaces.Netcallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PublishHuodongActivity extends BaseActivity implements View.OnClickListener {



    @Bind(R.id.imageview_back)
    ImageView imageviewBack;
    @Bind(R.id.image_poster_publish)
    ImageView imagePosterPublish;
    @Bind(R.id.edittext_title_publish)
    EditText edittextTitlePublish;
    @Bind(R.id.spinner_city_publish)
    Spinner spinnerCityPublish;
    @Bind(R.id.spinner_xuechang_publish)
    Spinner spinnerXuechangPublish;
    @Bind(R.id.button_publish)
    Button buttonPublish;
    @Bind(R.id.text_data_publish)
    TextView textDataPublish;

    private static final String[] cityArray={"北京","天津","上海","杭州","西安"};
    private static final String[] xuechangArray={"南山滑雪场","渔阳滑雪场","怀北滑雪场","八达岭滑雪场","军都山滑雪场"};
    private static String city="北京";
    private static String xuechang="南山滑雪场";
    private static String time="2016年5月28日";
    private static final int PUBLISH_SUCCESS=12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_huodong);
        ButterKnife.bind(this);

        imageviewBack.setOnClickListener(this);
        imagePosterPublish.setOnClickListener(this);
        textDataPublish.setOnClickListener(this);
        buttonPublish.setOnClickListener(this);

        ArrayAdapter<String> cityAdapter=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,cityArray);
        spinnerCityPublish.setAdapter(cityAdapter);
        spinnerCityPublish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city=cityArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ArrayAdapter<String> xuechangAdapter=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,xuechangArray);
        spinnerXuechangPublish.setAdapter(xuechangAdapter);
        spinnerXuechangPublish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xuechang=xuechangArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Calendar calendar=Calendar.getInstance();
        time=calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日";
        textDataPublish.setText(time);

    }

    @Override
    void init() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_back:
                PublishHuodongActivity.this.finish();
                break;
            case R.id.image_poster_publish:
                Toast.makeText(this, "现在暂未开通选择活动图片功能哦~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_data_publish:
                pickTime();
                break;
            case R.id.button_publish:
                publishHuodong();
                break;
            default:
                break;
        }
    }

    /**
     * 发布活动
     */
    public void publishHuodong() {
         String title=edittextTitlePublish.getText().toString();
        if(title.equals("")){
            Toast.makeText(this,"标题不能为空哦~",Toast.LENGTH_SHORT).show();
            return;
        }

        //把发布信息传到服务器
        AppContext appContext=(AppContext) getApplicationContext();
        User user=appContext.getUser();
        if(user!=null) {

            //服务器设置sportID,timeToNow

            NetRequestConstant nrc = new NetRequestConstant();
            // post请求
            nrc.setType(HttpRequestType.POST);
            NetRequestConstant.requestUrl = NetUrlConstant.PUBLISH_SPORT;
            NetRequestConstant.context = this;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("publisher_id", user.getUsername());
            map.put("sport_title", title);
            map.put("sport_xuechang", xuechang);
            map.put("sport_city", city);
            map.put("sport_time", time);
            //图片功能先用定义好的
            map.put("sport_picture", "http://img0.imgtn.bdimg.com/it/u=1911760539,2751271597&fm=21&gp=0.jpg");
            NetRequestConstant.map = map;

            getServer(new Netcallback() {
                public void preccess(Object res, boolean flag) {
                    if (res != null) {
                        try {
                            JSONObject object = new JSONObject((String) res);
                            String success = object.optString("success");
                            if (success.equals("1")) {
                                Toast.makeText(PublishHuodongActivity.this, "发布成功！",
                                        Toast.LENGTH_SHORT).show();

                                 setResult(PUBLISH_SUCCESS);
                                finish();
                            } else {
                                Toast.makeText(PublishHuodongActivity.this, "发布失败！",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }, nrc);
        }else
            Toast.makeText(this,"请您先登录哦",Toast.LENGTH_SHORT).show();

//        //单机测试
//                   setResult(PUBLISH_SUCCESS);
//                    finish();
    }

    /**
     * 选择时间
     */
    public void pickTime() {
        final DatePicker datePicker=new DatePicker(this);
        AlertDialog ad=new AlertDialog.Builder(this).setView(datePicker)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        time=datePicker.getYear()+"年"+(datePicker.getMonth()+1)+"月"+datePicker.getDayOfMonth()+"日";
                        textDataPublish.setText(time);
                    }
                }).show();
    }
}

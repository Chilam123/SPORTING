package com.ycl.sportsing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ycl.sportsing.R;
import com.ycl.sportsing.common.NetRequestConstant;
import com.ycl.sportsing.common.NetUrlConstant;
import com.ycl.sportsing.interfaces.Netcallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText edittext_username, edittext_password;
    private Button button_confirm;
    private ImageView imageview_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);
        edittext_username = (EditText) findViewById(R.id.register_userName);
        edittext_password = (EditText) findViewById(R.id.register_userPassword);
        button_confirm = (Button) findViewById(R.id.button_register_confirm);
        imageview_back = (ImageView) findViewById(R.id.imageview_back);

        button_confirm.setOnClickListener(this);
        imageview_back.setOnClickListener(this);

    }

    @Override
    void init() {}

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_back:
                finish();
                break;
            case R.id.button_register_confirm:// 确认注册

                Log.v("TAG","我点击了注册");
                final String username = edittext_username.getText().toString();
                final String password = edittext_password.getText().toString();

                if (!username.equals("") && !password.equals("")) {

                    NetRequestConstant nrc = new NetRequestConstant();
                    // post请求
                    nrc.setType(HttpRequestType.POST);
                    NetRequestConstant.requestUrl = NetUrlConstant.REGISTERURL;
                    NetRequestConstant.context = this;
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("user_Name", username);
                    map.put("user_Password", password);
                    NetRequestConstant.map = map;

                    getServer(new Netcallback() {

                        public void preccess(Object res, boolean flag) {
                            if (res != null) {
                                try {
                                    JSONObject object = new JSONObject((String) res);
                                    String success = object.optString("success");
                                    if (success.equals("1")) {
                                        Intent data = new Intent();
                                        data.putExtra("username", username);
                                        data.putExtra("password", password);
                                        setResult(RESULT_OK, data);
                                        Toast.makeText(RegisterActivity.this,
                                                "注册成功", Toast.LENGTH_LONG)
                                                .show();
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this,
                                                "此用户名已被注册", Toast.LENGTH_LONG)
                                                .show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    }, nrc);

                }else{
                    Toast.makeText(this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }

    }

}

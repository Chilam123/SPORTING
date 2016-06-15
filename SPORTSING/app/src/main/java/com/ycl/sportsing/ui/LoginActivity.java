package com.ycl.sportsing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private TextView textview_register;
    private ImageView imageview_back;
    private EditText edittext_username, edittext_password;
    private Button login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init() {
        setContentView(R.layout.activity_login);
        textview_register = (TextView) findViewById(R.id.textView_register);
        imageview_back = (ImageView) findViewById(R.id.imageview_back);
        login = (Button) findViewById(R.id.button_login);
        edittext_username = (EditText) findViewById(R.id.login_userName);
        edittext_password = (EditText) findViewById(R.id.login_userPassword);

        textview_register.setOnClickListener(this);
        imageview_back.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_register:
                startActivityForResult(new Intent(this, RegisterActivity.class), 9);
                break;
            case R.id.imageview_back:
                finish();
                break;
            case R.id.button_login:
                NetRequestConstant nrc = new NetRequestConstant();
                // post请求
                nrc.setType(HttpRequestType.POST);
                final String username = edittext_username.getText().toString();
                final String password = edittext_password.getText().toString();
                NetRequestConstant.requestUrl = NetUrlConstant.LOGINURL;
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
                                    String touxiang = object.optString("mytouxiang");
                                    data.putExtra("mytouxiang",touxiang);
                                    AppContext appContext = (AppContext) getApplicationContext();
                                    User user = new User();
                                    user.setUsername(username);
                                    user.setPassword(password);
                                    appContext.setUser(user);
                                    Toast.makeText(LoginActivity.this, "登陆成功！",
                                            Toast.LENGTH_LONG).show();
                                    setResult(12, data);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "账号或密码不正确",
                                            Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                        Log.d("TAG","我得到了登录的返回");

                    }
                }, nrc);

                break;
            default:
                break;
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null){
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            edittext_username.setText(username);
            edittext_password.setText(password);
        }
    }
}

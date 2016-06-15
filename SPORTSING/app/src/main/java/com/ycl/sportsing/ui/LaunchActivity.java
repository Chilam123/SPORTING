package com.ycl.sportsing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.ycl.sportsing.R;

public class LaunchActivity extends Activity {

    private ImageView mImageViewAnimation;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);


        AlphaAnimation anima=new AlphaAnimation(0.3f,1.0f);
        anima.setDuration(2000);  //时间为2秒

        mImageViewAnimation=(ImageView)this.findViewById(R.id.launch_animation_iv) ;
        mImageViewAnimation.startAnimation(anima);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转
//
//            public void run() {
//                //Intent intent = new Intent(StartActivity.this, LoginWelcomeActivity.class);
//                Intent intent = new Intent(StartActivity.this,MainFrameActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 5000);//2秒后跳转至应用主界面MainActivity



        anima.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mImageViewAnimation.setBackgroundResource(R.mipmap.bg_welcome_page);
                // mImageViewStart.setBackgroundResource(R.drawable.logo);
                Log.v("verbose","OnAnimationstart()方法被调用了！！！！！！！！！");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                SharedPreferences sp=getPreferences(MODE_PRIVATE);
//
//                //判断是否已经登录过账号，是的话直接用这账号登录进去
//                Boolean hasAccount=sp.getBoolean("hasAccount",false);
//               if(hasAccount){
//                   //获取sp账号
//                   String username=sp.getString("username","");
//                   String password=sp.getString("password","");
//                   //账号密码都存在，则请求登录
//                   if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
//                       /**
//                        * 这里省略发送到服务器请求登录
//                        *
//                        */
//                      //假设他登录成功，跳转到主界面 销毁欢迎界面
//                       //intent以后写
//                       StartActivity.this.finish();
//                   }
//               }
//                Button button=(Button)StartActivity.this.findViewById(R.id.button);
//                button.setVisibility(View.VISIBLE);
                skip();

               LaunchActivity.this.finish();
                Log.v("verbose","OnAnimationEnd()方法被调用了");


            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });




    }

    private void skip(){
        //下面是跳转到登录界面
        startActivity(new Intent(this,MainActivity.class));
        //销毁欢迎界面
        this.finish();
    }
}


package com.ycl.yuesport.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.ycl.yuesport.config.AppConfig;
import com.ycl.yuesport.domain.User;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by MI on 2016/5/20.
 * App上下文，保存基本信息，单例模式
 */

public class AppContext extends Application {

    private User user;
    private int screenWidth;
    private int screenHeight;
    private static AppContext instance;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;

        //初始化NoHttp
        NoHttp.init(this);
        Logger.setTag("NoHttp");
        Logger.setDebug(true);// 开始NoHttp的调试模式, 这样就能看到请求过程和日志

        AppConfig.getInstance(); //初始化一下AppConfig类,来保证是第一次实例化,保证线程安全,防止两个同时判断为null都进去实例化

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
        ////




    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

        //把用户信息写进sharedPreference
        AppConfig.getInstance().putString("userPhone", user.getUserPhone());
        AppConfig.getInstance().putString("userPassword", user.getUserPassword());
        AppConfig.getInstance().putString("userNickname", user.getUserNickname());
        AppConfig.getInstance().putString("userYueAccount", user.getUserYueAccount());
        AppConfig.getInstance().putInt("userSex", user.getUserSex());
        AppConfig.getInstance().putInt("userAge", user.getUserAge());
        AppConfig.getInstance().putInt("userTag", user.getUserTag());
        AppConfig.getInstance().putInt("userCartState", user.getUserCartState());
        AppConfig.getInstance().putInt("userKeepfitArea", user.getUserKeepfitArea());
        AppConfig.getInstance().putString("userFriendsPurpose", user.getUserFriendsPurpose());
        AppConfig.getInstance().putString("userTouxiang", user.getUserTouxiang());
        AppConfig.getInstance().putInt("showAgeInYue", user.getShowAgeInYue());
        AppConfig.getInstance().putInt("showAgeInMine", user.getShowAgeInMine());
    }

    public static AppContext getInstance() {
        return instance;
    }

}

/*
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ycl.yuesport.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.ycl.yuesport.app.AppContext;
import com.ycl.yuesport.utils.FileUtil;

import java.io.File;
import java.util.Set;

/**
 * App的配置类，保存偏好设置等
 * 使用单例模式，保证线程安全
 */
public class AppConfig {

    private static AppConfig appConfig;

    private SharedPreferences preferences;

    private final String APP_SP_FILENAME="yuesport"; //保存偏好设置的SharedPreferences文件名字

    private final String APP_NAME="约健身";  //App项目名字,一般是manifest里的 @string/app_name

    /**
     * 是否是测试环境.
     */
    public static final boolean DEBUG = true;  //先测试
//    public static final boolean DEBUG = false;

    /**
     * App根目录.
     */
    public String APP_PATH_ROOT;

    private AppConfig() {

        preferences = AppContext.getInstance().getSharedPreferences(APP_SP_FILENAME, Context.MODE_PRIVATE);

        APP_PATH_ROOT = FileUtil.getRootPath().getAbsolutePath() + File.separator + APP_NAME; //SD卡目录+文件分隔符+项目名称 这就是app的根目录
        FileUtil.initDirectory(APP_PATH_ROOT);
    }

    public static AppConfig getInstance() {
        if (appConfig == null)
            appConfig = new AppConfig();
        return appConfig;
    }

    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).commit();
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public void putString(String key, String value) {
        preferences.edit().putString(key, value).commit();
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public void putLong(String key, long value) {
        preferences.edit().putLong(key, value).commit();
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public void putFloat(String key, float value) {
        preferences.edit().putFloat(key, value).commit();
    }

    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public void putStringSet(String key, Set<String> value) {
        preferences.edit().putStringSet(key, value).commit();
    }

    public Set<String> getStringSet(String key, Set<String> defValue) {
        return preferences.getStringSet(key, defValue);
    }

}

package com.ycl.sportsing.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.Handler;

import com.ycl.sportsing.domain.City;
import com.ycl.sportsing.domain.Sport;
import com.ycl.sportsing.domain.User;
import com.ycl.sportsing.domain.Weather;
import com.ycl.sportsing.parser.AssetsParser;
import com.yolanda.nohttp.RequestQueue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AppContext extends Application {
	private List<City> cities;
	private List<Sport> sports;
   private RequestQueue requestQueue;
	private String city;
	private SharedPreferences preferences;
	private User user;
	private int screenWidth;
	private int screenHeight;
	private ArrayList<Weather> myList=new ArrayList<>();//用户自己添加的天气
	private ArrayList<Weather> allList=new ArrayList<>();//全部天气
	private Handler handler;


	public RequestQueue getRequestQueue() {
		return requestQueue;
	}

	public void setRequestQueue(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
	}

	public ArrayList<Weather> getAllList() {
		return allList;
	}

	public void setAllList(ArrayList<Weather> allList) {
		this.allList = allList;
	}

	public ArrayList<Weather> getMyList() {
		return myList;
	}

	public void setMyList(ArrayList<Weather> myList) {
		this.myList = myList;
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

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public List<City> getCities() {
		return cities;
	}

	public List<Sport> getSports() {
		return sports;
	}

	public void setSports(List<Sport> sports) {
		this.sports = sports;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		String as;
		try {
			as = getA();
			AssetsParser parser = new AssetsParser();
			List<City> cities = parser.getCities(as);
			setCities(cities);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getA() throws IOException {
		AssetManager assetManager = this.getAssets();
		InputStream is = assetManager.open("city_coordinate.txt");
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = is.read(buffer)) != -1) {
			stream.write(buffer, 0, length);
		}
		return stream.toString();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public SharedPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}


	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		Editor editor = preferences.edit();
		editor.putString("username", user.getUsername());
		editor.putString("password", user.getPassword());
		editor.commit();

	}

}

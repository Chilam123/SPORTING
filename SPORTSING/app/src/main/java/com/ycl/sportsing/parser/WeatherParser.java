package com.ycl.sportsing.parser;

import com.ycl.sportsing.domain.Weather;
import com.ycl.sportsing.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherParser {
	public ArrayList<Weather> getWeather(Object res){
		Logger.i(res.toString());
		ArrayList<Weather> sportsList= null;
		JSONObject object;
		try {
			object = new JSONObject(res.toString());
			JSONArray array = object.getJSONArray("weathers");
			if (array != null && !array.equals("[]")) {
				sportsList=new ArrayList<Weather>();
				for(int i=0;i<array.length();i++){
					Weather weather=new Weather();
					JSONObject object2 = array.optJSONObject(i);

					weather.setXuechang(object2.optString("xuechang"));
					weather.setTianqi(object2.optString("tianqi"));
					weather.setWendu(object2.optString("wendu"));
					weather.setJianshuiliang(object2.optString("jianshuiliang"));
					weather.setImageweather(object2.optString("imageweather"));

					sportsList.add(weather);
				}
				
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return sportsList;
	}
	
}

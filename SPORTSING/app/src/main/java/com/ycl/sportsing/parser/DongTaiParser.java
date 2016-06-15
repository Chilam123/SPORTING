package com.ycl.sportsing.parser;

import com.ycl.sportsing.domain.DongTai;
import com.ycl.sportsing.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DongTaiParser {
	public ArrayList<DongTai> getDongTai(Object res){
		Logger.i(res.toString());
		ArrayList<DongTai> sportsList= null;
		JSONObject object;
		try {
			object = new JSONObject(res.toString());
			JSONArray array = object.getJSONArray("dongtais");
			if (array != null && !array.equals("[]")) {
				sportsList=new ArrayList<DongTai>();
				for(int i=0;i<array.length();i++){
					DongTai dongTai=new DongTai();
					JSONObject object2 = array.optJSONObject(i);

					dongTai.setDongtai_id(object2.optInt("dongtai_id"));
					dongTai.setPublisher_id(object2.optString("publisher_id"));  //发布者名字
					dongTai.setDongtai_details(object2.optString("dongtai_details"));
					dongTai.setDongtai_picture(object2.optString("dongtai_picture"));
					dongTai.setDongtai_time(object2.optString("dongtai_time"));

					sportsList.add(dongTai);
				}
				
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return sportsList;
	}
	
}

package com.ycl.yuesport.parser;

import com.ycl.yuesport.domain.Yue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YueListParser {
	public ArrayList<Yue> getYueList(Object res){
		ArrayList<Yue> yueList= null;
		JSONObject object;
		try {
			object = new JSONObject(res.toString());
			JSONArray array = object.getJSONArray("yuelist");
			if (array != null && !array.equals("[]")) {
				yueList=new ArrayList<>();
				for(int i=0;i<array.length();i++){
					Yue yue=new Yue();
					JSONObject object2 = array.optJSONObject(i);

					yue.setYue_id(object2.optString("yue_id")); //约的id
					yue.setPublisher_touxiang(object2.optString("publisher_touxiang"));  //发布者头像
					yue.setPublisher_sex(object2.optString("publisher_sex"));//发布者性别
					yue.setYue_time(object2.optString("yue_time"));//约的时间
					yue.setYue_jianshenfang_name(object2.optString("yue_jianshenfang_name"));   //健身房名称
					yue.setYue_jianshenfang_area(object2.optString("yue_jianshenfang_area"));   //健身房地区
					yue.setYue_price(object2.optInt("yue_price"));  //约的价格
					yue.setYue_need_people(object2.optInt("yue_need_people"));  //约的总共人数
					yue.setYue_now_people(object2.optInt("yue_now_people"));   //约的现在人数
					yueList.add(yue);
				}
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return yueList;
	}
	
}

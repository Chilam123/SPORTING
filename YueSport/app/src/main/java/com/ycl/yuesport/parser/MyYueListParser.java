package com.ycl.yuesport.parser;

import com.ycl.yuesport.domain.Yue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyYueListParser {
	public ArrayList<Yue> getMyYueList(Object res){
		ArrayList<Yue> myYueList= null;
		JSONObject object;
		try {
			object = new JSONObject(res.toString());
			JSONArray array = object.getJSONArray("myyuelist");
			if (array != null && !array.equals("[]")) {
				myYueList=new ArrayList<>();
				for(int i=0;i<array.length();i++){
					Yue yue=new Yue();
					JSONObject object2 = array.optJSONObject(i);

					yue.setYue_id(object2.optString("yue_id")); //约的id
			       yue.setYue_date(object2.optString("yue_date"));
					yue.setYue_time(object2.optString("yue_time"));//约的时间
					yue.setCart_state(object2.optInt("yue_cart_state"));//有无卡
					yue.setYue_jianshenfang_name(object2.optString("yue_jianshenfang_name"));   //健身房名称
					yue.setYue_price(object2.optInt("yue_price"));  //约的价格
					yue.setYue_need_people(object2.optInt("yue_need_people"));  //约的总共人数
					yue.setYue_now_people(object2.optInt("yue_now_people"));   //约的现在人数
					yue.setYue_intent(object2.optString("yue_intent"));   //约的意图
					myYueList.add(yue);
				}
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return myYueList;
	}
	
}

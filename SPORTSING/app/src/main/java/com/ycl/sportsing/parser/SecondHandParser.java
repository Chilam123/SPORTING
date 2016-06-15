package com.ycl.sportsing.parser;

import com.ycl.sportsing.domain.SecondHand;
import com.ycl.sportsing.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondHandParser {
	public ArrayList<SecondHand> getSecondHand(Object res){
		Logger.i(res.toString());
		ArrayList<SecondHand> sportsList= null;
		JSONObject object;
		try {
			object = new JSONObject(res.toString());
			JSONArray array = object.getJSONArray("secondhands");
			if (array != null && !array.equals("[]")) {
				sportsList=new ArrayList<SecondHand>();
				for(int i=0;i<array.length();i++){
					SecondHand secondHand=new SecondHand();
					JSONObject object2 = array.optJSONObject(i);

					secondHand.setSecondhand_id(object2.optInt("secondhand_id"));
					secondHand.setPublisher_id(object2.optString("publisher_id"));  //发布者名字
					secondHand.setSecondhand_details(object2.optString("secondhand_details"));
					secondHand.setSecondhand_picture(object2.optString("secondhand_picture"));
					secondHand.setSecondhand_price(object2.optString("secondhand_price"));
					secondHand.setSecondhand_title(object2.optString("secondhand_title"));

					sportsList.add(secondHand);
				}
				
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return sportsList;
	}
	
}

package com.ycl.sportsing.parser;

import com.ycl.sportsing.domain.Sport;
import com.ycl.sportsing.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SportParser {
	public ArrayList<Sport> getSport(Object res){
		Logger.i(res.toString());
		ArrayList<Sport> sportsList= null;
		JSONObject object;
		try {
			object = new JSONObject(res.toString());
			JSONArray array = object.getJSONArray("sports");
			if (array != null && !array.equals("[]")) {
				sportsList=new ArrayList<Sport>();
				for(int i=0;i<array.length();i++){
					Sport sport=new Sport();
					JSONObject object2 = array.optJSONObject(i);

					sport.setSport_id(object2.optInt("sport_id"));
					sport.setPublisher_id(object2.optString("publisher_id"));  //发布者名字
					sport.setSport_city(object2.optString("sport_city"));
					sport.setSport_join_list(object2.optString("sport_join_list"));
					sport.setSport_picture(object2.optString("sport_picture"));
					sport.setSport_title(object2.optString("sport_title"));
					sport.setSport_xuechang(object2.getString("sport_xuechang"));
					sport.setSport_date(object2.getString("sport_date"));
					sport.setIs_join(object2.getInt("is_join"));
					sportsList.add(sport);
				}
				
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return sportsList;
	}
	
}

package com.ycl.sportsing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ycl.sportsing.R;
import com.ycl.sportsing.domain.Weather;

import java.util.ArrayList;

public class SelectWeatherAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Weather> allList;
	private HolderView holderView=null;

	public SelectWeatherAdapter(Context context, ArrayList<Weather> allList) {
		this.context = context;
		this.allList = allList;
		System.out.println("allList.size()" + allList.size());


	}

	public int getCount() {
		// TODO Auto-generated method stub
		return allList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		holderView = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.select_weather_listitem, null);
			  holderView = new HolderView();
            holderView.selectweather=(TextView)convertView.findViewById(R.id.text_select_weather);

            convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}

		holderView.selectweather.setText(allList.get(position)
				.getXuechang());

		return convertView;
	}

	class HolderView {
		private TextView selectweather;
	}


}

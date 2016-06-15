package com.ycl.sportsing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.sportsing.R;
import com.ycl.sportsing.domain.Weather;

import java.util.ArrayList;

public class XueChangWeatherAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Weather> allList;
	private HolderView holderView=null;

	public XueChangWeatherAdapter(Context context, ArrayList<Weather> allList) {
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

	public void setData(ArrayList<Weather> weathers) {
		this.allList = weathers;
		notifyDataSetChanged();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		holderView = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.weather_listitem, null);
			  holderView = new HolderView();
            holderView.imageWeather=(ImageView)convertView.findViewById(R.id.image_weather);
            holderView.xuechang=(TextView)convertView.findViewById(R.id.text_xuechang_weather);
            holderView.tianqi=(TextView)convertView.findViewById(R.id.text_tianqi_weather);
            holderView.wendu=(TextView)convertView.findViewById(R.id.text_wendu_weather);
            holderView.jianshuiliang=(TextView)convertView.findViewById(R.id.text_jianshuiliang_weather);

            convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}

		holderView.xuechang.setText(allList.get(position)
				.getXuechang());
		holderView.tianqi
				.setText(allList.get(position).getTianqi());
		holderView.wendu
				.setText(allList.get(position).getWendu());
		holderView.jianshuiliang
				.setText(allList.get(position).getJianshuiliang());
        holderView.imageWeather.setBackgroundResource(allList.get(position).getImageweather());

		return convertView;
	}

	class HolderView {
		private ImageView imageWeather;
		private TextView xuechang,tianqi,wendu,jianshuiliang;
	}


}

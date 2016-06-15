package com.ycl.yuesport.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.ycl.yuesport.R;
import com.ycl.yuesport.domain.Yue;

import java.util.ArrayList;

public class YueListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Yue> yueArrayList;
	private DisplayImageOptions touxiangOptions;

	public YueListAdapter(Context context, ArrayList<Yue> yueArrayList)
	{
		this.context = context;
		this.yueArrayList = yueArrayList;
		touxiangOptions = new DisplayImageOptions.Builder()
				.showImageOnFail(R.mipmap.user_default_touxiang)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
	}

	public int getCount() {
		return yueArrayList==null ? 0 : yueArrayList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return yueArrayList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.yuelist_cell, null);
			viewHolder.image_publsher_touxiang = (ImageView) convertView
					.findViewById(R.id.ic_publisher_touxiang_yuelist_cell);
			viewHolder.publisher_sex = (TextView) convertView
					.findViewById(R.id.text_sex_yuelist_cell);
			viewHolder.yue_time = (TextView) convertView
					.findViewById(R.id.text_yuetime_yuelist_cell);
			viewHolder.jianshenfang_name = (TextView) convertView
					.findViewById(R.id.text_jianshenfangname_yuelist_cell);
			viewHolder.yue_price = (TextView) convertView
					.findViewById(R.id.text_yueprice_yuelist_cell);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.publisher_sex.setText(yueArrayList.get(position).getPublisher_sex());
		viewHolder.yue_time.setText(yueArrayList.get(position).getYue_time());
		viewHolder.jianshenfang_name.setText(yueArrayList.get(position).getYue_jianshenfang_name());
		viewHolder.yue_price.setText(yueArrayList.get(position).getYue_price()+"元");


		return convertView;
	}
	
	class ViewHolder {
		ImageView image_publsher_touxiang;
		TextView publisher_sex,yue_time,jianshenfang_name,yue_price;
	}
  


}
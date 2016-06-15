package com.ycl.sportsing.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ycl.sportsing.R;
import com.ycl.sportsing.domain.XuePiao;

import java.util.ArrayList;

public class XuePiaoAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<XuePiao> allList;

	public XuePiaoAdapter(Context context, ArrayList<XuePiao> allList) {
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
		HolderView holderView = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.xuepiao_listitem, null);
			holderView = new HolderView();
			holderView.xuechang = (TextView) convertView
					.findViewById(R.id.text_title_xuepiao);
			holderView.price = (TextView) convertView
					.findViewById(R.id.text_price_xuepiao);
			holderView.buy=(ImageButton)convertView.findViewById(R.id.imagebutton_buy_xuepiao) ;

			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}

		holderView.xuechang.setText(allList.get(position).getXuechang());
		holderView.price.setText(allList.get(position).getPrice());
		Log.v("TAG","position"+position+" "+allList.get(position).getXuechang()+"价格"+allList.get(position).getPrice());

		holderView.buy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context,"已购买雪票",Toast.LENGTH_SHORT).show();
			}
		});

		return convertView;
	}

	class HolderView {
		private TextView xuechang, price;
		private ImageButton buy;
	}


}

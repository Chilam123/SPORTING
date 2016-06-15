package com.ycl.sportsing.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.sportsing.R;
import com.ycl.sportsing.domain.DongTai;
import com.ycl.sportsing.utils.SplitNetImagePath;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DongTaiAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<DongTai> allList;
	Bitmap pngBM;// 网络图片
	Bitmap[] bitmaps;
	private HolderView holderView=null;

	public DongTaiAdapter(Context context, ArrayList<DongTai> allList) {
		this.context = context;
		this.allList = allList;
		System.out.println("allList.size()" + allList.size());

		bitmaps = new Bitmap[allList.size()];
		ImageCache1 imageCache = new ImageCache1();
		imageCache.start();
		try {
			imageCache.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
					R.layout.dongtai_listitem, null);
			holderView = new HolderView();
			holderView.dongtai_details = (TextView) convertView
					.findViewById(R.id.text_details_dongtai);
			holderView.publisher_id = (TextView) convertView
					.findViewById(R.id.publisher_dongtai);
			holderView.dongtai_time = (TextView) convertView
					.findViewById(R.id.text_time_dongtai);
			holderView.image_dongtai = (ImageView) convertView
					.findViewById(R.id.image_dongtai);


			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}

		holderView.dongtai_details.setText(allList.get(position)
				.getDongtai_details());
		holderView.dongtai_time
				.setText(allList.get(position).getDongtai_time());
		holderView.publisher_id
				.setText(allList.get(position).getPublisher_id());

		holderView.image_dongtai.setImageBitmap(bitmaps[position]);

		return convertView;
	}

	class HolderView {
		private ImageView image_dongtai;
		private TextView  dongtai_details, publisher_id,dongtai_time;
	}

	class ImageCache1 extends Thread {

		public void run() {
			for (int i = 0; i < allList.size(); i++) {
				String netPictruePath = allList.get(i).getDongtai_picture();
				String[] strings = SplitNetImagePath
						.splitNetImagePath(netPictruePath);
				// 显示第一张图片，为默认图片
				String pictruePath = strings[0];
				// 把网络地址转换为BitMap
				URL picUrl;
				try {
					picUrl = new URL(pictruePath);
					pngBM = BitmapFactory.decodeStream(picUrl.openStream());
					bitmaps[i] = pngBM;
					Log.v("TAG","请求网络图片成功");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

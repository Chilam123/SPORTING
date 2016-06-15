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
import com.ycl.sportsing.domain.SecondHand;
import com.ycl.sportsing.utils.SplitNetImagePath;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SecondHandAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<SecondHand> allList;
	Bitmap pngBM;// 网络图片
	Bitmap[] bitmaps;
	private HolderView holderView=null;

	public SecondHandAdapter(Context context, ArrayList<SecondHand> allList) {
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
					R.layout.secondhand_listitem, null);
			holderView = new HolderView();
			holderView.secondhand_title = (TextView) convertView
					.findViewById(R.id.title_secondhand);
			holderView.secondhand_details = (TextView) convertView
					.findViewById(R.id.text_details_secondhand);
			holderView.secondhand_price = (TextView) convertView
					.findViewById(R.id.price_secondhand);
			holderView.publisher_id = (TextView) convertView
					.findViewById(R.id.publisher_secondhand);
			holderView.image_secondhand = (ImageView) convertView
					.findViewById(R.id.image_secondhand);

			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}

		holderView.secondhand_title.setText(allList.get(position)
				.getSecondhand_title());
		holderView.secondhand_details
				.setText(allList.get(position).getSecondhand_details());
		holderView.secondhand_price
				.setText(allList.get(position).getSecondhand_price());
		holderView.publisher_id
				.setText(allList.get(position).getPublisher_id());

		holderView.image_secondhand.setImageBitmap(bitmaps[position]);

		return convertView;
	}

	class HolderView {
		private ImageView image_secondhand;
		private TextView secondhand_title, secondhand_details, secondhand_price,publisher_id;
	}

	class ImageCache1 extends Thread {

		public void run() {
			for (int i = 0; i < allList.size(); i++) {
				String netPictruePath = allList.get(i).getSecondhand_picture();
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

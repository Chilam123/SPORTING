package com.ycl.yuesport.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ycl.yuesport.R;
import com.ycl.yuesport.adapter.TextAdapter;


public class ViewPrice extends RelativeLayout implements ViewBaseAction {

	private ListView mListView;
	private final String[] items = new String[] {"不限价格","从低到高", "从高到低"};// 价格字段
	private final int[] itemsPosition = new int[] { 0,1,2};// 隐藏id
	private OnSelectListener mOnSelectListener;
	private TextAdapter adapter;
	private String showText = "价格";
	private Context mContext;
	private int bgResourcePic=0;

	public String getShowText() {
		return showText;
	}

	public ViewPrice(Context context) {
		super(context);
		init(context);
	}

	public ViewPrice(Context context,int bgResourcePic) {
		super(context);
		this.bgResourcePic=bgResourcePic;
		init(context);
	}

	public ViewPrice(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ViewPrice(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.expand_item_listview, this, true);
		if(bgResourcePic!=0)
		    setBackgroundDrawable(getResources().getDrawable(R.mipmap.choosearea_bg_right));
		else
			setBackgroundDrawable(getResources().getDrawable(bgResourcePic));
		mListView = (ListView) findViewById(R.id.listView);

		adapter = new TextAdapter(context, items, R.drawable.choose_eara_item_selector);
		adapter.setTextSize(13);

		mListView.setAdapter(adapter);
		adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			public void onItemClick(View view, int position) {

				if (mOnSelectListener != null) {
					showText = items[position];
					mOnSelectListener.getValue(itemsPosition[position], items[position]);
				}
			}
		});
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(int choosePosition, String showText);
	}

	public void hide() {

	}

	public void show() {

	}

	public int getShowPhoto() {
		// TODO Auto-generated method stub
		return 0;
	}

}

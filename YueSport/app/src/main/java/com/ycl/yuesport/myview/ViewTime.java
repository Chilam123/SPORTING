package com.ycl.yuesport.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.ycl.yuesport.R;
import com.ycl.yuesport.adapter.TimeTextAdapter;

import java.util.ArrayList;


public class ViewTime extends RelativeLayout implements ViewBaseAction {

	private GridView mGridView;
	private Button confirmTimeButton;
	private final String[] items = new String[] { "06:00-08:00","08:00-10:00","10:00-12:00", "12:00-14:00", "14:00-16:00", "16:00-18:00", "18:00-20:00","20:00-22:00"};// 显示字段
	private final int[] itemsPosition = new int[] { 0,1,2,3,4,5,6,7};// 隐藏id
	private OnSelectListener mOnSelectListener;
	private TimeTextAdapter timeAdapter;
	private String showText = "时间";
	private Context mContext;
	private int bgResourcePic=0;
	private ArrayList<Integer> selectedTimeArrayList=new ArrayList<>();

	public String getShowText() {
		return showText;
	}

	public ViewTime(Context context) {
		super(context);
		init(context);
	}

	public ViewTime(Context context,int bgResourcePic) {
		super(context);
		this.bgResourcePic=bgResourcePic;
		init(context);
	}

	public ViewTime(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ViewTime(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.expand_item_gridview_, this, true);
		if(bgResourcePic!=0)
		    setBackgroundDrawable(getResources().getDrawable(bgResourcePic));
		else
			setBackgroundDrawable(getResources().getDrawable(R.mipmap.choosearea_bg_mid));
		mGridView = (GridView) findViewById(R.id.gridView);
		confirmTimeButton=(Button)findViewById(R.id.button_confirm_time);
		timeAdapter = new TimeTextAdapter(context, items, R.drawable.choose_eara_item_selector);
		timeAdapter.setTextSize(13);


		mGridView.setAdapter(timeAdapter);
		timeAdapter.setOnItemClickListener(new TimeTextAdapter.OnItemClickListener() {

			public void onItemClick(View view, int position) {

				showText = items[position];
				modifySelectedTimeArrayList(position);

//				if (mOnSelectListener != null) {
//					showText = items[position];
//					mOnSelectListener.getValue(itemsPosition[position], items[position]);
//				}
			}
		});

		confirmTimeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mOnSelectListener != null) {
					mOnSelectListener.getValue(selectedTimeArrayList);
				}
			}
		});
	}

	private void modifySelectedTimeArrayList(int position){
		if(selectedTimeArrayList==null)
			return;
		//若存在，就把它删掉
		if(selectedTimeArrayList.contains(position)){
			selectedTimeArrayList.remove(new Integer(position));
		}
		//否则把它加进去
		else{
			selectedTimeArrayList.add(new Integer(position));
		}
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(ArrayList<Integer> selectedTimeArrayList);
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

package com.ycl.yuesport.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ycl.yuesport.R;
import com.ycl.yuesport.adapter.TextAdapter;


public class ViewArea extends RelativeLayout implements ViewBaseAction {

    private ListView mListView;
    private final String[] items = new String[]{"全城","东城区", "西城区", "崇文区", "宣武区", "朝阳区", "海淀区",
            "丰台区", "石景山区", "门头沟区", "房山区", "通州区", "顺义区",
            "昌平区", "大兴区", "怀柔区", "平谷区", "延庆县", "密云县"}; //地区字段
    private final int[] itemsPosition = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};//隐藏id
    private OnSelectListener mOnSelectListener;
    private TextAdapter adapter;
    private String showText = "地区";
    private Context mContext;
    private int bgResourcePic=0;

    public String getShowText() {
        return showText;
    }

    public ViewArea(Context context) {
        super(context);
        init(context);
    }

    public ViewArea(Context context,int bgResourcePic) {
        super(context);
        this.bgResourcePic=bgResourcePic;
        init(context);

    }

    public ViewArea(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.expand_item_listview, this, true);
        if(bgResourcePic!=0)
            setBackgroundDrawable(getResources().getDrawable(bgResourcePic));
        else
            setBackgroundDrawable(getResources().getDrawable(R.mipmap.choosearea_bg_left));
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

package com.ycl.yuesport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ycl.yuesport.R;
import com.ycl.yuesport.domain.Yue;

import java.util.ArrayList;

public class MyYueListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Yue> myYueArrayList;

    public MyYueListAdapter(Context context, ArrayList<Yue> myYueArrayList) {
        this.context = context;
        this.myYueArrayList = myYueArrayList;

    }

    public int getCount() {
        return myYueArrayList == null ? 0 : myYueArrayList.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return myYueArrayList.get(position);
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
                    R.layout.myyuelist_cell, null);
            viewHolder.gym_name = (TextView) convertView
                    .findViewById(R.id.text_gym_name_myyue);
            viewHolder.yue_date = (TextView) convertView
                    .findViewById(R.id.text_date_myyue);
            viewHolder.yue_time = (TextView) convertView
                    .findViewById(R.id.text_time_myyue);
            viewHolder.yue_people = (TextView) convertView
                    .findViewById(R.id.text_people_myyue);
            viewHolder.yue_price = (TextView) convertView
                    .findViewById(R.id.text_price_myyue);
            viewHolder.yue_intent = (TextView) convertView
                    .findViewById(R.id.text_intent_myyue);
            viewHolder.cart_state_description = (TextView) convertView
                    .findViewById(R.id.text_cart_description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.gym_name.setText(myYueArrayList.get(position).getYue_jianshenfang_name());
        viewHolder.yue_date.setText(myYueArrayList.get(position).getYue_date());
        viewHolder.yue_time.setText(myYueArrayList.get(position).getYue_time());
        //有卡
        if (myYueArrayList.get(position).getCart_state() == 1) {
            viewHolder.yue_people.setText(myYueArrayList.get(position).getYue_now_people() + "/" + myYueArrayList.get(position).getYue_need_people() + "人");
            viewHolder.cart_state_description.setText("有卡");
            viewHolder.cart_state_description.setBackgroundResource(R.drawable.text_shape_publisher);
        }else {
            //无卡
            viewHolder.yue_people.setText(myYueArrayList.get(position).getYue_need_people() + "人");
            viewHolder.cart_state_description.setText("无卡");
            viewHolder.cart_state_description.setBackgroundResource(R.drawable.text_shape_publisher2);
        }
            viewHolder.yue_price.setText(myYueArrayList.get(position).getYue_price() + "元");
        viewHolder.yue_intent.setText(myYueArrayList.get(position).getYue_intent());

        return convertView;
    }

    class ViewHolder {
        TextView gym_name, yue_date, yue_time, yue_people, yue_price, yue_intent,cart_state_description;
    }


}
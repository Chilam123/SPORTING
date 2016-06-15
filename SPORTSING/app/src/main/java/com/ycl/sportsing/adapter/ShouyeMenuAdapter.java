 package com.ycl.sportsing.adapter;

 import android.content.Context;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.BaseAdapter;
 import android.widget.ImageView;

 import com.ycl.sportsing.R;

 public class ShouyeMenuAdapter extends BaseAdapter {
     private Context context;
     private int[] i;

     public ShouyeMenuAdapter(Context context, int[] i) {
         this.context = context;
         this.i = i;
     }


     public int getCount() {
         // TODO Auto-generated method stub
         return i.length;
     }

     public Object getItem(int position) {
         // TODO Auto-generated method stub
         return null;
     }


     public long getItemId(int position) {
         // TODO Auto-generated method stub
         return 0;
     }

     class Holder {
         ImageView imageview;
     }


     public View getView(int position, View convertView, ViewGroup parent) {
         Holder holder = null;
         if (convertView == null) {
             holder = new Holder();
             convertView = LayoutInflater.from(context).inflate(
                     R.layout.shouye_menu_cell, null);
             holder.imageview = (ImageView) convertView
                     .findViewById(R.id.imageview_menu_cell);
             convertView.setTag(holder);
         } else {
             holder = (Holder) convertView.getTag();
         }
         holder.imageview.setBackgroundResource(i[position]);

         return convertView;
     }

 }

package com.ycl.sportsing.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ycl.sportsing.R;
import com.ycl.sportsing.app.AppContext;
import com.ycl.sportsing.domain.Sport;
import com.ycl.sportsing.domain.User;
import com.ycl.sportsing.utils.Logger;
import com.ycl.sportsing.utils.SplitNetImagePath;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SportAllAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Sport> allList;
    Bitmap pngBM;// 网络图片
    Bitmap[] bitmaps;
    private HolderView holderView = null;
    private static final int CHANGE_JOIN_STATE = 10;
    private Handler mHandler;

    public SportAllAdapter(Context context, ArrayList<Sport> allList, Handler handler) {
        this.context = context;
        this.allList = allList;
        System.out.println("allList.size()" + allList.size());
        mHandler = handler;


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

    public void setData(ArrayList<Sport> newList) {
        allList = newList;
        notifyDataSetInvalidated();
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        holderView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.sport_listitem, null);
            holderView = new HolderView();
            holderView.sport_title = (TextView) convertView
                    .findViewById(R.id.title_sport);
            holderView.sport_city = (TextView) convertView
                    .findViewById(R.id.text_city_sport);
            holderView.sport_xuechang = (TextView) convertView
                    .findViewById(R.id.text_xuechang_sport);
            holderView.sport_data = (TextView) convertView
                    .findViewById(R.id.text_data_sport);
            holderView.image_sport = (ImageView) convertView
                    .findViewById(R.id.image_sport);
            holderView.image_join_sport = (ImageView) convertView
                    .findViewById(R.id.image_join_sport);

            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }

        holderView.sport_title.setText(allList.get(position)
                .getSport_title());
        holderView.sport_city
                .setText(allList.get(position).getSport_city());
        holderView.sport_xuechang
                .setText(allList.get(position).getSport_xuechang());
        holderView.sport_data
                .setText(allList.get(position).getSport_date());
        Logger.i("position:" + position);
        holderView.image_sport.setImageBitmap(bitmaps[position]);


        // 若有人参加这活动，看参加列表里有没有我，设置已参加or没参加图片
        if (allList.get(position).getIs_join()==1) {
            String[] joinList = allList.get(position).getSport_join_list().split("&");
            if (joinList != null) {
             //   Log.v("TAG","测试.....");
                AppContext appContext = (AppContext) context.getApplicationContext();
                if (appContext.getUser() != null) {
                    String myUserName = appContext.getUser().getUsername();
                    Logger.i("username"+myUserName);
                    for (String username : joinList) {
                        if (myUserName.equals(username)) {
                            holderView.image_join_sport.setImageResource(R.mipmap.bt_sport_hasjoin);
                        }
                    }
                }

//                //这里先模拟用户名为Chilam
//                for (String username : joinList) {
//                    if (username.equals("Chilam")) {
//                        holderView.image_join_sport.setImageResource(R.mipmap.bt_sport_hasjoin);
//
//                        Logger.i("设置了已参加照片");
//                        break;
//                    }
//                }
            } else
                holderView.image_join_sport.setImageResource(R.mipmap.bt_sport_join);
        } else {
            holderView.image_join_sport.setImageResource(R.mipmap.bt_sport_join);
            Logger.i("设置了未参加照片");
        }

        //要做：点击图片后修改图片状态，并向服务器变更状态


        holderView.image_join_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppContext appContext = (AppContext) context.getApplicationContext();
				  User user=appContext.getUser();
                if(user==null){
                    Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (allList.get(position).getIs_join()==1) {
                    String[] joinList = allList.get(position).getSport_join_list().split("&");
                    if (joinList != null) {
                        String myUserName = user.getUsername();
                        for (String username : joinList) {
                            if (myUserName.equals(username)) {
                                return;
                            }
                        }

//                        //这里先模拟用户名为Chilam
//                        for (String username : joinList) {
//                            if (username.equals("Chilam")) {
//                                return;
//                            }
//                        }
                    }

                }

                Message message = new Message();
                int sportID = allList.get(position).getSport_id();
                message.obj = sportID;
                message.what = CHANGE_JOIN_STATE;
                mHandler.sendMessage(message);
                allList.get(position).setIs_join(1);
               allList.get(position).setSport_join_list(allList.get(position).getSport_join_list() + "&"+user.getUsername());
//               //先用Chilam替代
//                allList.get(position).setSport_join_list(allList.get(position).getSport_join_list() + "&Chilam");
                notifyDataSetInvalidated();

                Logger.i("点击了ListView中的按钮图片,这是adapter中消息,position:" + position);


            }
        });
        return convertView;
    }


    public static class HolderView {
        private ImageView image_sport;
        public ImageView image_join_sport;
        private TextView sport_title, sport_city, sport_xuechang, sport_data;
    }

    class ImageCache1 extends Thread {

        public void run() {
            for (int i = 0; i < allList.size(); i++) {
                String netPictruePath = allList.get(i).getSport_picture();
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

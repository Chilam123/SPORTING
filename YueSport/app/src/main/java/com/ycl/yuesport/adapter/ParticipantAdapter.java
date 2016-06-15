package com.ycl.yuesport.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ycl.yuesport.R;

import java.util.ArrayList;

public class ParticipantAdapter extends BaseAdapter {

    private Context mContext;

    // References to our images in res > drawable
    public int[] mThumbIds = {}; //装载本地图片的resourcesID:R.id.XXX
    public ArrayList<String> mThumbPicUrl = null; //装载网络图片url
    private static boolean isNetPic = false; //判断是显示本地图片还是网络图片

    //设置gridview图片高度，layoutparams长宽默认是像素px,传进来前转dp
    private AbsListView.LayoutParams layoutParams = null;

    //待处理，修改默认参与者默认略缩图图片...........................................................
    private DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
            .showImageOnFail(R.mipmap.user_default_touxiang)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    /**
     * 显示本地图片的Adapter
     * @param c           上下文
     * @param mThumbIds   装载本地图片的ResourcesID数组：{R.id.XXX,R.id.XXX,...}
     * @param layoutParams 设置图片高度的AbsListView.LayoutParams
     */
    public ParticipantAdapter(Context c,int[] mThumbIds,AbsListView.LayoutParams layoutParams) {
        mContext = c;
        this.mThumbIds = mThumbIds;
        this.layoutParams=layoutParams;

    }

    /**
     * 显示网络图片的Adapter
     * @param c            上下文
     * @param mThumbPicUrl 装载网络图片url的arrayList
     * @param layoutParams 设置图片高度的AbsListView.LayoutParams
     */
    public ParticipantAdapter(Context c, ArrayList<String> mThumbPicUrl,AbsListView.LayoutParams layoutParams) {
        mContext = c;
        this.mThumbPicUrl = mThumbPicUrl;
        this.layoutParams=layoutParams;
        isNetPic = true;//设置显示为网络图片

    }

    public int getCount() {
        return isNetPic ? mThumbPicUrl.size() : mThumbIds.length;
    }

    public Object getItem(int position) {
        return isNetPic ? mThumbPicUrl.get(position) : mThumbIds[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        //设置图片大小参数
        if (layoutParams != null)
            imageView.setLayoutParams(layoutParams);

        //本地图片
        if (!isNetPic) {
            imageView.setImageResource(mThumbIds[position]);
            imageView.setTag(mThumbIds[position]);
        }
        //网络图片
        else {
            ImageLoader.getInstance().displayImage(mThumbPicUrl.get(position), imageView, imageOptions);
            imageView.setTag(mThumbPicUrl.get(position));
        }

        return imageView;
    }




}
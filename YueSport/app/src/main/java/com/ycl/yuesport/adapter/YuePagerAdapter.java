package com.ycl.yuesport.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ycl.yuesport.fragment.HasCartFragment;
import com.ycl.yuesport.fragment.NoCartFragment;


public class YuePagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public YuePagerAdapter(FragmentManager fm,int numOfTabs){
        super(fm);
        this.numOfTabs=numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HasCartFragment hasCartFragment=new HasCartFragment();
                return hasCartFragment;
            case 1:
                NoCartFragment noCartFragment=new NoCartFragment();
                return noCartFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}

package com.ycl.yuesport.pager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 点击略缩图图片后，装载大图片的ViewPager
 */
public class HackyViewPager extends ViewPager {

	public HackyViewPager(Context context) {
		super(context);
	}

	
	public HackyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
	}

}

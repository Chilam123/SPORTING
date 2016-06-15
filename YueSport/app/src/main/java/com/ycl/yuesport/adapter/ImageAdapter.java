package com.ycl.yuesport.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ycl.yuesport.R;
import com.ycl.yuesport.pager.HackyViewPager;
import com.ycl.yuesport.utils.DensityUtil;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageAdapter extends BaseAdapter {

    private Animator mCurrentAnimator;

    private int mShortAnimationDuration = 300;

    private Context mContext;

    // References to our images in res > drawable
    public int[] mThumbIds = {}; //装载本地图片的resourcesID:R.id.XXX
    public ArrayList<String> mThumbPicUrl = null; //装载网络图片url
    float startScale;
    HackyViewPager viewPager;
    Rect startBounds;
    float startScaleFinal;
    private static boolean isNetPic = false; //判断是显示本地图片还是网络图片
    private int containerID; //根layout的id,最外层layout的id
    private GridView targetGridView;//目标gridview

    private AbsListView.LayoutParams layoutParams = null;

    //待处理，修改默认健身房默认略缩图图片...........................................................
    private DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
            .showImageOnFail(R.mipmap.user_default_touxiang)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    /**
     * 显示本地图片的Adapter
     * @param c           上下文
     * @param viewPager   显示大图片的viewpager
     * @param containerID 最外层layout的id，用来计算动画的扩展结束位置
      * @param targetGridView 传入你要设置动画的gridview
     * @param mThumbIds   装载本地图片的ResourcesID数组：{R.id.XXX,R.id.XXX,...}
     */
    public ImageAdapter(Context c, HackyViewPager viewPager, int containerID,  GridView targetGridView,int[] mThumbIds) {
        mContext = c;
        this.viewPager = viewPager;
        this.mThumbIds = mThumbIds;
        this.containerID = containerID;
        this.targetGridView=targetGridView;

        //设置gridview图片高度，layoutparams长宽默认是像素px,转dp
        layoutParams = new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT, DensityUtil.dip2px(mContext, 80f));

    }

    /**
     * 显示网络图片的Adapter
     * @param c            上下文
     * @param viewPager    显示大图片的viewpager
     * @param containerID  最外层layout的id，用来计算动画的扩展结束位置
     * @param targetGridView 传入你要设置动画的gridview
     * @param mThumbPicUrl 装载网络图片url的arrayList
     */
    public ImageAdapter(Context c, HackyViewPager viewPager, int containerID,GridView targetGridView, ArrayList<String> mThumbPicUrl) {
        mContext = c;
        this.viewPager = viewPager;
        this.mThumbPicUrl = mThumbPicUrl;
        this.containerID = containerID;
        this.targetGridView=targetGridView;
        isNetPic = true;//设置显示为网络图片

        //设置gridview图片高度，layoutparams长宽默认是像素px,转dp
        layoutParams = new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT, DensityUtil.dip2px(mContext, 80f));

        //待处理，若健身圈图片大小，参考朋友圈图片数量变更大小，看要不要把layoutparams封装.........................................................
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

        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                zoomImageFromThumb(view, position);
            }
        });


        return imageView;
    }


    private void zoomImageFromThumb(View thumbView, int position) {
        // If there's an animation in progress, cancel it immediately and
        // proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        if (!isNetPic)
            viewPager.setAdapter(new SamplePagerAdapter(mThumbIds, mContext));
        else
            viewPager.setAdapter(new SamplePagerAdapter(mThumbPicUrl, mContext));

        viewPager.setCurrentItem(position);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step
        // involves lots of math. Yay, math.
        startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the
        // final bounds are the global visible rectangle of the container view.
        // Also
        // set the container view's offset as the origin for the bounds, since
        // that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        Log.i(position + "", thumbView.toString());
        Log.i(position + "", "(" + startBounds.left + "," + startBounds.top + "," + startBounds.right + "," + startBounds.bottom + ")");
        ((Activity) mContext).findViewById(containerID).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the
        // "center crop" technique. This prevents undesirable stretching during
        // the animation.
        // Also calculate the start scaling factor (the end scaling factor is
        // always 1.0).

        if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
                .width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        //  show the zoomed-in view. When the animation
        // begins,
        // it will position the zoomed-in view in the place of the thumbnail.
        viewPager.setVisibility(View.VISIBLE);
        // Set the pivot point for SCALE_X and SCALE_Y transformations to the
        // top-left corner of
        // the zoomed-in view (the default is the center of the view).

        // 设置动画开始的SCALE_X 和SCALE_Y变化的左上角原点,如果不设置的话默认从View的center开始
//		viewPager.setPivotX(0f);  
//		viewPager.setPivotY(0f);  
//		
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(1);
        animSet.play(ObjectAnimator.ofFloat(viewPager, "pivotX", 0f))
                .with(ObjectAnimator.ofFloat(viewPager, "pivotY", 0f))
                .with(ObjectAnimator.ofFloat(viewPager, "alpha", 1.0f));
        animSet.start();


        // Construct and run the parallel animation of the four translation and
        // scale properties
        // (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(targetGridView, "alpha", 1.0f, 0.f);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(viewPager, "x", startBounds.left, finalBounds.left);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(viewPager, "y", startBounds.top, finalBounds.top);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(viewPager, "scaleX", startScale, 1f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(viewPager, "scaleY", startScale, 1f);

        set.play(alphaAnimator)
                .with(animatorX)
                .with(animatorY)
                .with(animatorScaleX).with(animatorScaleY);
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {

            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the
        // original bounds
        // and show the thumbnail instead of the expanded image.
        startScaleFinal = startScale;

    }


    public boolean getScaleFinalBounds(int position) {
        GridView gridView =targetGridView;
        View childView = gridView.getChildAt(position - gridView.getFirstVisiblePosition());

        startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        try {

            if (childView == null) {
                Log.i("info", "null");
            } else {
                childView.getGlobalVisibleRect(startBounds);
            }

            Log.i(position + ">>", "(" + startBounds.left + "," + startBounds.top + "," + startBounds.right + "," + startBounds.bottom + ")");
        } catch (Exception e) {

            Log.i("info", "出异常:" + e.getMessage());
            return false;
        }
        ((Activity) mContext).findViewById(containerID)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
                .width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }
        startScaleFinal = startScale;
        return true;
    }

    class SamplePagerAdapter extends PagerAdapter {

        private int[] sDrawables;
        private ArrayList<String> mThumbPicUrlInAdapter;
        private Context mContext;
        private boolean isNetPicInAdapter = false;

        //待处理，修改默认健身房大图默认图片(用略缩图？？？？)...........................................................
        DisplayImageOptions imageOptionsBigPic = new DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .cacheOnDisk(true)
        .bitmapConfig(Bitmap.Config.RGB_565)
        .build();

        public SamplePagerAdapter(int[] imgIds, Context context) {
            this.sDrawables = imgIds;
            this.mContext = context;
        }

        public SamplePagerAdapter(ArrayList<String> mThumbPicUrl, Context context) {
            this.mThumbPicUrlInAdapter = mThumbPicUrl;
            this.mContext = context;
            isNetPicInAdapter = true;
        }

        @Override
        public int getCount() {
            return isNetPicInAdapter ? mThumbPicUrlInAdapter.size() : sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            final PhotoView photoView = new PhotoView(container.getContext());
            if (!isNetPicInAdapter)
                photoView.setImageResource(sDrawables[position]);
            else
                ImageLoader.getInstance().displayImage(mThumbPicUrlInAdapter.get(position), photoView, imageOptionsBigPic);

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            photoView
                    .setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                        public void onPhotoTap(View paramAnonymousView,
                                               float paramAnonymousFloat1,
                                               float paramAnonymousFloat2) {
                            if (mCurrentAnimator != null) {
                                mCurrentAnimator.cancel();
                            }

                            photoView.clearZoom();

                            boolean scaleResult = getScaleFinalBounds(position);
                            // Animate the four positioning/sizing properties in parallel,
                            // back to their
                            // original values.
                            AnimatorSet as = new AnimatorSet();
                            ObjectAnimator containAlphaAnimator = ObjectAnimator.ofFloat(targetGridView, "alpha", 0.f, 1.0f);
                            if (scaleResult) {
                                ObjectAnimator animatorX = ObjectAnimator.ofFloat(viewPager, "x", startBounds.left);
                                ObjectAnimator animatorY = ObjectAnimator.ofFloat(viewPager, "y", startBounds.top);
                                ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(viewPager, "scaleX", startScaleFinal);
                                ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(viewPager, "scaleY", startScaleFinal);

                                as.play(containAlphaAnimator).with(animatorX).with(animatorY).with(animatorScaleX).with(animatorScaleY);
                            } else {
                                //the selected photoview is beyond the mobile screen display
                                //so it just fade out
                                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(viewPager, "alpha", 0.1f);
                                as.play(alphaAnimator).with(containAlphaAnimator);
                            }
                            as.setDuration(mShortAnimationDuration);
                            as.setInterpolator(new DecelerateInterpolator());
                            as.addListener(new AnimatorListenerAdapter() {

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    viewPager.clearAnimation();
                                    viewPager.setVisibility(View.GONE);
                                    mCurrentAnimator = null;
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                    viewPager.clearAnimation();
                                    viewPager.setVisibility(View.GONE);
                                    mCurrentAnimator = null;
                                }
                            });
                            as.start();
                            mCurrentAnimator = as;

                        }
                    });

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
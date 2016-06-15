package com.ycl.yuesport.myview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ycl.yuesport.R;
import com.ycl.yuesport.utils.Logger;

import java.util.ArrayList;

/**
 * 菜单控件头部，封装了下拉动画，动态生成头部按钮个数
 */

public class ExpandTabView extends LinearLayout implements OnDismissListener {

    private ToggleButton selectedButton;
    private ArrayList<String> mTextArray = new ArrayList<String>();  //分类栏标题数组
    private ArrayList<RelativeLayout> mViewArray = new ArrayList<RelativeLayout>();
    private ArrayList<ToggleButton> mToggleButton = new ArrayList<ToggleButton>();
    private Context mContext;
    private final int SMALL = 0;
    private int displayWidth;
    private int displayHeight;
    private PopupWindow popupWindow;
    private int selectPosition;

    public ExpandTabView(Context context) {
        super(context);
        init(context);
    }

    public ExpandTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 根据选择的位置设置tabitem显示的值
     */
    public void setTitle(String valueText, int position) {
        if (position < mToggleButton.size()) {
            mToggleButton.get(position).setText(valueText);
        }
    }


    /**
     * 根据选择的位置设置tabitem显示的图片
     */
    public void setPhoto(int valuePhoto, int position) {
        if (position < mToggleButton.size()) {

            Logger.i("valuePhoto:" + valuePhoto);
            //设置toggleButton左，上，右，下的图标
            mToggleButton.get(position).setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    /**
     * 根据选择的位置获取tabitem显示的图片
     */
    public Drawable getPhoto(int position) {
        if (position < mToggleButton.size()) {
            Drawable[] drawables = mToggleButton.get(position).getCompoundDrawables();
            Logger.i("drawables[0]" + drawables[0]);
            return drawables[0];
        }
        return null;
    }


    /**
     * 根据选择的位置获取tabitem显示的值
     */
    public String getTitle(int position) {
        if (position < mToggleButton.size() && mToggleButton.get(position).getText() != null) {
            return mToggleButton.get(position).getText().toString();
        }
        return "";
    }

    /**
     * 设置tabitem的个数和初始值
     *
     * @param textArray 标题数组
     * @param viewArray 弹出框popupWindow数组
     * @param photo     图标数组
     */
    public void setValue(ArrayList<String> textArray, ArrayList<View> viewArray, int[] photo) {
        if (mContext == null) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mTextArray = textArray;
        for (int i = 0; i < viewArray.size(); i++) {
            final RelativeLayout r = new RelativeLayout(mContext);
            int maxHeight = (int) (displayHeight * 0.58);
            RelativeLayout.LayoutParams rlparams;
            if (i == 0) {
                //地区popupWindow高度太高，设置最大高度
                rlparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, maxHeight);
            } else
                rlparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rlparams.leftMargin = 10;
            rlparams.rightMargin = 10;
            r.addView(viewArray.get(i), rlparams);
            mViewArray.add(r);
            r.setTag(SMALL);
            ToggleButton tButton = (ToggleButton) inflater.inflate(R.layout.toggle_button, this, false);
            tButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            addView(tButton);
            View line = new TextView(mContext);
            line.setBackgroundResource(R.color.backgroundcolor);
            if (i < viewArray.size() - 1) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(2, ViewGroup.LayoutParams.MATCH_PARENT);
                addView(line, lp);
            }
            mToggleButton.add(tButton);
            tButton.setTag(i);
            tButton.setText(mTextArray.get(i));
            tButton.setTextSize(12);
            Logger.i("ExpandTab:  mTextArray.get(i) " + mTextArray.get(i));
            r.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    onPressBack();
                }
            });

            r.setBackgroundColor(mContext.getResources().getColor(R.color.popup_main_background));
            tButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    // initPopupWindow();
                    ToggleButton tButton = (ToggleButton) view;

                    if (selectedButton != null && selectedButton != tButton) {
                        selectedButton.setChecked(false);
                    }
                    selectedButton = tButton;
                    selectPosition = (Integer) selectedButton.getTag();
                    startAnimation();
                    if (mOnButtonClickListener != null && tButton.isChecked()) {
                        mOnButtonClickListener.onClick(selectPosition);
                    }
                }
            });
        }
    }

    /**
     * 启动弹出框
     */
    private void startAnimation() {

        if (popupWindow == null) {
            popupWindow = new PopupWindow(mViewArray.get(selectPosition), displayWidth, displayHeight);
            popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(true);
        }

        if (selectedButton.isChecked()) {
            if (!popupWindow.isShowing()) {
                showPopup(selectPosition);
            } else {
                popupWindow.setOnDismissListener(this);
                popupWindow.dismiss();
                hideView();
            }
        } else {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
                hideView();
            }
        }
    }


    /**
     * 显示弹出框
     *
     * @param position 弹出框popupWindow数组下标位置
     */
    private void showPopup(int position) {
        View tView = mViewArray.get(selectPosition).getChildAt(0);
        if (tView instanceof ViewBaseAction) {
            ViewBaseAction f = (ViewBaseAction) tView;
            f.show();
        }
        if (popupWindow.getContentView() != mViewArray.get(position)) {
            popupWindow.setContentView(mViewArray.get(position));
        }
        popupWindow.showAsDropDown(this, 0, 0);
    }

    /**
     * 如果菜单成展开状态，则让菜单收回去
     */
    public boolean onPressBack() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            hideView();
            if (selectedButton != null) {
                selectedButton.setChecked(false);
            }
            return true;
        } else {
            return false;
        }

    }

    /**
     * 隐藏弹出框
     */
    private void hideView() {
        View tView = mViewArray.get(selectPosition).getChildAt(0);
        if (tView instanceof ViewBaseAction) {
            ViewBaseAction f = (ViewBaseAction) tView;
            f.hide();
        }
    }

    /**
     * 获取设备猖狂
     *
     * @param context 上下文
     */
    private void init(Context context) {
        mContext = context;
        displayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
        displayHeight = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
        setOrientation(LinearLayout.HORIZONTAL);
        //设置toggleButton的左端图标
        //	mToggleButton.get(1).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_category_all, 0,R.drawable.ic_routelist_dropdown_arrow,0);
    }

    public void onDismiss() {
        showPopup(selectPosition);
        popupWindow.setOnDismissListener(null);
    }

    private OnButtonClickListener mOnButtonClickListener;

    /**
     * 设置tabitem的点击监听事件
     */
    public void setOnButtonClickListener(OnButtonClickListener l) {
        mOnButtonClickListener = l;
    }

    /**
     * 自定义tabitem点击回调接口
     */
    public interface OnButtonClickListener {
        public void onClick(int selectPosition);
    }

}

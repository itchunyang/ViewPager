package com.itchunyang.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by luchunyang on 2016/12/8.
 *
 * 切换效果
 * 1.只需要用户在切换时，拿到当前的View和下一个View，然后添加动画是不是就可以了
 * 2.
 *
 *
 * 1.打开没有任何滑动
 *      onPageScrolled: position=0 offset=0.0 offsetPixels=0
 *
 * 2.index=0 ---> index=1
 *      onPageScrolled: position=0 offset(0->1) offsetPixels(0-900)
 *      onPageScrolled: position=1 offset=0.0 offsetPixels=0
 *
 *   index=1 ---> index=0
 *      onPageScrolled: position=0 offset(1->0) offsetPixels(900-0)
 *
 *
 * 3.index=1 ---> index=2
 *      onPageScrolled: position=1 offset(0->1) offsetPixels(0-900)
 *      onPageScrolled: position=2 offset=0.0 offsetPixels=0
 *
 *   index=2 ---> index=1
 *      onPageScrolled: position=1 offset(1->0) offsetPixels(900-0)
 *
 *
 * 4.index=2 ---> index=3
 *      onPageScrolled: position=2 offset(0->1) offsetPixels(0-900)
 *      onPageScrolled: position=3 offset=0.0 offsetPixels=0
 *
 *   index=3 ---> index=2
 *      onPageScrolled: position=2 offset(1->0) offsetPixels(900-0)
 *
 *
 * 5.index=3 ---> index=4
 *      onPageScrolled: position=3 offset(0->1) offsetPixels(0-900)
 *      onPageScrolled: position=4 offset=0.0 offsetPixels=0
 *
 *   index=4 ---> index=3
 *      onPageScrolled: position=3 offset(1->0) offsetPixels(900-0)
 *
 * 结论:
 * 滑动到下一页时,position为当前页位置；滑动到上一页：position为当前页-1
 * positionOffset 滑动到下一页，[0,1)区间上变化；滑动到上一页：(1,0]区间上变化
 * positionOffsetPixels这个和positionOffset很像：滑动到下一页，[0,宽度)区间上变化；滑动到上一页：(宽度,0]区间上变化
 *
 *
 */

public class MyAnimationPager extends ViewPager {

    private View mLeftView;
    private View mRightView;
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();


    public static final String TAG = MyAnimationPager.class.getSimpleName();

    public MyAnimationPager(Context context) {
        super(context);
    }

    public MyAnimationPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private final float SCALE_MAX = 0.5f;
    private float scale;
    private float trans;

    /**
     *
     * @param position 当前页面，及你点击滑动的页面
     * @param offset arg1:当前页面偏移的百分比
     * @param offsetPixels 当前页面偏移的像素位置
     */
    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        Log.i(TAG, "onPageScrolled: position=" + position + " offset=" + offset + " offsetPixels=" + offsetPixels);


        mLeftView = findViewFromObject(position);
        mRightView = findViewFromObject(position+1);

        if(mLeftView != null){
            mLeftView.bringToFront();
        }

        if(mRightView != null){
            mRightView.setAlpha(offset);
            scale = (1-SCALE_MAX) * offset + SCALE_MAX;

            trans = offsetPixels - getWidth();

            if(scale >= 0.999)
                scale = 1;
            System.out.println("scale="+scale);
            mRightView.setScaleX(scale);
            mRightView.setScaleY(scale);

            // x偏移量： 如果手指从右到左的滑动（切换到后一个）：0-900 如果手指从左到右的滑动（切换到前一个）：900-0
//            System.out.println("----->"+trans);
//            mRightView.setTranslationX(trans);
        }
        super.onPageScrolled(position, offset, offsetPixels);
    }

    public void setObjectForPosition(View view, int position)
    {
        mChildrenViews.put(position, view);
    }

    public View findViewFromObject(int position)
    {
        return mChildrenViews.get(position);
    }

}

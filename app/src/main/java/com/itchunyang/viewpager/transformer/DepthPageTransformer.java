package com.itchunyang.viewpager.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by luchunyang on 2016/12/8.
 * 1.不滑动
 *      viewTag=0 position=0.0
 *      viewTag=1 position=1.0
 *
 * 2.index=0 ---> index=1
 *      viewTag=0 position=0->-1
 *      viewTag=1 position=1->0
 *
 *      index=1 ---> index=0
 *      viewTag=2 position=1->2
 *      viewTag=1 position=0->1
 *      viewTag=0 position=-1->0
 *
 * 3.index=1 ---> index=2
 *      viewTag=0 position=-1->-2
 *      viewTag=1 position=0->-1
 *      viewTag=2 position=1->0
 *
 *      index=2 ---> index=1
 *      viewTag=2 position=0->1
 *      viewTag=1 position=-1->0
 *      viewTag=3 position=1->2
 *
 *
 * 4.index=2 ---> index=3
 *      viewTag=1 position=-1->-2
 *      viewTag=2 position=0->-1
 *      viewTag=3 position=1->0
 *
 *      index=3 ---> index=2
 *      viewTag=2 position=-1->0
 *      viewTag=3 position=0->1
 *      viewTag=4 position=1->2
 *
 */

public class DepthPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;

    //在ViewPager滑动时，内存中存活的Page都会执行transformPage方法，在滑动过程中涉及到两个Page，当前页和下一页，
    //而它们 的position值是相反的（因为是相对运动,一个滑入一个滑出），比如，页面A向右滑动到屏幕一半，页面B也正好处于一半的位置，
    //那么A和B的 position为：0.5 和 -0.5
    @Override
    public void transformPage(View view, float position) {
        ImageView iv = (ImageView) view;

        System.out.println("viewTag=" + iv.getTag() + " position=" + position);
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)//超出屏幕
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 0) { // [-1,0]//当前的左边控件 [-1,0] 越往左滑，position值越小
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1) { // (0,1]//当前的右边控件，[0,1]越往左滑，Position值越小
            // Fade the page out.
            view.setAlpha(1 - position);

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}

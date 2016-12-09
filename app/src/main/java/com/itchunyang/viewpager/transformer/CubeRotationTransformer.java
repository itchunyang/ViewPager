package com.itchunyang.viewpager.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by luchunyang on 2016/12/9.
 */

public class CubeRotationTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        if(-1 <= position && position <= 0){
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight() / 2.0f);
            page.setRotationY(position * 90);
        }else if(position <= 1){
            page.setPivotX(0);
            page.setPivotY(page.getHeight() / 2.0f);
            page.setRotationY(90 * position);
        }
    }
}

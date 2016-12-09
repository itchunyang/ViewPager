package com.itchunyang.viewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.itchunyang.viewpager.transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager自带了一个setPageTransformer用于设置切换动画~
 * 该方法在SDK11以下的版本不起作用
 */

public class AnimationPagerActivity1 extends AppCompatActivity {

    private ViewPager vp;
    private List<View> views = new ArrayList<>();
    private int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner5, R.drawable.banner6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_pager1);

        vp = (ViewPager) findViewById(R.id.viewPager);

        init();
        
        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }
        });

//        vp.setPageTransformer(true,new DepthPageTransformer());
//        vp.setPageTransformer(true,new CubeRotationTransformer());
        vp.setPageTransformer(true,new ZoomOutPageTransformer());

    }

    private void init() {
        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(images[i]);
            iv.setTag(i);
            views.add(iv);
        }
    }

}

package com.itchunyang.viewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * PagerTabStrip是ViewPager的一个关于当前页面、上一个页面和下一个页面的一个可交互的指示器
 * 它的 android:layout_gravity 属性设置为TOP或BOTTOM来将它显示在ViewPager的顶部或底部
 *
 * PagerTitleStrip是不可交互的!
 * 也就是Tab是不可以点击的
 *
 */

public class SimpleTabPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerTabStrip tabStrip;
    private List<View> views;
    private PagerAdapter adapter;
    private int[] images = {R.drawable.meinv1,R.drawable.meinv2,R.drawable.meinv3,R.drawable.meinv4};
    private String[] title = {"News","Video","ShenZhen","Android"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab_pager);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabStrip = (PagerTabStrip) findViewById(R.id.tab);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(views == null)
            views = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setBackgroundResource(images[i]);
            views.add(iv);
        }

        if(adapter == null){
            adapter = new PagerAdapter() {
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

                @Override
                public CharSequence getPageTitle(int position) {
                    return title[position];
                }
            };
        }

        viewPager.setAdapter(adapter);

        //设置tab的背景色
//        tabStrip.setTabIndicatorColor(Color.RED);

        //默认情况下,整个的tabStrip底部会有一条和它一样宽的横线
//        tabStrip.setDrawFullUnderline(false);
        tabStrip.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        tabStrip.setTextSize(TypedValue.COMPLEX_UNIT_PX,25);
    }
}

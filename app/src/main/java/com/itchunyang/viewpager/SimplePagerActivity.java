package com.itchunyang.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchunyang on 2016/12/8.
 */

public class SimplePagerActivity extends AppCompatActivity {

    public static final String TAG = SimplePagerActivity.class.getSimpleName();
    private Handler handler = new Handler();
    private AutoPlay autoPlay = new AutoPlay();

    private ViewPager vp;
    private int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner5,R.drawable.banner6};
    private List<View> viewList = new ArrayList<>();
    private List<View> dotsView = new ArrayList<>();
    private MyPagerAdapter adapter;
    private LinearLayout dotsGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        vp = (ViewPager) findViewById(R.id.viewPager);
        adapter = new MyPagerAdapter();
        dotsGroup = (LinearLayout) findViewById(R.id.dotsGroup);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(SimplePagerActivity.this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(images[i]);
            viewList.add(iv);

            ImageView dotView = new ImageView(this);
            if(i == 0)
                dotView.setBackgroundResource(R.drawable.dot_focused);
            else
                dotView.setBackgroundResource(R.drawable.dot_normal);

            dotsView.add(dotView);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            dotsGroup.addView(dotView,layoutParams);
        }
        vp.setAdapter(adapter);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i(TAG, "onPageScrolled: position=" + position + " offset=" + positionOffset + " offsetPixels=" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsView.size(); i++) {
                    if(position == i)
                        dotsView.get(i).setBackgroundResource(R.drawable.dot_focused);
                    else
                        dotsView.get(i).setBackgroundResource(R.drawable.dot_normal);
                }
            }

            //有三种状态（0，1，2）。arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i(TAG, "onPageScrollStateChanged: "+state);
            }
        });

        handler.postDelayed(autoPlay,6000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(autoPlay);
    }

    public void add(View view) {
        ImageView iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setImageResource(R.drawable.banner1);
        viewList.add(iv);
        adapter.notifyDataSetChanged();
    }

    //vp只会维持2-3个view
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
//            Log.i(TAG, "getCount: ");
            return viewList.size();
        }

        //isViewFromObject( ):判断instantiateItem(ViewGroup, int)函数所返回来的Key与一个页面视图是否是
        //代表的同一个视图(即它俩是否是对应的，对应的表示同一个View),通常我们直接写return view == object;就可以了
        @Override
        public boolean isViewFromObject(View view, Object object) {
//            Log.i(TAG, "isViewFromObject: ");
            return view == object;
        }

        //将给定位置的view添加到ViewGroup(容器)中,创建并显示出来
        //返回一个代表新增页面的Object(key),通常都是直接返回view本身就可以了.当然你也可以自定义自己的key,但是key和每个view要一一对应的关系
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i(TAG, "instantiateItem: position=" + position);
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i(TAG, "destroyItem: position=" + position);
            container.removeView((ImageView) object);
        }
    }


    class AutoPlay implements Runnable{
        @Override
        public void run() {
            int id = vp.getCurrentItem();
            if((++id) == viewList.size())
                id = 0;
            Log.i(TAG, "run: 跳转到" + id);
            vp.setCurrentItem(id);
            handler.postDelayed(this,6000);
        }
    }
}

package com.itchunyang.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class AnimationPagerActivity extends AppCompatActivity {

    private List<View> views;
    private int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner5, R.drawable.banner6};
    private MyAnimationPager viewPager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_pager);

        viewPager = (MyAnimationPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (views == null) {
            views = new ArrayList<>();
            for (int i = 0; i < images.length; i++) {
                MyImageView iv = new MyImageView(this,""+i);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setImageResource(images[i]);
                views.add(iv);
            }
        }

        if (adapter == null) {
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
                    viewPager.setObjectForPosition(views.get(position), position);
                    return views.get(position);
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(views.get(position));
                }
            };
        }

        viewPager.setAdapter(adapter);
    }

    public void test(View view) {
        Log.i("ViewPager", "test: " + viewPager.getChildCount());
    }

    class MyImageView extends ImageView{

        private String tag;
        public MyImageView(Context context,String tag) {
            super(context);
            this.tag = tag;
        }

        public MyImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public String getTag(){
            return tag;
        }

    }
}

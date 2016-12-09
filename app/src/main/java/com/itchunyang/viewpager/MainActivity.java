package com.itchunyang.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 切换效果
 * 1.只需要用户在切换时，拿到当前的View和下一个View，然后添加动画是不是就可以了
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void simplePager(View view) {
        startActivity(new Intent(this,SimplePagerActivity.class));
    }

    public void tabPager(View view) {
        startActivity(new Intent(this,SimpleTabPagerActivity.class));
    }

    public void fragmentPager(View view) {
        startActivity(new Intent(this,FragmentPagerActivity.class));
    }

    public void animationPager(View view) {
        startActivity(new Intent(this,AnimationPagerActivity.class));
    }

    public void animationPager1(View view) {
        startActivity(new Intent(this,AnimationPagerActivity1.class));
    }
}

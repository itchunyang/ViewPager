package com.itchunyang.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.itchunyang.viewpager.fragment.ConfigFragment;
import com.itchunyang.viewpager.fragment.LoginFragment;
import com.itchunyang.viewpager.fragment.QueryFragment;
import com.itchunyang.viewpager.fragment.SetFragment;

public class FragmentPagerActivity extends AppCompatActivity {

    private Fragment[] fragments = new Fragment[]{new SetFragment(),new LoginFragment(),new QueryFragment(),new ConfigFragment()};
    private ViewPager viewPager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_pager);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //该类更专注于每一页均为 Fragment 的情况。
        //该类内的每一个生成的 Fragment 都将保存在内存之中，因此适用于那些相对静态的页，数量也比较少的那种；
        //默认会保存前一个后一个,否则只会调用onDestroyView 不会调用onDestroy

        //如果需要处理有很多页， 并且数据动态性较大、占用内存较多的情况，应该使用FragmentStatePagerAdapte
        //默认会保存前一个后一个,除此之外会销毁 onDestory()
        if(adapter == null){
            adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragments[position];
                }

                @Override
                public int getCount() {
                    return fragments.length;
                }
            };
        }

        viewPager.setAdapter(adapter);
    }
}

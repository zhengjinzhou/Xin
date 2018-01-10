package com.zhou.xin.ui.activity.love;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GuildActivity extends BaseActivity {

    @BindView(R.id.viewPager) ViewPager viewPager;
    private List<View> list;

    @Override
    protected int getLayout() {
        return R.layout.activity_guild;
    }

    @Override
    protected void init() {
        initViewpager();
    }

    private void initViewpager() {
        viewPager = findViewById(R.id.viewPager);
        list = new ArrayList<>();
        View view1 = getLayoutInflater().inflate(R.layout.guild1, null);
        View view2 = getLayoutInflater().inflate(R.layout.guild2, null);
        View view3 = getLayoutInflater().inflate(R.layout.guild3, null);

        list.add(view1);
        list.add(view2);
        list.add(view3);

        viewPager.setAdapter(new MyPagerAdapter());
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }
    }
}

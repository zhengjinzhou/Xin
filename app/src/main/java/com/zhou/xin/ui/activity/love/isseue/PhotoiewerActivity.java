package com.zhou.xin.ui.activity.love.isseue;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PhotoiewerActivity extends BaseActivity {

    @BindView(R.id.count)
    TextView count;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private List<String> urls;

    private int pos;

    public static Intent newIntent(Context context, String[] urls, int pos) {

        Intent intent = new Intent(context, PhotoiewerActivity.class);
        intent.putExtra("urls",urls);
        intent.putExtra("pos",pos);
        return intent;
    }

    private void bindIntent() {
        if (getIntent() != null) {
            urls = Arrays.asList(getIntent().getStringArrayExtra("urls")) ;
            pos = getIntent().getIntExtra("pos",0);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_photoiewer;
    }

    @Override
    protected void init() {
        bindIntent();
        initViewPager();
    }

    @OnClick(R.id.back) void onClick(View v){
        finish();
    }

    private void initViewPager(){

        if(urls == null) return;

        viewPager.setAdapter(new PhotoViewAdapter());
        viewPager.setCurrentItem(pos);
        int index = pos + 1;
        count.setText(String.format("%d / %d",index,urls.size()));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                count.setText(String.format("%d / %d",position + 1,urls.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class PhotoViewAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(PhotoiewerActivity.this);
            photoView.enable();
            String url = Constant.URL + urls.get(position);
            Glide.with(PhotoiewerActivity.this).load(url).into(photoView);
            container.addView(photoView);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }
}

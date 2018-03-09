package com.zhou.xin.ui.activity.love;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhou.xin.R;
import com.zhou.xin.adapter.base.CommonAdapter;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.bean.BaseInfo;
import com.zhou.xin.bean.GuildBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GuildActivity extends BaseActivity {

    @BindView(R.id.tv_head)
    TextView tv_head;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;


    @Override
    protected int getLayout() {
        return R.layout.activity_guild;
    }

    @Override
    protected void init() {
        tv_head.setText("帮助中心");
        initRecycle();
    }

    /**
     * recyclerView
     */
    private void initRecycle() {
        final List<GuildBean> data = new ArrayList<>();
        data.add(new GuildBean(getString(R.string.title1), getString(R.string.desc1)));
        data.add(new GuildBean(getString(R.string.title2), getString(R.string.desc2)));
        data.add(new GuildBean(getString(R.string.title3), getString(R.string.desc2)));
        data.add(new GuildBean(getString(R.string.title4), getString(R.string.desc4)));
        data.add(new GuildBean(getString(R.string.title5), getString(R.string.desc5)));
        data.add(new GuildBean(getString(R.string.title6), getString(R.string.desc6)));
        data.add(new GuildBean(getString(R.string.title7), getString(R.string.desc7)));
        data.add(new GuildBean(getString(R.string.title8), getString(R.string.desc8)));
        data.add(new GuildBean(getString(R.string.title9), getString(R.string.desc9)));
        data.add(new GuildBean(getString(R.string.title10), getString(R.string.desc10)));

        CommonAdapter adapter = new CommonAdapter<GuildBean>(this, R.layout.recycle_guild, data) {
            @Override
            public void convert(ViewHolder holder, final GuildBean s, final int position) {
                holder.setText(R.id.tv_des, s.getTitle());
                holder.setOnClickListener(R.id.rl_guild, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(GuildInfoActivity.newIntent(getApplicationContext(), s.getTitle(), s.getDesc()));
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}

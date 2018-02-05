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
        data.add(new GuildBean("如何在应用中举报和反馈?", ""));
        data.add(new GuildBean("如何进行线下活动?", "应用中的主界面里有 ‘线下活动’可点击进去，进入的界面有近期的开展的线下活动，您可以挑选您喜欢的活动，再次点击进入，按照流程报名填写信息报名参加即可 "));
        data.add(new GuildBean("本应用只能和一人聊天么?", ""));
        data.add(new GuildBean("本应用中的红包是怎么回事?", "本应用中的红包，不会绑定在很账号中，仅是您与支付宝的绑定，您账户中的余额会自动转入到支付宝中"));
        data.add(new GuildBean("如何联系我们?", "请添加我的qq或者微信号：1457594845"));

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

package com.zhou.xin.ui.activity.huanxin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.zhou.xin.R;
import com.zhou.xin.adapter.NewFriendsMsgAdapter;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.db.InviteMessgeDao;
import com.zhou.xin.domain.InviteMessage;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class NewFriendsMsgActivity extends BaseActivity {

    @BindView(R.id.list) ListView listView;

    @Override
    public int getLayout() {
        return R.layout.activity_new_friends_msg;
    }

    @Override
    public void init() {
        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        Collections.reverse(msgs);

        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);
    }

    public void back(View view) {
        finish();
    }

}

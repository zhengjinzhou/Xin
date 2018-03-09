package com.zhou.xin.ui.activity.huanxin;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.hyphenate.easeui.domain.EaseUser;
import com.zhou.xin.R;
import com.zhou.xin.adapter.NewFriendsMsgAdapter;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.base.DemoHelper;
import com.zhou.xin.db.InviteMessgeDao;
import com.zhou.xin.domain.InviteMessage;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 申请与通知界面
 */
public class NewFriendsMsgActivity extends BaseActivity {

    @BindView(R.id.list)
    ListView listView;

    @Override
    public int getLayout() {
        return R.layout.activity_new_friends_msg;
    }

    @Override
    public void init() {
        //新添加的，用于控制能不能添加好友
        Map<String, EaseUser> contactList = DemoHelper.getInstance().getContactList();
        int size = contactList.size();
        if (contactList.get("admin") != null) {
            size = size - 1;
        }
        Log.d("申请与通知界面", "init: "+size);
        if (size >= 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("温馨提示");
            builder.setMessage("本应用仅支持一对一聊天，您如果需要添加新的好友，请将原来的好友删除或者拉黑。对您造成影响真的抱歉，感谢您的使用");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setCancelable(false).create();
            builder.show();
        } else {
            InviteMessgeDao dao = new InviteMessgeDao(this);
            List<InviteMessage> msgs = dao.getMessagesList();
            Collections.reverse(msgs);

            NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
            listView.setAdapter(adapter);
            dao.saveUnreadMessageCount(0);
        }
    }

    public void back(View view) {
        finish();
    }

}

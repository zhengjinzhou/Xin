package com.zhou.xin.ui.activity.huanxin;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.ToastUtil;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 黑名单界面
 */
public class BlacklistActivity extends BaseActivity {

    private BlacklistAdapter adapter;

    @BindView(R.id.list) ListView listView;

    @Override
    public int getLayout() {
        return R.layout.activity_blacklist;
    }

    @Override
    public void init() {
// get blacklist from local databases
        List<String> blacklist = EMClient.getInstance().contactManager().getBlackListUsernames();

        // show the blacklist
        if (blacklist != null) {
            Collections.sort(blacklist);
            adapter = new BlacklistAdapter(this, blacklist);
            listView.setAdapter(adapter);
        }

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.em_remove_from_blacklist, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.remove) {
            final String tobeRemoveUser = adapter.getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
            // remove user out from blacklist
            removeOutBlacklist(tobeRemoveUser);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * remove user out from blacklist
     *
     * @param tobeRemoveUser
     */
    void removeOutBlacklist(final String tobeRemoveUser) {
        Log.d("", "removeOutBlacklist: 11111111111111111111111");

        ToastUtil.show(getApplicationContext(),"暂不支持移出黑名单！");

        /*final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getString(R.string.be_removing));
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    EMClient.getInstance().contactManager().removeUserFromBlackList(tobeRemoveUser);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            adapter.remove(tobeRemoveUser);
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), R.string.Removed_from_the_failure, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();*/
    }

    public void toGroupDetails(View view) {
    }

    /**
     * adapter
     *
     */
    private class BlacklistAdapter extends ArrayAdapter<String> {

        public BlacklistAdapter(Context context, List<String> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.ease_row_contact, null);
            }
            String username = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            ImageView avatar = (ImageView) convertView.findViewById(R.id.avatar);

            EaseUserUtils.setUserAvatar(getContext(), username, avatar);
            EaseUserUtils.setUserNick(username, name);

            return convertView;
        }

    }
}

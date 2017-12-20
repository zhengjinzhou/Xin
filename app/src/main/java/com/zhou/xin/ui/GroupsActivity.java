package com.zhou.xin.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.hyphenate.chat.EMGroup;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;

import java.util.List;

public class GroupsActivity extends BaseActivity {

    public static final String TAG = "GroupsActivity";
    private ListView groupListView;
    protected List<EMGroup> grouplist;
    //private GroupAdapter groupAdapter;
    private InputMethodManager inputMethodManager;
    public static GroupsActivity instance;
    private View progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public int getLayout() {
        return R.layout.activity_groups;
    }

    @Override
    public void initV() {

    }

    @Override
    public void onResume() {
        //refresh();
        super.onResume();
    }


}

package com.zhou.xin.ui.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseFragment;
import com.zhou.xin.base.DemoHelper;
import com.zhou.xin.bean.GenderBean;
import com.zhou.xin.swipe.SwipeFlingAdapterView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhou on 2017/12/29.
 */

public class HomeFragment extends BaseFragment implements SwipeFlingAdapterView.onFlingListener,
        SwipeFlingAdapterView.OnItemClickListener, View.OnClickListener {


    @BindView(R.id.swipe_view) SwipeFlingAdapterView swipeView;
    @BindView(R.id.swipeLeft) View vLeft;
    @BindView(R.id.swipeRight) View vRight;
    @BindView(R.id.back) ImageView back;
    @BindView(R.id.tv_head) TextView tv_head;


    private static final String TAG = "HomeFragment";
    int [] headerIcons = {
            R.drawable.i1,
            R.drawable.i2,
            R.drawable.i3,
            R.drawable.i4,
            R.drawable.i5,
            R.drawable.i6
    };

    Random ran = new Random();
    private int cardWidth;
    private int cardHeight;

    private InnerAdapter adapter;
    private List<String> nameList;
    private List<String> cityList;
    private List<String> edusList;
    private List<String> yearsList;
    private List<String> photoList;


    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(View v) {

        back.setVisibility(View.GONE);
        tv_head.setText("主页");
        getInfo();

        nameList = new ArrayList<>();
        cityList = new ArrayList<>();
        edusList = new ArrayList<>();
        yearsList = new ArrayList<>();
        photoList = new ArrayList<>();
        /*nameList.add("张三");nameList.add("李四");nameList.add("王小二"); nameList.add("王五");nameList.add("汇一城");nameList.add("他");
        cityList.add("背景");cityList.add("背景");cityList.add("背r景");cityList.add("背e景");cityList.add("背d景");cityList.add("背景");
        edusList.add("奔溃");edusList.add("奔溃");edusList.add("奔溃");edusList.add("奔g溃");edusList.add("奔4溃");edusList.add("奔2溃");
        yearsList.add("1年");yearsList.add("2年");yearsList.add("3年");yearsList.add("14年");yearsList.add("1年");yearsList.add("1年");*/
        initView();

    }

    /**
     * 获取异性信息
     */
    private void getInfo() {
        if (App.getInstence().getUserInfo() != null){
            String token = App.getInstence().getUserInfo().getToken();
            Log.d(TAG, "getInfo: "+token);
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("token",token)
                    .build();
            Request request = new Request.Builder().url(Constant.ISOMERISM_URL).post(body).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "获取异性信息 onFailure: "+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                   // Log.d(TAG, "获取异性信息 onResponse: "+response.body().string());
                    String string = response.body().string();
                    setResult(string);
                }
            });
        }
    }

    //回调
    private void setResult(String data) {
        Gson gson = new Gson();
        GenderBean genderBean = gson.fromJson(data, GenderBean.class);
        List<GenderBean.MemberListBean> memberList = genderBean.getMemberList();
        Log.d(TAG, "setResult:222222222222 "+memberList.size());
        for (int i = 0; i< memberList.size();i++){
            GenderBean.MemberListBean memberListBean = memberList.get(i);
           /* memberListBean.getAge();
            memberListBean.getCity().getName();
            memberListBean.getNickname();
            memberListBean.getMajor().getMajorName();*/
            photoList.add(memberListBean.getPhotoPath());
            nameList.add(memberListBean.getRealname());
            cityList.add(memberListBean.getCity().getName());
            yearsList.add(memberListBean.getAge()+"岁");
            edusList.add(memberListBean.getMajor().getMajorName());

            Log.d(TAG, "setResult: "+memberListBean.getRealname());
            loadData();
        }
    }

    private void initView() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        cardWidth = (int) (dm.widthPixels - (2 * 18 * density));
        cardHeight = (int) (dm.heightPixels - (338 * density));

        if (swipeView != null) {
            swipeView.setIsNeedSwipe(true);
            swipeView.setFlingListener(this);
            swipeView.setOnItemClickListener(this);

            adapter = new InnerAdapter();
            swipeView.setAdapter(adapter);
        }

        if (vLeft != null) {
            vLeft.setOnClickListener(this);
        }
        if (vRight != null) {
            vRight.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swipeLeft:
                swipeView.swipeLeft();
                //swipeView.swipeLeft(250);
                break;
            case R.id.swipeRight:
                swipeView.swipeRight();
                //swipeView.swipeRight(250);
        }
    }

    @Override
    public void onItemClicked(MotionEvent event, View v, Object dataObject) {
        Log.d(TAG, "onItemClicked: "+dataObject.toString());

    }

    @Override
    public void removeFirstObjectInAdapter() {
        adapter.remove(0);
    }

    @Override
    public void onLeftCardExit(Object dataObject) {
        //左画以及点击dislike  不做处理
        Log.d(TAG, "onLeftCardExit: "+dataObject.toString());

    }

    @Override
    public void onRightCardExit(Object dataObject) {
        //右划以及点击Like  添加好友
        //先获取当前用户名
        Talent talent = (Talent) dataObject;

    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        if (itemsInAdapter == 3) {
            loadData();
        }
    }

    @Override
    public void onScroll(float progress, float scrollXProgress) {

    }

    @SuppressLint("StaticFieldLeak")
    private void loadData() {
        new AsyncTask<Void, Void, List<Talent>>() {
            @Override
            protected List<Talent> doInBackground(Void... params) {
                ArrayList<Talent> list = new ArrayList<>(nameList.size());
                Talent talent;
                for (int i = 0; i < nameList.size(); i++) {
                    talent = new Talent();
                    //talent.headerIcon = headerIcons[i % headerIcons.length];
                    talent.headerIcon = Constant.URL + photoList.get(i % photoList.size());
                    talent.nickname = nameList.get(i);
                    talent.cityName = cityList.get(i);
                    talent.educationName = edusList.get(i);
                    talent.workYearName = yearsList.get(i);

                    list.add(talent);
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<Talent> list) {
                super.onPostExecute(list);
                adapter.addAll(list);
            }
        }.execute();
    }

    public static class Talent {
        public String headerIcon;
        public String nickname;
        public String cityName;
        public String educationName;
        public String workYearName;
    }

    private class InnerAdapter extends BaseAdapter {

        ArrayList<Talent> objs;

        public InnerAdapter() {
            objs = new ArrayList<>();
        }

        public void addAll(Collection<Talent> collection) {
            if (isEmpty()) {
                objs.addAll(collection);
                notifyDataSetChanged();
            } else {
                objs.addAll(collection);
            }
        }

        public void clear() {
            objs.clear();
            notifyDataSetChanged();
        }

        public boolean isEmpty() {
            return objs.isEmpty();
        }

        public void remove(int index) {
            if (index > -1 && index < objs.size()) {
                objs.remove(index);
                notifyDataSetChanged();
            }
        }


        @Override
        public int getCount() {
            return objs.size();
        }

        @Override
        public Talent getItem(int position) {
            if(objs==null ||objs.size()==0) return null;
            return objs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // TODO: getView
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            Talent talent = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_new_item, parent, false);
                holder  = new ViewHolder();
                convertView.setTag(holder);
                convertView.getLayoutParams().width = cardWidth;
                holder.portraitView = (ImageView) convertView.findViewById(R.id.portrait);
                holder.portraitView.getLayoutParams().height = cardHeight;
                holder.nameView = (TextView) convertView.findViewById(R.id.name);
                holder.cityView = (TextView) convertView.findViewById(R.id.city);
                holder.eduView = (TextView) convertView.findViewById(R.id.education);
                holder.workView = (TextView) convertView.findViewById(R.id.work_year);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

           // holder.portraitView.setImageResource(talent.headerIcon);
            Glide.with(getContext()).load(talent.headerIcon).into(holder.portraitView);

            holder.nameView.setText(String.format("%s", talent.nickname));
            //holder.jobView.setText(talent.jobName);

            final CharSequence no = "暂无";

            holder.cityView.setHint(no);
            holder.cityView.setText(talent.cityName);
            holder.cityView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_location,0,0);

            holder.eduView.setHint(no);
            holder.eduView.setText(talent.educationName);
            holder.eduView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_edu,0,0);

            holder.workView.setHint(no);
            holder.workView.setText(talent.workYearName);
            holder.workView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_work_year,0,0);

            return convertView;
        }

    }

    private static class ViewHolder {
        ImageView portraitView;
        TextView nameView;
        TextView cityView;
        TextView eduView;
        TextView workView;
        CheckedTextView collectView;
        LinearLayout layout;
    }


    public void addContact(final String username){
        if(EMClient.getInstance().getCurrentUser().equals(username)){
            new EaseAlertDialog(getContext(), R.string.not_add_myself).show();
            return;
        }

        if(DemoHelper.getInstance().getContactList().containsKey(username)){
            //let the user know the contact already in your contact list
            if(EMClient.getInstance().contactManager().getBlackListUsernames().contains(username)){
                new EaseAlertDialog(getContext(), R.string.user_already_in_contactlist).show();
                return;
            }
            new EaseAlertDialog(getContext(), R.string.This_user_is_already_your_friend).show();
            return;
        }

        String stri = getResources().getString(R.string.Is_sending_a_request);

        new Thread(new Runnable() {
            public void run() {
                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(username, s);

                    String s1 = getResources().getString(R.string.send_successful);
                    Toast.makeText(getContext(), s1, Toast.LENGTH_LONG).show();

                } catch (final Exception e) {

                    String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                    Toast.makeText(getContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }).start();
    }

}

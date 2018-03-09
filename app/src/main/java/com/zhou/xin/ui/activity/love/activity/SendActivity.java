package com.zhou.xin.ui.activity.love.activity;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;
import com.zhou.xin.R;
import com.zhou.xin.adapter.base.BaseCommonAdapter;
import com.zhou.xin.adapter.base.CommonAdapter;
import com.zhou.xin.adapter.base.GridSpacingItemDecoration;
import com.zhou.xin.adapter.base.MultiItemCommonAdapter;
import com.zhou.xin.adapter.base.MultiItemTypeSupport;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.base.other.PermissionsResultListener;
import com.zhou.xin.utils.DateUtil;
import com.zhou.xin.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SendActivity extends BaseActivity {

    private static final String TAG = "SendActivity";
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.etTitle) EditText etTitle;
    @BindView(R.id.etTime1) EditText etTime1;
    @BindView(R.id.etTime2) EditText etTime2;
    @BindView(R.id.etPlace) EditText etPlace;
    @BindView(R.id.etDes) EditText etDes;
    @BindView(R.id.recycleView) RecyclerView photos;
    private TimePickerView pickerView;
    private BaseCommonAdapter adapter;
    private List<String> photosData;
    private boolean multiSelect = false;
    private int maxCount = 9;
    private int index = 0;
    private static final int PER_REQUEST_CODE = 1;
    private static final int REQUEST_CODE = 1000;

    @Override
    protected int getLayout() {
        return R.layout.activity_send;
    }

    @Override
    protected void init() {
        initPhotos();
        tv_head.setText("发布活动");
    }

    /**
     * 上传视频和照片
     */
    private void initPhotos() {
        photosData = new ArrayList<>();
        photosData.add("add");
        adapter = new BaseCommonAdapter<String>(this, R.layout.view_issue_photos, photosData) {
            @Override
            public void convert(ViewHolder holder, String s, final int position) {

                if (s.equals("add")) {
                    ImageView img = (ImageView) holder.getView(R.id.img);
                    img.setImageResource(R.drawable.opinion_add);
                    img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    holder.getView(R.id.del).setVisibility(View.GONE);

                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            multiSelect = true;
                            openPhotoPermissions();
                        }
                    });

                } else {
                    ImageView img = (ImageView) holder.getView(R.id.img);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(mContext).load(s).into(img);
                    holder.getView(R.id.del).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            multiSelect = false;
                            index = position;
                            openPhotoPermissions();
                        }
                    });
                }
            }
        };

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space_10);
        photos.setVisibility(View.VISIBLE);
        photos.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        photos.addItemDecoration(new GridSpacingItemDecoration(3, spacingInPixels, false));
        photos.setAdapter(adapter);
    }

    private void openPhotoPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            performRequestPermissions(getResources().getString(R.string.permission_desc)
                    , new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , PER_REQUEST_CODE, new PermissionsResultListener() {
                        @Override
                        public void onPermissionGranted() {
                            actionPhoto();
                        }

                        @Override
                        public void onPermissionDenied() {
                            //finish();
                        }
                    });
            return;
        }
        actionPhoto();
    }

    private void actionPhoto() {
        final ImageLoader loader = new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        };

        ImgSelConfig config = new ImgSelConfig.Builder(this, loader)
                // 是否多选, 默认true
                .multiSelect(multiSelect)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.TRANSPARENT)
                // “确定”按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(getResources().getColor(R.color.colorPrimary))
                // 返回图标ResId
                .backResId(R.drawable.bar_back_white)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                // 第一个是否显示相机，默认true
                .needCamera(false)
                // 最大选择图片数量，默认9
                .maxNum(maxCount)
                .build();
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            for (String path : pathList) {
                if (multiSelect) {
                    adapter.add(path);
                } else {
                    adapter.set(index, path);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.back,R.id.etTime1,R.id.etTime2,R.id.btnSubmit}) void onClick(View view){
        switch (view.getId()){
            case R.id.btnSubmit:
                sumbit();
                break;
            case R.id.etTime1:
                initTimePicker(etTime1);
                pickerView.show();
                break;
            case R.id.etTime2:
                initTimePicker(etTime2);
                pickerView.show();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    /**
     * 发布活动
     */
    private void sumbit() {
        String title = etTitle.getText().toString().trim();
        String place = etPlace.getText().toString().trim();
        String des = etDes.getText().toString().trim();
        if (TextUtils.isEmpty(title)){
            ToastUtil.show(getApplicationContext(),"标题不能为空");
            return;
        }
        if (TextUtils.isEmpty(place)){
            ToastUtil.show(getApplicationContext(),"地点不能为空");
            return;
        }
        if (TextUtils.isEmpty(des)){
            ToastUtil.show(getApplicationContext(),"描述不能为空");
            return;
        }

    }

    private void initTimePicker(final EditText et) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        //选中事件回调
        //年月日时分秒 的显示与否，不设置则默认全部显示
        //设置外部遮罩颜色
        pickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String s = DateUtil.lineHDate(date);
                et.setText(s);
                Log.d("", "onTimeSelect: "+s);
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, false, false})
                .setLabel("年", "月", "日", "时", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

}

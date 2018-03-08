package com.zhou.xin.ui.activity.love.activity;
import android.annotation.TargetApi;
import android.content.ContentUris;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.zhou.xin.R;
import com.zhou.xin.adapter.base.BaseCommonAdapter;
import com.zhou.xin.adapter.base.CommonAdapter;
import com.zhou.xin.adapter.base.MultiItemCommonAdapter;
import com.zhou.xin.adapter.base.MultiItemTypeSupport;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.BaseActivity;
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
    @BindView(R.id.recycleView) RecyclerView recycleView;
    private TimePickerView pickerView;
    private List<String> mDatas;
    private BaseCommonAdapter adapter;
    private static final int CHOOSE_PHOTO = 1;
    private String imagePath;

    @Override
    protected int getLayout() {
        return R.layout.activity_send;
    }

    @Override
    protected void init() {
        tv_head.setText("发布活动");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecycler();
    }

    private void initRecycler() {
        mDatas = new ArrayList<>();
        adapter = new BaseCommonAdapter<String>(this, R.layout.recycle_send, mDatas) {
            @Override
            public void convert(ViewHolder holder, String s, int position) {

            }
        };
        recycleView.setLayoutManager(new GridLayoutManager(this,3));
        recycleView.setAdapter(adapter);
    }

    @OnClick({R.id.back,R.id.etTime1,R.id.etTime2,R.id.btnSubmit,R.id.ivAdd}) void onClick(View view){
        switch (view.getId()){
            case R.id.ivAdd:
                getPhone();
                break;
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


    /**
     * 从相册获取照片
     */
    private void getPhone() {
        //new一个file用于存放照片
        File imageFile = new File(Environment.getExternalStorageDirectory(), "outputImage.jpg");
        if (imageFile.exists()) {
            imageFile.delete();
        }
        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        //转换成Uri
        Uri imageUri = Uri.fromFile(imageFile);
        //开启选择呢绒界面
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        //设置可以缩放
        intent.putExtra("scale", true);
        //设置可以裁剪
        intent.putExtra("crop", true);
        intent.setType("image/*");
        //设置输出位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        //开始选择
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    handleImageOnKitkat(data);
                }
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitkat(Intent data) {

        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri
                    .getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri
                    .getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果不是document类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        }
        //displayImage(imagePath); // 根据图片路径显示图片
        mDatas.add(imagePath);
        adapter.add(mDatas);
        adapter.notifyDataSetChanged();
        Log.d(TAG, "照片的详细地址" + imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}

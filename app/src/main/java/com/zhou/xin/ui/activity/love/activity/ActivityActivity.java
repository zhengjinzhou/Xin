package com.zhou.xin.ui.activity.love.activity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhou.xin.R;
import com.zhou.xin.adapter.base.CommonAdapter;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class ActivityActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "ActivityActivity";
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.recycleView) RecyclerView recycleView;
    @BindView(R.id.refresh) SwipeRefreshLayout refresh;
    @BindView(R.id.iv_add) ImageView iv_add;
    private static final int CHOOSE_PHOTO = 1;
    private ImageView iv_img;
    String imagePath = null;

    @Override
    protected int getLayout() {
        return R.layout.activity_activity;
    }

    @Override
    protected void init() {
        tv_head.setText("线下活动");
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setImageResource(R.drawable.addto);
        initRecycle();
        refresh.setOnRefreshListener(this);
    }

    private void initRecycle() {
        List<String> data = new ArrayList<>();
        int num = new Random().nextInt(10) * 1;
        for (int i=0;i<num;i++){
            data.add(i+"");
        }
        CommonAdapter adapter = new CommonAdapter<String>(this, R.layout.recycle_activity, data) {
            @Override
            public void convert(ViewHolder holder, String s, int position) {
                holder.setOnClickListener(R.id.ll_activity, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startToActivity(ActivityInfoActivity.class);//要传参
                    }
                });
            }
        };
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
    }

    @OnClick({R.id.back,R.id.iv_add}) void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.iv_add:
                startToActivity(SendActivity.class);
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
            }
        },2000);
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
        switch (requestCode){
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
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
        displayImage(imagePath); // 根据图片路径显示图片
        Log.d(TAG, "照片的详细地址"+imagePath);
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

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            iv_img.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}

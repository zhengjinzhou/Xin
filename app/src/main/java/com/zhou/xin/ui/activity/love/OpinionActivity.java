package com.zhou.xin.ui.activity.love;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.ToastUtil;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class OpinionActivity extends BaseActivity {

    private static final int CHOOSE_PHOTO = 1;
    private static final String TAG = "OpinionActivity";
    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.et_opinion) EditText et_opinion;
    @BindView(R.id.iv_img) ImageView iv_img;


    @Override
    protected int getLayout() {
        return R.layout.activity_opinion;
    }

    @Override
    protected void init() {
        tv_head.setText("意见反馈");
    }

    @OnClick({R.id.back,R.id.bt_submit,R.id.rl_add}) void click(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_submit://提交反馈
                submit();
                break;
            case R.id.rl_add:
                getPhone();
                break;
        }
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
        String imagePath = null;
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

    //提交信息
    private void submit() {
        String opinion = et_opinion.getText().toString().trim();
        if (TextUtils.isEmpty(opinion)){
            ToastUtil.show(getApplicationContext(),"意见不能为空");
            return;
        }

    }
}

package com.zhou.xin.ui.activity.love;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.utils.CurrentTimeUtil;
import com.zhou.xin.utils.Md5Util;
import com.zhou.xin.utils.ToastUtil;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 举报用户
 */
public class ReportInfoActivity extends BaseActivity {

    private static final String TAG = "ReportInfoActivity";
    String imagePath = "";

    @BindView(R.id.tv_head) TextView tv_head;
    @BindView(R.id.iv_img) ImageView iv_img;
    @BindView(R.id.et_opinion) EditText et_opinion;


    private static final int CHOOSE_PHOTO = 1;

    public static Intent newIntent(Context context, String str) {
        Intent intent = new Intent(context, ReportInfoActivity.class);
        intent.putExtra("typeId", str);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_report_info;
    }

    @Override
    protected void init() {
        tv_head.setText("举报用户");
    }

    @OnClick({R.id.back, R.id.bt_submit, R.id.rl_add})
    void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_submit://提交反馈
                submit();
                break;
            case R.id.rl_add:
                getImage();
                break;
        }
    }

    /**
     * 得到照片
     *
     */
    private void getImage() {
        //new 一个file用于存放照片
        File imageFile = new File(Environment.getExternalStorageDirectory(), "outputImage.jpg");
        if (imageFile.exists()){
            imageFile.delete();
        }
        try{
            imageFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
            Log.d(TAG, "getImage: "+e.getMessage());
        }
        //转为uri
        Uri imageUri = Uri.fromFile(imageFile);
        //开启选择界面
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        //设置可以缩放
        intent.putExtra("scale",true);
        //设置可以裁剪
        intent.putExtra("crop",true);
        intent.setType("image/*");
        //设置输出位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        //开始选择
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

    /**
     * 找到照片位置
     *
     * @param uri
     * @param selection
     * @return
     */
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

    /**
     * 用于显示照片
     *
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            iv_img.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 提交证据
     */
    private void submit() {
        String reportContent = this.et_opinion.getText().toString().trim();
        if (TextUtils.isEmpty(reportContent)){
            ToastUtil.show(getApplicationContext(),"举报原因不能为空");
            return;
        }
        String aid = "3";
        String typeId = getIntent().getStringExtra("typeId");
        String photo = imagePath;

        dialog.show();
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File(photo);
        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("photo",photo,fileBody)
                .addFormDataPart("aid", aid)
                .addFormDataPart("typeId",typeId)
                .addFormDataPart("reportContent",reportContent)
                .build();
        Request request = new Request.Builder()
                .url(Constant.JUBAO)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: "+string);
                dialog.show();
            }
        });
    }
}

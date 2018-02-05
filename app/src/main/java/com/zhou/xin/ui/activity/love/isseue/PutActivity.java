package com.zhou.xin.ui.activity.love.isseue;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;
import com.zhou.xin.Constant;
import com.zhou.xin.R;
import com.zhou.xin.adapter.base.BaseCommonAdapter;
import com.zhou.xin.adapter.base.GridSpacingItemDecoration;
import com.zhou.xin.adapter.base.ViewHolder;
import com.zhou.xin.base.App;
import com.zhou.xin.base.BaseActivity;
import com.zhou.xin.base.other.PermissionsResultListener;
import com.zhou.xin.bean.UserInfo;
import com.zhou.xin.utils.ImageCompressionTools;
import com.zhou.xin.utils.PersonalFormTools;
import com.zhou.xin.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class PutActivity extends BaseActivity {

    private static final String TAG = "PutActivity";
    private List<String> photosData;
    private BaseCommonAdapter adapter;
    private boolean multiSelect = false;
    private int maxCount = 9;
    private int index = 0;
    private static final int PER_REQUEST_CODE = 1;
    private static final int REQUEST_CODE = 1000;
    @BindView(R.id.photos)
    RecyclerView photos;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.tv_head)
    TextView tv_head;

    @Override
    protected int getLayout() {
        return R.layout.activity_put;
    }

    @Override
    protected void init() {
        initPhotos();
        tv_head.setText("发布图文信息");
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
                    img.setImageResource(R.drawable.add_photo);
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

    @OnClick({R.id.back, R.id.bt_submit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_submit:
                submitImager();
                break;
        }
    }

    /**
     * 发布图文动态
     */
    private void submitImager() {
        List<String> data = PersonalFormTools.getInstall().getTureImager(adapter.getData());
        if (data.size() == 0) {
            ToastUtil.show(getApplicationContext(), "照片不能为空！");
            return;
        }
        final int count = data.size();
        for (int i = 0; i < count; i++) {
            String url = data.get(i);
            int index = i;
            File f = ImageCompressionTools.getInstance(this).onMianLuban(url);
            if (index == count - 1) {
                upImagers(f, true);
            } else {
                upImagers(f, false);
            }
        }
    }

    private void upImagers(File f, boolean b) {
        MultipartBody.Builder builider = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builider.addFormDataPart("token", App.getInstence().getUserInfo().getToken());
        if (!TextUtils.isEmpty(content.getText().toString()))
            builider.addFormDataPart("content", content.getText().toString());
        if (f == null) return;
        Log.e("TAG", "upImagers: url = " + f.getAbsolutePath());
        Log.e("TAG", "upImagers: size = " + (f.length() / 1024) + "k");

        builider.addFormDataPart("photoFile", f.getAbsolutePath(), RequestBody.create(MediaType.parse("image/*"), f));
        dialog.show();
        MultipartBody body = builider.build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.POST_LIST)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                dialog.dismiss();
                Gson gson = new Gson();
                final UserInfo userInfo = gson.fromJson(string, UserInfo.class);
                if (userInfo.getError().equals("-1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(getApplicationContext(), userInfo.getMsg());
                            finish();
                        }
                    });
                }
            }
        });
    }
}

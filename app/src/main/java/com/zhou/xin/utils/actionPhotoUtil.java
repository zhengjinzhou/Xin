package com.zhou.xin.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;
import com.zhou.xin.R;

/**
 * Created by zhou on 2018/2/8.
 */

public class actionPhotoUtil {

    public static void actionPhoto(Activity activity,int REQUEST_CODE){
        final ImageLoader loader = new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        };

        ImgSelConfig config = new ImgSelConfig.Builder(activity, loader)
                // 是否多选, 默认true
                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(R.color.colorPrimary)
                // 返回图标ResId
                .backResId(R.drawable.bar_back_white)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(R.color.colorPrimary)
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                // 第一个是否显示相机，默认true
                .needCamera(false)
                // 最大选择图片数量，默认9
                .maxNum(6)
                .build();

        ImgSelActivity.startActivity(activity, config, REQUEST_CODE);
    }
}

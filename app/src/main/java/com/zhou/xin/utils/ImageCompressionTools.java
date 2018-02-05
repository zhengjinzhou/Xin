package com.zhou.xin.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by zhou on 2018/2/5.
 */

public class ImageCompressionTools {

    private static ImageCompressionTools instance;

    private static Context context;

    public static ImageCompressionTools getInstance(Context c) {
        if(instance == null){
            instance = new ImageCompressionTools();
            context = c;
        }
        return instance;
    }

    public void onLuban(String path, OnCompressListener listener){

        Luban.with(context).load(path)
                .ignoreBy(100)
                .setTargetDir(getPath())
                .setCompressListener(listener).launch();
    }

    public File onMianLuban(String path){
        try {
            return Luban.with(context).load(path).get(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/LoveMe/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }
}

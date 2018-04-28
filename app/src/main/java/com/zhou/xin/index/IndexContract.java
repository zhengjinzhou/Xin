package com.zhou.xin.index;

import android.content.Context;

import java.io.File;

/**
 * Created by zhou on 2018/4/27.
 */

public interface IndexContract {

    interface View {
        void showUpdate(String version);
        void showProgress(int progress);
        void showFail(String msg);
        void showComplete(File file);
    }

    interface Presenter{
        void checkUpdate(String local);
        void setIgnore(String version);
        void downApk(Context context);
        void unbind(Context context);
    }
}
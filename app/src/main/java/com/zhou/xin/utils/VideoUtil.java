package com.zhou.xin.utils;

import android.app.Activity;
import android.content.Context;

import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.zhou.xin.ui.activity.love.isseue.PutVideoActivity;

/**
 * Created by zhou on 2018/2/7.
 */

public class VideoUtil {
    public static void toVideo(Activity context) {
        //使用框架录制视频的参数
        boolean needFull = true;
        int width = 480;
        int height = 480;
        int minTime = 1000;
        int maxTime = 10000;
        int maxFramerate = 20;
        int bitrate = 580000;
        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                .fullScreen(needFull)
                .smallVideoWidth(needFull ? 0 : width)
                .smallVideoHeight(height)
                .recordTimeMax(maxTime)
                .recordTimeMin(minTime)
                .maxFrameRate(maxFramerate)
                .videoBitrate(bitrate)
                .captureThumbnailsTime(1)
                .build();
        MediaRecorderActivity.goSmallVideoRecorder(context, PutVideoActivity.class.getName(), config);
    }
}

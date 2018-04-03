package com.zhou.xin.base;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.easemob.redpacketsdk.RPInitRedPacketCallback;
import com.easemob.redpacketsdk.RPValueCallback;
import com.easemob.redpacketsdk.RedPacket;
import com.easemob.redpacketsdk.bean.RedPacketInfo;
import com.easemob.redpacketsdk.bean.TokenData;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.EMLog;
import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;
import com.zhou.xin.Constant;
import com.zhou.xin.bean.ActivityBean;
import com.zhou.xin.bean.PersonalBean;
import com.zhou.xin.bean.ReportBean;
import com.zhou.xin.bean.SelectBean;
import com.zhou.xin.bean.UserInfo;
import com.zhou.xin.bean.VerBean;
import com.zhou.xin.db.InviteMessgeDao;
import com.zhou.xin.db.UserDao;
import com.zhou.xin.domain.RobotUser;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by zhou on 2017/12/19.
 */

public class App extends Application {

    private ActivityBean activityBean;

    private UserInfo userInfo;

    private PersonalBean personalBean;

    private VerBean verBean;

    private SelectBean selectBean;
    private static App app;
    private String TAG = "Application";

    public static Context applicationContext;
    // login user name
    public final String PREF_USERNAME = "username";
    //nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
    public static String currentUserNick = "";
    private DemoModel demoModel;
    private EaseUI easeUI;
    private Map<String, EaseUser> contactList;
    private InviteMessgeDao inviteMessgeDao;
    private UserDao userDao;
    private Map<String, RobotUser> robotList;
    private String username;
    private EMMessageListener messageListener;

    public ActivityBean getActivityBean() {
        return activityBean;
    }

    public void setActivityBean(ActivityBean activityBean) {
        this.activityBean = activityBean;
    }

    public SelectBean getSelectBean() {
        return selectBean;
    }

    public void setSelectBean(SelectBean selectBean) {
        this.selectBean = selectBean;
    }

    public PersonalBean getPersonalBean() {
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public VerBean getVerBean() {
        return verBean;
    }

    public void setVerBean(VerBean verBean) {
        this.verBean = verBean;
    }

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        applicationContext = this;
        app = this;
        initSmallVideo();
        initXin();
        ///initxxxxx(applicationContext);
    }

    private void initXin() {

        //init demo helper
        DemoHelper.getInstance().init(applicationContext);
        //red packet code : 初始化红包SDK，开启日志输出开关
        RedPacket.getInstance().initRedPacket(applicationContext, RPConstant.AUTH_METHOD_EASEMOB, new RPInitRedPacketCallback() {

            @Override
            public void initTokenData(RPValueCallback<TokenData> callback) {
                TokenData tokenData = new TokenData();
                tokenData.imUserId = EMClient.getInstance().getCurrentUser();
                //此处使用环信id代替了appUserId 开发者可传入App的appUserId
                tokenData.appUserId = EMClient.getInstance().getCurrentUser();
                tokenData.imToken = EMClient.getInstance().getAccessToken();
                //同步或异步获取TokenData 获取成功后回调onSuccess()方法
                callback.onSuccess(tokenData);
            }

            @Override
            public RedPacketInfo initCurrentUserSync() {
                //这里需要同步设置当前用户id、昵称和头像url
                String fromAvatarUrl = "";
                String fromNickname = EMClient.getInstance().getCurrentUser();
                EaseUser easeUser = EaseUserUtils.getUserInfo(fromNickname);
                if (easeUser != null) {
                    fromAvatarUrl = TextUtils.isEmpty(easeUser.getAvatar()) ? "none" : easeUser.getAvatar();
                    fromNickname = TextUtils.isEmpty(easeUser.getNick()) ? easeUser.getUsername() : easeUser.getNick();
                }
                RedPacketInfo redPacketInfo = new RedPacketInfo();
                redPacketInfo.fromUserId = EMClient.getInstance().getCurrentUser();
                redPacketInfo.fromAvatarUrl = fromAvatarUrl;
                redPacketInfo.fromNickName = fromNickname;
                return redPacketInfo;
            }
        });
        RedPacket.getInstance().setDebugMode(true);
        //end of red packet code
    }

    public static App getInstance() {
        return app;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 录制视频初始化
     */
    public static void initSmallVideo() {
        // 设置拍摄视频缓存路径
        File dcim = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
            } else {
                JianXiCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/mabeijianxi/");
            }
        } else {
            JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
        }
        // 初始化拍摄，遇到问题可选择开启此标记，以方便生成日志
        JianXiCamera.initialize(false,null);
    }

}

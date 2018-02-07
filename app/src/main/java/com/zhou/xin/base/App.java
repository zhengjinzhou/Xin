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
import com.zhou.xin.bean.PersonalBean;
import com.zhou.xin.bean.ReportBean;
import com.zhou.xin.bean.SelectBean;
import com.zhou.xin.bean.UserInfo;
import com.zhou.xin.db.InviteMessgeDao;
import com.zhou.xin.db.UserDao;
import com.zhou.xin.domain.RobotUser;
import com.zhou.xin.huanxin.HxEaseuiHelper;
import com.zhou.xin.huanxin.SharedPreferencesUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by zhou on 2017/12/19.
 */

public class App extends Application {


    private UserInfo userInfo;

    private PersonalBean personalBean;

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

    /**
     * get current user's id
     */
    public String getCurrentUsernName(){
        if(username == null){
            username = (String) SharedPreferencesUtils.getParam(applicationContext, Constant.HX_CURRENT_USER_ID, "");
        }
        return username;
    }
    /**
     *获取所有的联系人信息
     *
     * @return
     */
    public Map<String, EaseUser> getContactList() {
        if (isLoggedIn() && contactList == null) {
            contactList = demoModel.getContactList();
        }
        // return a empty non-null object to avoid app crash
        if(contactList == null){
            return new Hashtable<String, EaseUser>();
        }
        return contactList;
    }
    /**
     * if ever logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }
    /**
     * set global listener
     */
    protected void setGlobalListeners(){
        registerMessageListener();
    }
    /**
     * Global listener
     * If this event already handled by an activity, you don't need handle it again
     * activityList.size() <= 0 means all activities already in background or not in Activity Stack
     */
    protected void registerMessageListener() {
        //接收并处理扩展消息
        //存入内存
        //存入db
        // in background, do not refresh UI, notify it in notification bar
        //                    if(!easeUI.hasForegroundActivies()){
        //                        getNotifier().onNewMsg(message);
        //                    }
        //get message body
        //end of red packet code
        //获取扩展属性 此处省略
        //maybe you need get extension of your message
        //message.getStringAttribute("");
        messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
                    //接收并处理扩展消息
                    String userName = message.getStringAttribute(Constant.USER_NAME, "");
                    String userId = message.getStringAttribute(Constant.USER_ID, "");
                    String userPic = message.getStringAttribute(Constant.HEAD_IMAGE_URL, "");
                    String hxIdFrom = message.getFrom();
                    System.out.println("helper接收到的用户名：" + userName + "helper接收到的id：" + userId + "helper头像：" + userPic);
                    EaseUser easeUser = new EaseUser(hxIdFrom);
                    easeUser.setAvatar(userPic);
                    easeUser.setNick(userName);
                    //存入内存
                    getContactList();
                    contactList.put(hxIdFrom, easeUser);
                    //存入db
                    UserDao dao = new UserDao(applicationContext);
                    List<EaseUser> users = new ArrayList<EaseUser>();
                    users.add(easeUser);
                    dao.saveContactList(users);
                    // in background, do not refresh UI, notify it in notification bar
//                    if(!easeUI.hasForegroundActivies()){
//                        getNotifier().onNewMsg(message);
//                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "receive command message");
                    //get message body
                    //end of red packet code
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                }
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }
}

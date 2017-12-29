package com.zhou.xin;

import com.hyphenate.easeui.EaseConstant;

/**
 * Created by zhou on 2017/12/19.
 */

public class Constant extends EaseConstant {
    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String CHAT_ROOM = "item_chatroom";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String ACCOUNT_CONFLICT = "conflict";
    public static final String ACCOUNT_FORBIDDEN = "user_forbidden";
    public static final String ACCOUNT_KICKED_BY_CHANGE_PASSWORD = "kicked_by_change_password";
    public static final String ACCOUNT_KICKED_BY_OTHER_DEVICE = "kicked_by_another_device";
    public static final String CHAT_ROBOT = "item_robots";
    public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
    public static final String ACTION_GROUP_CHANAGED = "action_group_changed";
    public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";

    public static final String EXTRA_CONFERENCE_ID = "confId";
    public static final String EXTRA_CONFERENCE_PASS = "password";
    public static final String EXTRA_CONFERENCE_IS_CREATOR = "is_creator";


    public static final String URL = "http://123.207.55.87:8080/LoveMe";
    //登陆        获取个人信息
    public static final String LOGIN_URL = URL+"/app/index.do";

    public static final String TOKEN = "";
    public static final String APP_ENCRYPTION_KEY = "IAASIDuioponuYBIUNLIK123ikoIO";
    public static final String ENCRYPTION_KEY = "ASDHOjhudhaos23asdihoh80";

    public static final String MOBILE = "mobile";

    //注册 发送验证码  //注册 提交用户名，密码，手机号码，邀请码
    public static final String URL_REGISTER = URL + "/app/index.do";

    //获取异性推送
    public static final String ISOMERISM_URL= URL +  "/app/o_push.do";
}

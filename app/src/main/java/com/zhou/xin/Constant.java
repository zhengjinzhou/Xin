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
    public static final String LOGIN_URL = URL + "/app/index.do";

    //修改会员个人信息
    public static final String UPDATE_URL = URL + "/app/update.do";
    //获取朋友圈
    public static final String GET_LIST = URL + "/app/v_list.do";
    //上传朋友圈
    public static final String POST_LIST = URL + "/app/v_upload.do";

    //举报
    public static final String JUBAO = URL + "/app/accusation.do";

    public static final String TOKEN = "";
    public static final String APP_ENCRYPTION_KEY = "IAASIDuioponuYBIUNLIK123ikoIO";
    public static final String ENCRYPTION_KEY = "ASDHOjhudhaos23asdihoh80";

    public static final String MOBILE = "mobile";

    //记住密码
    public static final String PASSWORD = "password";

    //注册 发送验证码  //注册 提交用户名，密码，手机号码，邀请码
    public static final String URL_REGISTER = URL + "/app/index.do";

    //获取异性推送
    public static final String ISOMERISM_URL = URL + "/app/o_push.do";

    //记住头像
    public static final String APP_PHOTO = "app_photo";

    public static final String POINT_LIKE = "point_like";//点赞

    public static final String HEAD_IMAGE_URL = "headImageUrl";//发送人的头像
    public static final String USER_ID = "userid";
    public static final String USER_NAME = "username";
    public static final String SEX = "sex";
    public static final String RECEIVOR_HEAD_IMAGE_URL = "objectHeadImageUrl";//接收人的头像
    public static final String RECEIVOR_USERID = "objectUserid";
    public static final String RECEIVOR_USER_NAME = "objectUserName";
    public static final String RECEIVOR_USER_SEX = "objectUserSex";
    public static String USER_HEAD_IMG = "user_head_img";
    public static String HX_CURRENT_USER_ID = "hx_current_user_id";

    public static String o_feedback = URL + "/app/o_feedback.do";

    /**
     * 使用框架录制视频的参数
     *
     */
    public static boolean needFull = true;

    //自动登录
    public static final String ISLOGIN = "ISLOGIN";
    public static final String USERNAME = "USERNAME";
    //保存当前通讯录人数，用于控制添加好友关系
    public static final String USER_NUM = "USER_NUM";
}

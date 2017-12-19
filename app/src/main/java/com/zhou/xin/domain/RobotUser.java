package com.zhou.xin.domain;

import com.hyphenate.easeui.domain.EaseUser;

/**
 * Created by zhou on 2017/12/19.
 */

public class RobotUser extends EaseUser {
    public RobotUser(String username) {
        super(username.toLowerCase());
    }
}

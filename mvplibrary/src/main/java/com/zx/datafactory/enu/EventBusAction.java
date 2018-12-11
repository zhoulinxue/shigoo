package com.zx.datafactory.enu;

import android.text.TextUtils;

/**
 * Name: EventBusAction
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-06 18:40
 */
public enum EventBusAction {
    MAIN("android.intent.action.MAIN"),
    MEMBER_MSG("member_msg"),
    SELLER_INFO("seller_info"), LOGIN("login"), LOGIN_SUC("login_suc"), NULL("nothing");

    EventBusAction(String member_msg) {
        this.action = member_msg;
    }

    public String getAction() {
        return action;
    }

    private String action;

    public static EventBusAction getEnum(String actionStr) {
        if (!TextUtils.isEmpty(actionStr)) {
            for (EventBusAction action : values()) {
                if (actionStr.equals(action.getAction())) {
                    return action;
                }
            }
        } else {
            return NULL;
        }
        return NULL;
    }

    ;
}

package com.zx.datafactory.bean;

import com.zx.api.api.ChildViewClick;

/**
 * Name: FragmentEvent
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-01 16:09
 */
public class FragmentEvent {
    private ChildViewClick event;
    private String[] params;

    public FragmentEvent(ChildViewClick event) {
        this.event = event;
    }

    public FragmentEvent(ChildViewClick event, String[] params) {
        this.event = event;
        this.params = params;
    }

    public ChildViewClick getEvent() {
        return event;
    }

    public void setEvent(ChildViewClick event) {
        this.event = event;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}

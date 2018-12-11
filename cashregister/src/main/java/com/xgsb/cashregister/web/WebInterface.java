package com.xgsb.cashregister.web;

/**
 * Name: WebInterface
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-24 18:00
 */
public enum WebInterface {
    CURRENT_TABLES("tables"),

    MENBERS("members"),

    SEARCH("search");

    WebInterface(String id) {
        this.interfaceId = id;
    }

    private String interfaceId;

    public String getInterfaceId() {
        return interfaceId;
    }

}

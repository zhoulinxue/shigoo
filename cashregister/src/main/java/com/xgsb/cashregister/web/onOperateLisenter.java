package com.xgsb.cashregister.web;

import io.starteos.dappsdk.Request;

/**
 * Name: onOperateLisenter
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-27 17:59
 */
public interface onOperateLisenter {

    public void onGettableInfo(final Request request);

    public void onOperate(Request request);

    public void onSearch(Request request);
}

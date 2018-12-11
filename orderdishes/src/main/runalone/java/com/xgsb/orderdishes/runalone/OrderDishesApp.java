package com.xgsb.orderdishes.runalone;

import com.xgsb.orderdishes.BuildConfig;
import com.zx.api.api.utils.AppLog;
import com.zx.mvplibrary.MvpApplication;

/**
 * Name: OrderDishesApp
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-10 10:15
 */
public class OrderDishesApp extends MvpApplication {
    @Override
    public String getApiHost() {
        AppLog.print(BuildConfig.API_HOST);
        return BuildConfig.API_HOST;
    }
}

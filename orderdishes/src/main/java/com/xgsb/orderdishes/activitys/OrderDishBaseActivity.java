package com.xgsb.orderdishes.activitys;

import android.content.Intent;

import com.zx.mvplibrary.BaseActivity;

/**
 * Name: OrderDishBaseActivity
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-11 10:55
 */
public abstract class OrderDishBaseActivity extends BaseActivity {
    @Override
    public void onSuccess(Object object) {

    }

    public void startRouterActivity(com.zx.datafactory.enu.EventBusAction action) {
        Intent intent = new Intent(this, RouterActivity.class);
        intent.setAction(action.getAction());
        startActivity(intent);
    }
}

package com.xgsb.shigoo.activitys;

import android.os.Bundle;

import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.luojilab.router.facade.annotation.RouteNode;
import com.xgsb.shigoo.R;
import com.zx.mvplibrary.BaseActivity;

/**
 * Name: MainActivity
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-11 17:01
 */
@RouteNode(path = "/main", desc = "首页")
public class MainActivity extends BaseActivity {
    @Override
    protected int initLayout() {
        return R.layout.router_activity;
    }

    @Override
    protected void onCreateView() {
        goToCashRegisterWithBundle();
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }

    @Override
    public void onSuccess(Object object) {

    }

    private void goToCashRegisterWithBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("bookName", "Gone with the Wind");
        UIRouter.getInstance().openUri(this, "cashregister://cashregister/cashmain", bundle);
    }
}

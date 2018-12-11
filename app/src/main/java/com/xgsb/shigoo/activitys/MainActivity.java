package com.xgsb.shigoo.activitys;

import android.os.Bundle;

import com.luojilab.router.facade.annotation.RouteNode;
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
        return 0;
    }

    @Override
    protected void onCreateView() {

    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }

    @Override
    public void onSuccess(Object object) {

    }
}

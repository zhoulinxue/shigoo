package com.xgsb.orderdishes.customViews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.xgsb.orderdishes.R;
import com.zx.mvplibrary.BaseCustomView;

/**
 * Name: OrderDishTableLeftView
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-11 12:57
 */
public class OrderDishTableLeftView extends BaseCustomView {
    public OrderDishTableLeftView(Context context, ViewGroup rootGroup) {
        super(context, rootGroup);
    }

    public OrderDishTableLeftView(Context context) {
        super(context);
    }

    @Override
    protected void initView(Context context, View rootView) {

    }

    @Override
    public int initLayout() {
        return R.layout.ordedishes_table_left_layout;
    }
}

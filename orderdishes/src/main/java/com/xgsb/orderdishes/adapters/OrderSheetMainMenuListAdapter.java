package com.xgsb.orderdishes.adapters;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xgsb.orderdishes.adapters.viewHoder.OrderSheetMainViewHolder;
import com.zx.datafactory.bean.OrderSheetMainMenu;

import java.util.List;

/**
 * Auther:zhouxue
 * Time :2018/8/24.
 */

public class OrderSheetMainMenuListAdapter extends BaseQuickAdapter<com.zx.datafactory.bean.OrderSheetMainMenu, OrderSheetMainViewHolder> {


    public OrderSheetMainMenuListAdapter(int layoutResId, @Nullable List<OrderSheetMainMenu> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(OrderSheetMainViewHolder helper, com.zx.datafactory.bean.OrderSheetMainMenu item) {
        helper.mLogo.setImageResource(item.getLogo());
        helper.mNameTv.setText(item.getName());
    }
}

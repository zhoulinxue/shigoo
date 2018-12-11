package com.xgsb.cashregister.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xgsb.cashregister.adapter.viewHolder.SellerViewHolder;
import com.zx.datafactory.bean.Sellerbean;

import java.util.List;

/**
 * Name: SellerListAdapter
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-05 19:33
 */
public class SellerListAdapter extends BaseQuickAdapter<Sellerbean, SellerViewHolder> {

    public SellerListAdapter(int layoutResId, @Nullable List<Sellerbean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(SellerViewHolder helper, Sellerbean item) {
        helper.mSellerTv.setText(item.getNumber() + "   " + item.getStaff_name());
    }
}

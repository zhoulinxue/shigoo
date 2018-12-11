package com.xgsb.cashregister.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xgsb.cashregister.adapter.viewHolder.CouponViewHolder;
import com.zx.datafactory.bean.Couponbean;
import com.zx.network.Param;

import java.util.List;

/**
 * Auther:zhouxue
 * Time :2018/8/24.
 */

public class CouponAdapter extends BaseQuickAdapter<Couponbean, CouponViewHolder> {

    public CouponAdapter(int layoutResId, @Nullable List<Couponbean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(CouponViewHolder helper, Couponbean item) {
        helper.mCouponMoney.setText(Param.Keys.RMB + item.getVoucher_money());
        helper.mCouponTypeName.setText("1".equals(item.getVoucher_type()) ? "代金券" : "商品卷");
        helper.mLimit.setText(item.getLimit());
        helper.mTimeText.setText(item.getVoucher_date() + "  -  " + item.getEnd_date());
    }

}

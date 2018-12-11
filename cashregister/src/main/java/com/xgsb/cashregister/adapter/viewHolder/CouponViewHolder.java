package com.xgsb.cashregister.adapter.viewHolder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xgsb.cashregister.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Auther:zhouxue
 * Time :2018/8/7.
 */

public class CouponViewHolder extends BaseViewHolder {
    @BindView(R2.id.cashregister_coupon_name)
    public TextView mCouponTypeName;
    @BindView(R2.id.cashregister_coupon_money)
    public TextView mCouponMoney;
    @BindView(R2.id.cashregister_coupon_time_text)
    public TextView mTimeText;
    @BindView(R2.id.cashregister_coupon_limit)
    public TextView mLimit;

    public CouponViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, itemView);
    }
}

package com.xgsb.cashregister.adapter.viewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xgsb.cashregister.R;
import com.xgsb.cashregister.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Auther:zhouxue
 * Time :2018/8/7.
 */

public class MemberRechargeGiveHolder extends BaseViewHolder {
    @BindView(R2.id.cashregister_member_recharge_name)
    public TextView mRechargeName;
    @BindView(R2.id.cashregister_member_give_name)
    public TextView mRechargeGive;
    @BindView(R2.id.cashregister_recharge_item_layout)
    public LinearLayout mRechargeLayout;

    public MemberRechargeGiveHolder(View view) {
        super(view);
        ButterKnife.bind(this, itemView);
    }
}

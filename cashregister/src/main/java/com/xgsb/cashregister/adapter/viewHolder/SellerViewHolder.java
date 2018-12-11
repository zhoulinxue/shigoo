package com.xgsb.cashregister.adapter.viewHolder;

import android.view.View;
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

public class SellerViewHolder extends BaseViewHolder {
    @BindView(R2.id.cashregister_seller_list_item_tv)
    public TextView mSellerTv;

    public SellerViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, itemView);
    }
}

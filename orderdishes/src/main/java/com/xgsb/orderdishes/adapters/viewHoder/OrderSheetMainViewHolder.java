package com.xgsb.orderdishes.adapters.viewHoder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xgsb.orderdishes.R2;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Auther:zhouxue
 * Time :2018/8/7.
 */

public class OrderSheetMainViewHolder extends BaseViewHolder {
    @BindView(R2.id.ordedishes_main_itme_name)
   public TextView mNameTv;
    @BindView(R2.id.ordedishes_main_itme_logo)
   public ImageView mLogo;

    public OrderSheetMainViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, itemView);
    }
}

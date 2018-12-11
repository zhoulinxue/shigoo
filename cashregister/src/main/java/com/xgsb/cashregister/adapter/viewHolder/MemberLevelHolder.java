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

public class MemberLevelHolder extends BaseViewHolder {
    @BindView(R2.id.cashregister_member_level_name)
    public TextView mLevelName;
    @BindView(R2.id.cashregister_member_youhu_name)
    public TextView mLevelZhe;
    @BindView(R2.id.cashregister_level_item_layout)
    public LinearLayout mLevelLayout;

    public MemberLevelHolder(View view) {
        super(view);
        ButterKnife.bind(this, itemView);
    }
}

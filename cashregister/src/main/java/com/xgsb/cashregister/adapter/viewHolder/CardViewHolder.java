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

public class CardViewHolder extends BaseViewHolder {
    @BindView(R2.id.cashregister_position_text)
    public TextView mPosition;
    @BindView(R2.id.cashregister_card_num)
    public TextView mCardNum;
    @BindView(R2.id.cashregister_card_status)
    public TextView mStatus;
    @BindView(R2.id.cashregister_delete_card)
    public TextView mDelete;


    public CardViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, itemView);
        addOnClickListener(R2.id.cashregister_delete_card);
    }
}

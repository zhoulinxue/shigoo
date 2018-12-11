package com.xgsb.cashregister.customViews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xgsb.cashregister.R;
import com.xgsb.cashregister.R2;
import com.zx.datafactory.bean.ConsumeListData;
import com.zx.api.api.utils.DateUtil;
import com.zx.mvplibrary.BaseCustomView;
import com.zx.network.Param;

import butterknife.BindView;

/**
 * Name: ReChargeHeaderView
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-27 19:28
 */
public class ConsumeHeaderView extends BaseCustomView {
    private String mStartTime = DateUtil.format(DateUtil.getStartTime(), DateUtil.YEAR_MONTH_DAY_PATTERN);
    private String mEndTime = DateUtil.format(DateUtil.getnowEndTime(), DateUtil.YEAR_MONTH_DAY_PATTERN);
    @BindView(R2.id.cashregister_records_tv)
    TextView mRecordsTv;
    @BindView(R2.id.cashregister_reciverable_count)
    TextView mConsumeNotStoreCount;
    @BindView(R2.id.cashregister_consume_not_store_money)
    TextView mRechargeMoney;
    @BindView(R2.id.cashregister_consume_give)
    TextView mConsumeGive;
    @BindView(R2.id.cashregister_store_money)
    TextView mConsumeStorMoney;
    @BindView(R2.id.cashregister_money_header)
    FrameLayout mMoneyHeaderLayout;
    private MemberMoneyView mMoneyheader;

    public ConsumeHeaderView(Context context, ViewGroup rootGroup) {
        super(context, rootGroup);
    }

    public ConsumeHeaderView(Context context) {
        super(context);
    }

    @Override
    protected void initView(Context context, View rootView) {
        mMoneyheader = new MemberMoneyView(context, mMoneyHeaderLayout);
    }

    @Override
    public int initLayout() {
        return R.layout.cashregister_consume_header_layout;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }


    public String getKey() {
        return mMoneyheader.getKey();
    }

    public void setQueryNum(int size) {
        String total = String.format("查询到 %s 笔记录", size + "");
        mRecordsTv.setText(total);
    }

    public void setNum(ConsumeListData data) {
        mConsumeNotStoreCount.setText(Param.Keys.RMB + data.getReceivable_money_count());
        mRechargeMoney.setText(Param.Keys.RMB + data.getNo_storage_money_count());
        mConsumeStorMoney.setText(Param.Keys.RMB + data.getStorage_money_count());
        mConsumeGive.setText(Param.Keys.RMB + data.getStorage_money_give_count());
    }

    public TextView getSearchbtn() {
        return mMoneyheader.getSearchTv();
    }

    public TextView getCleanBtn() {
        return mMoneyheader.getCleanTv();
    }

    public void clean() {
        mMoneyheader.clean();
    }

    ;
}

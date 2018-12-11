package com.xgsb.cashregister.customViews;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xgsb.cashregister.R;
import com.xgsb.cashregister.R2;
import com.xgsb.cashregister.wedgit.ClearableEditTextWithIcon;
import com.zx.api.api.utils.DateUtil;
import com.zx.mvplibrary.BaseCustomView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Name: MemberMoneyView
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-28 18:17
 */
public class MemberMoneyView extends BaseCustomView {
    @BindView(R2.id.cashregister_member_search_edit)
    ClearableEditTextWithIcon mEditText;
    @BindView(R2.id.cashregister_today_tv)
    TextView mTodayTv;
    @BindView(R2.id.cashregister_yesterday_tv)
    TextView mYesTerdayTv;
    @BindView(R2.id.cashregister_last_seven_days)
    TextView mLast7DaysTv;
    @BindView(R2.id.cashregister_recharge_time_text)
    TextView mTimeTv;
    @BindColor(R.color.cashregister_time_text_color)
    int mTimeNomalc;
    @BindColor(R.color.cashregister_member_detail_title_color)
    int mTimePressColor;
    @BindView(R2.id.cashregister_search_tv)
    TextView mSearchTv;
    @BindView(R2.id.cashregister_clean_tv)
    TextView mCleanTv;
    String DEFAULT_END_TIME = DateUtil.format(DateUtil.getnowEndTime(), DateUtil.YEAR_MONTH_DAY_PATTERN);
    String DEFAULT_START_TIME = DateUtil.format(DateUtil.getStartTime(), DateUtil.YEAR_MONTH_DAY_PATTERN);
    private String mStartTime = DEFAULT_START_TIME;
    private String mEndTime = DEFAULT_END_TIME;
    private TimePickerView mTimePicker;
    private boolean hasStartTime;
    private boolean hasEndTime;


    public MemberMoneyView(Context context, ViewGroup rootGroup) {
        super(context, rootGroup);
    }

    public MemberMoneyView(Context context) {
        super(context);
    }

    @Override
    protected void initView(final Context context, View rootView) {
        mTimePicker = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (!hasStartTime) {
                    hasStartTime = true;
                    mStartTime = DateUtil.format(date, DateUtil.YEAR_MONTH_DAY_PATTERN);
                } else if (hasStartTime && !hasEndTime) {
                    hasEndTime = true;
                    mEndTime = DateUtil.format(date, DateUtil.YEAR_MONTH_DAY_PATTERN);
                }
                updateTime();
                if (hasEndTime && hasStartTime) {
                    mTodayTv.setTextColor(mTimeNomalc);
                    mYesTerdayTv.setTextColor(mTimeNomalc);
                    mLast7DaysTv.setTextColor(mTimeNomalc);
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).isDialog(true).build();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(500, 400,
                Gravity.CENTER_VERTICAL);
        params.leftMargin = 0;
        params.rightMargin = 0;
        mTimePicker.getDialogContainerLayout().setLayoutParams(params);
        mTodayTv.setTextColor(mTimePressColor);
        updateTime();
    }

    private void updateTime() {
        if (TextUtils.isEmpty(mStartTime) || TextUtils.isEmpty(mEndTime)) {
            mEndTime = DateUtil.format(DateUtil.getnowEndTime(), DateUtil.YEAR_MONTH_DAY_PATTERN);
            mStartTime = DateUtil.format(DateUtil.getStartTime(), DateUtil.YEAR_MONTH_DAY_PATTERN);
            mTodayTv.setTextColor(mTimePressColor);
        }
        mTimeTv.setText(String.format("%s   ~   %s", mStartTime, mEndTime));
    }


    @Override
    public int initLayout() {
        return R.layout.cashregister_money_header_layout;
    }

    public String getKey() {
        return mEditText.getText().toString();
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    @OnClick({R2.id.cashregister_recharge_time_text, R2.id.cashregister_today_tv, R2.id.cashregister_last_seven_days, R2.id.cashregister_yesterday_tv, R2.id.cashregister_clean_tv})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R2.id.cashregister_recharge_time_text:
                if (!hasStartTime || (hasEndTime && hasEndTime)) {
                    if ((hasEndTime && hasEndTime)) {
                        hasStartTime = false;

                        hasEndTime = false;
                    }
                    Calendar date = Calendar.getInstance();
                    date.setTime(DateUtil.parse(mStartTime, DateUtil.YEAR_MONTH_DAY_PATTERN));
                    mTimePicker.setTitleText("设置开始时间");
                } else if (!hasEndTime) {
                    mTimePicker.setTitleText("设置结束时间");
                    Calendar date = null;
                    if (mEndTime.equals(DEFAULT_END_TIME)) {
                        date = Calendar.getInstance();
                        date.setTime(DateUtil.parse(mStartTime, DateUtil.YEAR_MONTH_DAY_PATTERN));
                    } else {
                        date = Calendar.getInstance();
                        date.setTime(DateUtil.parse(mEndTime, DateUtil.YEAR_MONTH_DAY_PATTERN));
                    }
                    mTimePicker.setDate(date);
                }
                mTimePicker.show();
                break;
            case R2.id.cashregister_yesterday_tv:
                mStartTime = DateUtil.format(DateUtil.getTime(1), DateUtil.YEAR_MONTH_DAY_PATTERN);
                mEndTime = DEFAULT_START_TIME;
                mTodayTv.setTextColor(mTimeNomalc);
                mYesTerdayTv.setTextColor(mTimePressColor);
                mLast7DaysTv.setTextColor(mTimeNomalc);
                updateTime();
                break;
            case R2.id.cashregister_today_tv:
                mStartTime = DEFAULT_START_TIME;
                mEndTime = DEFAULT_END_TIME;
                mTodayTv.setTextColor(mTimePressColor);
                mYesTerdayTv.setTextColor(mTimeNomalc);
                mLast7DaysTv.setTextColor(mTimeNomalc);
                updateTime();
                break;
            case R2.id.cashregister_last_seven_days:
                mStartTime = DateUtil.format(DateUtil.getTime(7), DateUtil.YEAR_MONTH_DAY_PATTERN);
                mEndTime = DEFAULT_END_TIME;
                mTodayTv.setTextColor(mTimeNomalc);
                mYesTerdayTv.setTextColor(mTimeNomalc);
                mLast7DaysTv.setTextColor(mTimePressColor);
                updateTime();
                break;
        }
    }

    public void clean() {
        mSearchTv.setText("");
        mStartTime = "";
        updateTime();
    }

    public TextView getSearchTv() {
        return mSearchTv;
    }

    public TextView getCleanTv() {
        return mCleanTv;
    }
}

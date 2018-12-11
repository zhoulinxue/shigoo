package com.xgsb.cashregister.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xgsb.cashregister.R2;
import com.xgsb.cashregister.customViews.RechargeView;
import com.xgsb.cashregister.R;
import com.xgsb.cashregister.activitys.AddAndUpdateMemberActivity;
import com.xgsb.cashregister.adapter.CouponAdapter;
import com.xgsb.cashregister.mvp.contacts.AddCardContact;
import com.xgsb.cashregister.mvp.contacts.MemberDetailContact;
import com.xgsb.cashregister.mvp.presenter.AddCardPresenter;
import com.xgsb.cashregister.mvp.presenter.MemberDetailPresenter;
import com.xgsb.cashregister.utils.DialogUtil;
import com.zx.datafactory.bean.Cardbean;
import com.zx.datafactory.bean.Couponbean;
import com.zx.datafactory.bean.EventRouter;
import com.zx.datafactory.bean.FragmentEvent;
import com.zx.datafactory.bean.Member;
import com.zx.api.api.ChildViewClick;
import com.zx.mvplibrary.MvpFragment;
import com.zx.network.Param;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Name: MemberDetailFragment
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-27 12:57
 */
public class MemberDetailFragment extends MvpFragment<MemberDetailPresenter> implements MemberDetailContact.view, AddCardContact.view, RechargeView.RechargeLisenter, DialogInterface.OnDismissListener {
    private String id;

    @BindView(R2.id.cashregister_member_detail_back_layout)
    View mBackView;
    @BindView(R2.id.cashregister_member_detail_mobile_num)
    TextView mPhoneNum;
    @BindView(R2.id.cashregister_card_type)
    TextView mCardType;
    @BindView(R2.id.cashregister_card_num)
    TextView mCardNum;
    @BindView(R2.id.cashregister_member_level)
    TextView mMemberLevelName;
    @BindView(R2.id.cashregister_growth_value)
    TextView mGrowthValue;
    @BindView(R2.id.cashregister_preferential)
    TextView mYHType;
    @BindView(R2.id.cashregister_integral_balance_value)
    TextView mIntegralBalance;
    @BindView(R2.id.cashregister_stored_value_balance)
    TextView mStoredValue;
    @BindView(R2.id.cashregister_membership_assignment)
    TextView mMembership;
    @BindView(R2.id.cashregister_membership_status)
    TextView mMenberStatus;
    @BindView(R2.id.cashregister_recharge_btn)
    Button mRechargeBtn;
    @BindView(R2.id.cashregister_member_name_tv)
    TextView mNameText;
    @BindView(R2.id.cashregister_member_birthday)
    TextView mBirthdayTv;
    @BindView(R2.id.cashregister_card_opening_time)
    TextView mCardOpTime;
    @BindView(R2.id.cashregister_consume_num)
    TextView mConsumeNum;
    @BindView(R2.id.cashregister_consumption_amount)
    TextView mAountedNum;
    @BindView(R2.id.cashregister_last_consume_time)
    TextView mLastConsumeTime;
    @BindView(R2.id.cashregister_card_num_layout)
    View mCardNumLayout;
    @BindView(R2.id.cashregister_coupon_available)
    TextView mCouponAvaliable;
    @BindView(R2.id.cashregister_coupon_recyclerview)
    RecyclerView mRecyclerView;
    CouponAdapter mAdapter;
    @BindView(R2.id.cashregister_freeze_tv)
    TextView mFroenTv;
    private List<Couponbean> mList;
    private Member mMber;
    private RechargeView mRechargeView;
    private Dialog mChanageDialog, mTwoBtnDialog;
    private View mChanageLayout;
    private EditText mCardNumEditText;
    private TextView mNumTextview;
    private boolean isExcute = false;
    private AddCardPresenter mCardPresenter;
    private int mFroenType=1;


    public static MemberDetailFragment newInstance() {
        MemberDetailFragment fragment = new MemberDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResult(Member member) {
        mMber = member;
        mRechargeView.setUserInfo(member);
        mLastConsumeTime.setText(member.getLast_consumption_time());
        mConsumeNum.setText(member.getConsumption_count());
        mAountedNum.setText(member.getConsumption_money_count());
        mCardOpTime.setText(member.getOpen_card_time());
        mBirthdayTv.setText(member.getBirthday());
        mNameText.setText(member.getName());
        if ("1".equals(member.getStatus_type())) {
            mMenberStatus.setText("挂失/注销");
            mFroenTv.setText("解冻");
            mFroenType=2;
        } else {
            mMenberStatus.setText("正常");
        }
        mMembership.setText(member.getMembership() + "");
        mStoredValue.setText(Param.Keys.RMB + member.getMoney() +
                "  (  本金 " + member.getMoney_principal() +
                "   赠送 :" + member.getMoney_give() + ")");
        mIntegralBalance.setText(member.getIntegral() + "");
        mGrowthValue.setText(member.getGrowth_value() + "");
        mPhoneNum.setText(member.getMobile());
        if (member.getCard_number().equals("0") || TextUtils.isEmpty(member.getCard_number())) {
            mCardNumLayout.setVisibility(View.GONE);
        } else {
            mCardNum.setText(member.getCard_number());
        }
        String couponTv = String.format(getResources().getString(R.string.cashregister_coupons_available), member.getVoucher().size() + "");
        mCouponAvaliable.setText(couponTv);
        mMemberLevelName.setText(member.getGrade_name());
        mYHType.setText(member.getGrade_discount_than_name());
        mAdapter.setNewData(member.getVoucher());

    }

    @Override
    public void onExChanageResult(String msg) {
        showToast(msg);
      onRefresh();
    }

    @Override
    public void onRefundResult(String msg) {
        showToast(msg);
        onRefresh();
    }

    @Override
    protected MemberDetailPresenter onCtreatPresenter() {
        return new MemberDetailPresenter(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.cashregister_member_detail_fragment;
    }

    @Override
    protected void onCreateView(View view, Bundle argment) {
        org.greenrobot.eventbus.EventBus.getDefault().register(this);
        ButterKnife.bind(this, view);
        mCardPresenter = new AddCardPresenter(this);
        mRechargeView = new RechargeView(getContext(), this);
        LinearLayoutManager ms = new LinearLayoutManager(view.getContext());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(ms);
        mAdapter = new CouponAdapter(R.layout.cashregister_coupon_layout, mList);
        mRecyclerView.setAdapter(mAdapter);
        mChanageLayout = LayoutInflater.from(getContext()).inflate(R.layout.cashregister_chanage_card_layout, null);
        mCardNumEditText = mChanageLayout.findViewById(R.id.cashregister_input_edite);
        mCardNumEditText.setInputType(InputType.TYPE_NULL);
        mNumTextview = mChanageLayout.findViewById(R.id.cashregister_card_codeing_num);
        mCardNumEditText.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                            mCardPresenter.getCardMsg(Param.Keys.INFO, mCardNumEditText.getText().toString());
                            return true;
                        }
                        return false;
                    }
                }
        );
        TextView cancelTv = mChanageLayout.findViewById(R.id.dialog_left_tv);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChanageDialog.dismiss();
            }
        });
        TextView surelTv = mChanageLayout.findViewById(R.id.dialog_right_tv);
        surelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.exChanageCard(Param.Keys.TOKEN, getToken(), Param.Keys.id, mMber.getId() + "", Param.Keys.ENTITY_CARD_NUM, mNumTextview.getText().toString());
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.getMemberDetail(Param.Keys.TOKEN, getToken(), Param.Keys.id, id);
    }

    public void updateId(String id) {
        this.id = id;
        if (mPresenter != null) {
            mPresenter.getMemberDetail(Param.Keys.TOKEN, getToken(), Param.Keys.id, id);
        }
    }

    @OnClick({R2.id.cashregister_member_detail_back_layout, R2.id.cashregister_freeze_tv, R2.id.cashregister_refund_tv, R2.id.cashregister_cancellation_tv, R2.id.cashregister_recharge_btn, R2.id.cashregister_change_card, R2.id.cashregister_update_member_msg})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R2.id.cashregister_member_detail_back_layout:
                org.greenrobot.eventbus.EventBus.getDefault().post(new FragmentEvent(ChildViewClick.MEMBER_DETAIL_BACK));
                break;
            case R2.id.cashregister_freeze_tv:
                String notice="";
                switch (mFroenType){
                    case 1:
                        notice="确认要冻结该会员吗?";
                        break;
                    case 2:
                        notice="确认要解除冻结状态么?";
                        break;
                }
                mTwoBtnDialog = DialogUtil.showDialogTwoButton(getActivity(), "提示", notice, "取消", null, "继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.memberFrozen(Param.Keys.id, mMber.getId() + "", Param.Keys.STATUS_TYPE, mFroenType + "");
                    }
                });
                mTwoBtnDialog.setOnDismissListener(this);
                break;
            case R2.id.cashregister_refund_tv:
                mTwoBtnDialog = DialogUtil.showDialogTwoButton(getActivity(), "确认要退款吗?",
                        " 退款金额: " + Param.Keys.RMB + mMber.getMoney_principal() + "\n 积分扣除：" + mIntegralBalance.getText() + "\n 成长值：" + mGrowthValue.getText() + "\n" + "会员等级：" + mMemberLevelName.getText(), "取消", null, "确认退款", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mPresenter.refund(Param.Keys.TOKEN, getToken(), Param.Keys.id, mMber.getId() + "", Param.Keys.SELLING_ID, "", Param.Keys.PERSION_IN_CHARGE, "");
                            }
                        });
                mTwoBtnDialog.setOnDismissListener(this);
                break;
            case R2.id.cashregister_cancellation_tv:
                mTwoBtnDialog = DialogUtil.showDialogTwoButton(getActivity(), "确认要注销吗?", "退款金额:" + Param.Keys.RMB + mMber.getMoney_principal(), "取消", null, "确认注销", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.deleteMember(Param.Keys.id, mMber.getId() + "");
                    }
                });
                mTwoBtnDialog.setOnDismissListener(this);
                break;
            case R2.id.cashregister_recharge_btn:
                mRechargeView.show();
                break;
            case R2.id.cashregister_change_card:
                if (mChanageDialog == null) {
                    mChanageDialog = DialogUtil.contentDialog(getActivity(), mChanageLayout);
                } else {
                    mChanageDialog.show();
                }
                mChanageDialog.setOnDismissListener(this);
                break;
            case R2.id.cashregister_update_member_msg:
                Intent intent = new Intent(getActivity(), AddAndUpdateMemberActivity.class);
                intent.putExtra(Param.Keys.MEMBER_DATA, mMber);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResult(List<Cardbean> cardbean) {

    }

    @Override
    public void onAddresult(String msg) {

    }

    @Override
    public void onDeleteResult(String msg) {
        showToast(msg);
        org.greenrobot.eventbus.EventBus.getDefault().post(new FragmentEvent(ChildViewClick.MEMBER_DETAIL_BACK));
    }

    @Override
    public void onFrozenResult(String msg) {
        showToast(msg);
        updateId(id);
    }

    @Override
    public void onCodeCardError(String msg) {

    }

    @Override
    public void onCardMsg(Cardbean cardbean) {
        mNumTextview.setText(cardbean.getCoding_card());
        mCardNumEditText.setText("");
        isExcute = false;
        hideInputMethod();
    }

    @Override
    public void onResultSuc() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        onInitData(null);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(EventRouter event) {
        switch (event.getAction()) {
            case MEMBER_MSG:
                updateId(id);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        hideInputMethod();
    }
}

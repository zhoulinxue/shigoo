package com.xgsb.cashregister.customViews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xgsb.cashregister.R;
import com.xgsb.cashregister.R2;
import com.xgsb.cashregister.activitys.SellerSearchActivity;
import com.xgsb.cashregister.adapter.MemberReChargeGivesAdapter;
import com.xgsb.cashregister.mvp.contacts.RechargeContact;
import com.xgsb.cashregister.mvp.presenter.RechargePresenter;
import com.xgsb.cashregister.utils.DialogUtil;
import com.zx.datafactory.bean.Member;
import com.zx.datafactory.bean.MemberReChargeGive;
import com.zx.datafactory.bean.Sellerbean;
import com.zx.network.Param;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Name: RechargeView
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-04 16:21
 */
public class RechargeView extends MvpCustomView<RechargePresenter> implements RechargeContact.view {
    @BindView(R2.id.cashregister_member_detail_mobile_num)
    TextView mRechargePhoneTv;
    @BindView(R2.id.cashregister_card_num)
    TextView mUserCardNum;
    @BindView(R2.id.cashregister_member_name)
    TextView mNameTv;
    @BindView(R2.id.cashregister_card_level_name)
    TextView mLevelTv;
    @BindView(R2.id.cashregister_rechare_give_recyclerView)
    RecyclerView mRechargeGives;
    @BindView(R2.id.cashregister_recharge_tv)
    EditText mRechargeEdite;
    @BindView(R2.id.cashregister_give_edite)
    EditText mGiveEdite;
    @BindView(R2.id.cashregister_btn_upcash)
    ImageView mUpcashImg;
    @BindView(R2.id.cashregister_cash)
    ImageView mCashImg;
    @BindView(R2.id.cashregister_btn_wechat)
    ImageView mWeChatImg;
    @BindView(R2.id.cashregister_btn_alipay)
    ImageView mALiImg;
    @BindView(R2.id.cashregister_rest_money)
    TextView mRestMoney;
    @BindView(R2.id.cashregister_recharge_result_tv)
    TextView mRechargeResult;
    @BindView(R2.id.cashregister_seller_textview)
    TextView mAutoCompleteTextView;
    private Member mRechargeMember;
    private MemberReChargeGivesAdapter mMemberReChargeGivesAdapter;
    private List<MemberReChargeGive> mReChargeGives;
    private Dialog mReChargeDialog;
    private int mPayType;
    private Sellerbean mSellerbean;
    private RechargeLisenter mLisenter;

    @Override
    protected RechargePresenter onCtreatPresenter() {
        return new RechargePresenter(this);
    }

    public RechargeView(Context context, ViewGroup rootGroup) {
        super(context, rootGroup);
    }

    public RechargeView(Context context) {
        super(context);
    }

    public RechargeView(Context context, ViewGroup rootGroup, RechargeLisenter mLisenter) {
        super(context, rootGroup);
        this.mLisenter = mLisenter;
    }

    public RechargeView(Context context, RechargeLisenter mLisenter) {
        super(context);
        this.mLisenter = mLisenter;
    }

    @Override
    protected void onInitData() {
        mPresenter.getReChareList(Param.Keys.TOKEN, getToken());
    }

    @Override
    public int initLayout() {
        return R.layout.cashregister_recharge_dialog_layout;
    }


    public void setGiveData(List<MemberReChargeGive> reChargeGives) {
        mReChargeGives = reChargeGives;
        if (mMemberReChargeGivesAdapter != null) {
            mMemberReChargeGivesAdapter.setNewData(reChargeGives);
        }
    }

    public void setUserInfo(Member rechargeMember) {
        this.mRechargeMember = rechargeMember;
        if (rechargeMember != null) {
            if (mRechargePhoneTv != null) {
                mRechargePhoneTv.setText(mRechargeMember.getMobile());
            }
            if (mUserCardNum != null) {
                mUserCardNum.setText(mRechargeMember.getCoding_card());
            }
            if (mNameTv != null) {
                mNameTv.setText(mRechargeMember.getName());
            }
            if (mLevelTv != null) {
                mLevelTv.setText(mRechargeMember.getGrade_name());
            }
            if (mRestMoney != null) {
                mRestMoney.setText(Param.Keys.RMB + rechargeMember.getMoney() + Param.Keys.YUAN);
            }
        } else {
            if (mRechargePhoneTv != null) {
                mRechargePhoneTv.setText("");
            }
            if (mUserCardNum != null) {
                mUserCardNum.setText("");
            }
            if (mNameTv != null) {
                mNameTv.setText("");
            }
            if (mLevelTv != null) {
                mLevelTv.setText("");
            }
            if (mRestMoney != null) {
                mRestMoney.setText("");
            }
        }

    }

    public void show() {
        if (mRechargeMember != null) {
            if (mReChargeDialog == null) {
                mReChargeDialog = DialogUtil.contentDialog((Activity) getContext(), getView());
                mReChargeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        setUserInfo(null);
                    }
                });
            } else {
                mReChargeDialog.show();
            }
        } else {
            showToast("请先选择用户");
        }
    }


    @Override
    public void onReChareList(List<MemberReChargeGive> reChargeGives) {
        setGiveData(reChargeGives);
    }

    @Override
    public void onRechargeResult(String msg) {
        showToast(msg);
        mReChargeDialog.dismiss();
        if (mLisenter != null) {
            mLisenter.onResultSuc();
        }
    }

    @Override
    public void onGiveResult(double give) {
        try {
            if (mRechargeResult != null && !TextUtils.isEmpty(mRechargeEdite.getText().toString())) {
                mRechargeResult.setText(String.format("赠 %s 元，共储值 %s 元", give + "", (Double.valueOf(mRechargeEdite.getText().toString()) + give) + ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showToast("储值金额 的 类型错误");
        }

        if (mGiveEdite != null) {
            mGiveEdite.setText(give + "");
        }
    }

    @Override
    protected void onInitView(Context context, View rootView) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRechargeGives.setLayoutManager(manager);
        mMemberReChargeGivesAdapter = new MemberReChargeGivesAdapter(R.layout.cashregister_member_recharge_give_item_layout, mReChargeGives);
        mMemberReChargeGivesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MemberReChargeGive give = mMemberReChargeGivesAdapter.onSelected(position);
                mRechargeEdite.setText(give.getAmount_of_money());
                mGiveEdite.setText(give.getGive());
            }
        });
        mRechargeGives.setAdapter(mMemberReChargeGivesAdapter);
        ColorMatrixColorFilter filter = getGrayColorFilter();
        mALiImg.setColorFilter(filter);
        mWeChatImg.setColorFilter(filter);
        mUpcashImg.setColorFilter(filter);
        mCashImg.setColorFilter(filter);
        mGiveEdite.setEnabled(false);
        mRechargeEdite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.getGives(Param.Keys.MONEY, charSequence.toString());
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getContext() instanceof Activity) {
                    Intent intent = new Intent(getContext(), SellerSearchActivity.class);
                    getContext().startActivity(intent);
                }
            }
        });

    }

    @OnClick({R2.id.cashregister_btn_alipay, R2.id.cashregister_btn_wechat, R2.id.cashregister_btn_upcash, R2.id.cashregister_cash, R2.id.cashregister_dialog_left_tv, R2.id.cashregister_dialog_right_tv})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R2.id.cashregister_btn_alipay:
                mPayType = 2;
                ColorMatrixColorFilter filter = getGrayColorFilter();
                mALiImg.setColorFilter(null);
                mWeChatImg.setColorFilter(filter);
                mUpcashImg.setColorFilter(filter);
                mCashImg.setColorFilter(filter);
                break;
            case R2.id.cashregister_btn_upcash:
                mPayType = 3;
                mALiImg.setColorFilter(getGrayColorFilter());
                mWeChatImg.setColorFilter(getGrayColorFilter());
                mUpcashImg.setColorFilter(null);
                mCashImg.setColorFilter(getGrayColorFilter());
                break;
            case R2.id.cashregister_cash:
                mPayType = 4;
                mALiImg.setColorFilter(getGrayColorFilter());
                mWeChatImg.setColorFilter(getGrayColorFilter());
                mUpcashImg.setColorFilter(getGrayColorFilter());
                mCashImg.setColorFilter(null);
                break;
            case R2.id.cashregister_btn_wechat:
                mPayType = 1;
                mALiImg.setColorFilter(getGrayColorFilter());
                mWeChatImg.setColorFilter(null);
                mUpcashImg.setColorFilter(getGrayColorFilter());
                mCashImg.setColorFilter(getGrayColorFilter());
                break;
            case R2.id.cashregister_dialog_left_tv:
                mReChargeDialog.dismiss();
                break;
            case R2.id.cashregister_dialog_right_tv:
                String sellerId = mSellerbean == null ? "" : mSellerbean.getId() + "";
                mPresenter.recharge(Param.Keys.TOKEN, getToken(), Param.Keys.SELLING_ID, sellerId, Param.Keys.MONEY_PRINCIPAL, mRechargeEdite.getText().toString(), Param.Keys.id, mRechargeMember.getId() + "", Param.Keys.PAY_TYPE, mPayType + "", Param.Keys.SELLING_ID, "");
                break;
        }
    }

    public ColorMatrixColorFilter getGrayColorFilter() {
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0); // 设置饱和度
        ColorMatrixColorFilter grayColorFilter = new ColorMatrixColorFilter(cm);
        return grayColorFilter;
    }

    public void setSellerInfo(Sellerbean sellerbean) {
        this.mSellerbean = sellerbean;
        mAutoCompleteTextView.setText(sellerbean.getStaff_name());
    }

    public interface RechargeLisenter {
        public void onResultSuc();
    }
}

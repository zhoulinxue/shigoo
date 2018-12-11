package com.xgsb.cashregister.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xgsb.cashregister.customViews.ReChargeHeaderView;
import com.xgsb.cashregister.customViews.WebChartView;
import com.xgsb.cashregister.R;
import com.xgsb.cashregister.mvp.contacts.MemberRechargeContact;
import com.xgsb.cashregister.mvp.presenter.RecahrgePresenter;
import com.xgsb.cashregister.utils.DialogUtil;
import com.xgsb.cashregister.web.onOperateLisenter;
import com.zx.datafactory.JSONManager;
import com.zx.datafactory.bean.ReChargeListData;
import com.zx.datafactory.bean.ReChargebean;
import com.zx.datafactory.bean.WebData;
import com.zx.mvplibrary.MvpFragment;
import com.zx.network.Param;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.starteos.dappsdk.Request;
import io.starteos.dappsdk.Response;

/**
 * Name: RechargeDetailListFragment
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-28 10:04
 */
public class RechargeDetailListFragment extends MvpFragment<RecahrgePresenter> implements MemberRechargeContact.view, onOperateLisenter {
    @BindView(R.id.web_page_header_layout)
    FrameLayout mHeaderViewLayout;
    @BindView(R.id.web_page_footer_layout)
    FrameLayout mFooterView;
    @BindView(R.id.web_chart_layout)
    FrameLayout mWebChartContainer;
    private ReChargeHeaderView mHeader;
    private WebChartView mWebCahrtView;
    private Request mRequest;
    private ReChargeListData mList;
    private Dialog mCancelationDialog;
    private View mCancelLayout;
    private ReChargebean mRechargebean;
    TextView mPhoneNum, mRechargeMoney, mPreReCharge, mValueRec, mCardNum;

    public static RechargeDetailListFragment newInstance() {
        RechargeDetailListFragment fragment = new RechargeDetailListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onGetReChargeListCallback(ReChargeListData data) {
        this.mList = data;
        if (mRequest != null) {
            if (data != null && data.getData() != null) {
                mHeader.setQueryNum(data.getData().size());
                mHeader.setNum(data);
                String json = WebData.newInstance().getRechargeListData(data.getData(), mWebCahrtView.getWidth(), mWebCahrtView.getHight());
                Response response = getResone(json);
                mWebCahrtView.callback(mRequest, response);
            }
        }
    }

    @Override
    public void onCancelLationResult(String msg) {
        showToast(msg);
        mCancelationDialog.dismiss();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mWebCahrtView.refresh("refresh");
    }

    private Response getResone(String json) {
        Response response = null;
        try {
            if (json.startsWith("[")) {
                response = new Response(Response.CODE_SUCCESS, "success", new JSONArray(json));
            } else {
                response = new Response(Response.CODE_SUCCESS, "success", new JSONObject(json));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected RecahrgePresenter onCtreatPresenter() {
        return new RecahrgePresenter(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.cashregister_recharge_fragment;
    }

    @Override
    protected void onCreateView(View view, Bundle argment) {
        ButterKnife.bind(this, view);
        mHeader = new ReChargeHeaderView(getContext(), mHeaderViewLayout);
        mWebCahrtView = new WebChartView(view.getContext(), mWebChartContainer, this, mHandler);
        mWebCahrtView.loadDefaultUrl();
        mHeader.getSearchBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebCahrtView.refresh("refresh");
            }
        });
        initDialog();
        mHeader.getCleanBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHeader.clean();
                onRefresh();
            }
        });
    }

    private void initDialog() {
        mCancelLayout = LayoutInflater.from(getContext()).inflate(R.layout.cashregister_cancelation_diaolog_layout, null);
        mPhoneNum = mCancelLayout.findViewById(R.id.moblie_phone);
        mRechargeMoney = mCancelLayout.findViewById(R.id.consume_recharge_money);
        mPreReCharge = mCancelLayout.findViewById(R.id.rechare_pre);
        mValueRec = mCancelLayout.findViewById(R.id.rechare_vlaue);
        mCardNum = mCancelLayout.findViewById(R.id.card_num);
        mCancelLayout.findViewById(R.id.dialog_left_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCancelationDialog.dismiss();
            }
        });
        mCancelLayout.findViewById(R.id.dialog_right_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.cancelation(Param.Keys.id, mRechargebean.getId() + "");
            }
        });
    }


    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.getReChargeList(Param.Keys.TOKEN, getToken(), Param.Keys.DATA, mHeader.getKey(), Param.Keys.START_DATE, mHeader.getStartTime(), Param.Keys.END_DATE, mHeader.getEndTime());
    }

    @Override
    public void onGettableInfo(Request request) {
        mRequest = request;
        if (mList != null) {
            onGetReChargeListCallback(mList);
        }
    }

    @Override
    public void onOperate(Request request) {
        String operate = request.getParams().optString("method");
        switch (operate) {
            case "撤销":
                mRechargebean = (ReChargebean) JSONManager.getInstance().parseObject(request.getParams().opt("row_data") + "", ReChargebean.class);
                if (!"3".equals(mRechargebean.getOrder_status())) {
                    initData(mRechargebean);
                    if (mCancelationDialog == null) {
                        mCancelationDialog = DialogUtil.contentDialog(getActivity(), mCancelLayout);
                    } else {
                        mCancelationDialog.show();
                    }
                } else {
                    showToast("退款不能被撤销");
                }
                break;

        }
    }

    private void initData(ReChargebean rechargebean) {
        mPhoneNum.setText(rechargebean.getMobile());
//        int real = rechargebean.getMoney();
//        try {
//            if (!TextUtils.isEmpty(rechargebean.getMoney_give())) {
//                real = rechargebean.getMoney() - ((int) Double.valueOf(rechargebean.getMoney_give()).doubleValue());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            AppLog.print("撤销时 数据类型错误");
//        }
        mValueRec.setText(Param.Keys.RMB + rechargebean.getStored_value_money() + "");
        mPreReCharge.setText(Param.Keys.RMB + rechargebean.getMoney_give() + "");
        mRechargeMoney.setText(Param.Keys.RMB + rechargebean.getMoney());
        mCardNum.setText(rechargebean.getCard_number());
    }

    @Override
    public void onSearch(Request request) {
        this.mRequest = request;
        onInitData(null);
    }
}

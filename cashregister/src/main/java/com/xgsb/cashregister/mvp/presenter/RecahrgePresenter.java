package com.xgsb.cashregister.mvp.presenter;

import com.xgsb.cashregister.mvp.contacts.CashLoginContact;
import com.xgsb.cashregister.mvp.contacts.MemberRechargeContact;
import com.zx.datafactory.bean.ListData;
import com.zx.datafactory.bean.ReChargeListData;
import com.zx.datafactory.bean.ReChargebean;
import com.zx.datafactory.bean.User;
import com.zx.api.api.netWork.NetRequest;
import com.zx.api.api.netWork.NetRequestCallBack;
import com.zx.mvplibrary.presenter.BasePresenterImpl;
import com.zx.network.OKHttp.ApiManager;

/**
 * Name: CashLoginPresenter
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-12 14:27
 */
public class RecahrgePresenter extends BasePresenterImpl<MemberRechargeContact.view> implements MemberRechargeContact.presenter {

    public RecahrgePresenter(MemberRechargeContact.view view) {
        super(view);
    }

    NetRequestCallBack<ReChargeListData> reChargeCallback = new NetRequestCallBack<ReChargeListData>() {
        @Override
        public void onSuccess(ReChargeListData reChargebeanListData) {
            mView.dismissLoadingDiaog();
            mView.onGetReChargeListCallback(reChargebeanListData);
        }

        @Override
        public void onError(int responseCode, String msg) {
            mView.dismissLoadingDiaog();
            mView.onError(msg);
        }
    };
    NetRequestCallBack<String> mCancelCallback = new NetRequestCallBack<String>() {
        @Override
        public void onSuccess(String reChargebeanListData) {
            mView.dismissLoadingDiaog();
            mView.onCancelLationResult(reChargebeanListData);
        }

        @Override
        public void onError(int responseCode, String msg) {
            mView.dismissLoadingDiaog();
            mView.onError(msg);
        }
    };

    @Override
    public void getReChargeList(String... params) {
        mView.showLoadingDialog();
        NetRequest request = ApiManager.getInstance().getReChargeList(params, reChargeCallback);
        addRequest(request);
    }

    @Override
    public void cancelation(String... params) {
        mView.showLoadingDialog();
        NetRequest request = ApiManager.getInstance().cancelRecharge(params, mCancelCallback);
        addRequest(request);
    }
}

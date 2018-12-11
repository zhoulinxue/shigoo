package com.xgsb.cashregister.mvp.presenter;

import com.xgsb.cashregister.mvp.contacts.ConsumeContact;
import com.xgsb.cashregister.mvp.contacts.MemberRechargeContact;
import com.zx.datafactory.bean.ConsumeListData;
import com.zx.datafactory.bean.Consumebean;
import com.zx.datafactory.bean.ListData;
import com.zx.datafactory.bean.ReChargebean;
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
public class ConsumePresenter extends BasePresenterImpl<ConsumeContact.view> implements ConsumeContact.presenter {

    public ConsumePresenter(ConsumeContact.view view) {
        super(view);
    }

    NetRequestCallBack<ConsumeListData> reChargeCallback = new NetRequestCallBack<ConsumeListData>() {
        @Override
        public void onSuccess(ConsumeListData reChargebeanListData) {
            mView.dismissLoadingDiaog();
            mView.onGetConsumeListCallback(reChargebeanListData);
        }

        @Override
        public void onError(int responseCode, String msg) {
            mView.dismissLoadingDiaog();
            mView.onError(msg);
        }
    };

    @Override
    public void getConsumeList(String... params) {
        mView.showLoadingDialog();
        NetRequest request = ApiManager.getInstance().getConsumeList(params, reChargeCallback);
        addRequest(request);
    }
}

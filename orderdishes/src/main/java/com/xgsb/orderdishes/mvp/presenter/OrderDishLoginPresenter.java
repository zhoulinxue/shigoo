package com.xgsb.orderdishes.mvp.presenter;

import com.xgsb.orderdishes.mvp.contacts.OrderDishLoginContact;
import com.zx.api.api.netWork.NetRequest;
import com.zx.api.api.netWork.NetRequestCallBack;
import com.zx.mvplibrary.presenter.BasePresenterImpl;
import com.zx.network.OKHttp.ApiManager;

/**
 * Name: OrderDishLoginPresenter
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-12 14:27
 */
public class OrderDishLoginPresenter extends BasePresenterImpl<OrderDishLoginContact.view> implements OrderDishLoginContact.presenter {

    public OrderDishLoginPresenter(OrderDishLoginContact.view view) {
        super(view);
    }

    NetRequestCallBack<com.zx.datafactory.bean.User> mCashLoginCallback = new NetRequestCallBack<com.zx.datafactory.bean.User>() {
        @Override
        public void onSuccess(com.zx.datafactory.bean.User user) {
            mView.dismissLoadingDiaog();
            mView.loginCallbak(user);
        }

        @Override
        public void onError(int responseCode, String msg) {
            mView.dismissLoadingDiaog();
            mView.showToast(msg);
        }
    };

    @Override
    public void cashLogin(com.zx.datafactory.bean.User user) {
        mView.showLoadingDialog();
        NetRequest request = ApiManager.getInstance().cashLogin(mCashLoginCallback, user);
        addRequest(request);
    }
}

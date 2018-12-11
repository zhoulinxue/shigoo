package com.xgsb.cashregister.mvp.presenter;

import com.xgsb.cashregister.mvp.contacts.MemberDetailContact;
import com.zx.datafactory.bean.ListData;
import com.zx.datafactory.bean.Member;
import com.zx.api.api.netWork.NetRequest;
import com.zx.api.api.netWork.NetRequestCallBack;
import com.zx.mvplibrary.presenter.BasePresenter;
import com.zx.mvplibrary.presenter.BasePresenterImpl;
import com.zx.network.OKHttp.ApiManager;

/**
 * Name: MemberDetailPresenter
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-27 13:15
 */
public class MemberDetailPresenter extends BasePresenterImpl<MemberDetailContact.view> implements MemberDetailContact.presenter {


    public MemberDetailPresenter(MemberDetailContact.view view) {
        super(view);
    }

    NetRequestCallBack<Member> mMemberCallback = new NetRequestCallBack<Member>() {

        @Override
        public void onSuccess(Member member) {
            mView.dismissLoadingDiaog();
            mView.onResult(member);
        }

        @Override
        public void onError(int responseCode, String msg) {
            mView.dismissLoadingDiaog();
            mView.onError(msg);
        }
    };
    NetRequestCallBack<String> meChanageCardCallback = new NetRequestCallBack<String>() {
        @Override
        public void onSuccess(String s) {
            mView.dismissLoadingDiaog();
            mView.onExChanageResult(s);
        }

        @Override
        public void onError(int responseCode, String msg) {
            mView.dismissLoadingDiaog();
            mView.onError(msg);
        }
    };
    NetRequestCallBack<String> mRefundCardCallback = new NetRequestCallBack<String>() {
        @Override
        public void onSuccess(String s) {
            mView.dismissLoadingDiaog();
            mView.onRefundResult(s);
        }

        @Override
        public void onError(int responseCode, String msg) {
            mView.dismissLoadingDiaog();
            mView.onError(msg);
        }
    };
    NetRequestCallBack<String> mDeleteCardCallback = new NetRequestCallBack<String>() {
        @Override
        public void onSuccess(String s) {
            mView.dismissLoadingDiaog();
            mView.onDeleteResult(s);
        }

        @Override
        public void onError(int responseCode, String msg) {
            mView.dismissLoadingDiaog();
            mView.onError(msg);
        }
    };

    @Override
    public void getMemberDetail(String... params) {
        mView.showLoadingDialog();
        NetRequest request = ApiManager.getInstance().getMemberDetail(params, mMemberCallback);
        addRequest(request);
    }

    @Override
    public void exChanageCard(String... params) {
        mView.showLoadingDialog();
        NetRequest request = ApiManager.getInstance().exChanageCard(params, meChanageCardCallback);
        addRequest(request);
    }

    @Override
    public void refund(String... params) {
        mView.showLoadingDialog();
        NetRequest request = ApiManager.getInstance().refund(params, mRefundCardCallback);
        addRequest(request);
    }

    @Override
    public void deleteMember(String... params) {
        mView.showLoadingDialog();
        NetRequest request = ApiManager.getInstance().deleteMember(params, mDeleteCardCallback);
        addRequest(request);
    }

    @Override
    public void memberFrozen(String... params) {
        NetRequest request = ApiManager.getInstance().frozen(params, mDeleteCardCallback);
        addRequest(request);
    }
}

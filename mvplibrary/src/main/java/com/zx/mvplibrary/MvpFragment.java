package com.zx.mvplibrary;

import android.app.Activity;
import android.text.TextUtils;

import com.zx.api.api.app.MvpDialog;
import com.zx.api.api.app.onChildViewClick;
import com.zx.api.api.mvp.BaseView;
import com.zx.api.api.utils.AppUtil;
import com.zx.api.api.utils.SPUtil;
import com.zx.mvplibrary.presenter.BasePresenter;
import com.zx.network.Param;

/**
 * Name: MvpFragment
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-26 16:20
 */
public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView {
    protected P mPresenter;
    private MvpDialog mDialog;

    @Override
    protected void preOnCreatView() {
        mPresenter = onCtreatPresenter();
    }

    /**
     * 初始化 presenter
     *
     * @return
     */
    protected abstract P onCtreatPresenter();

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(String msg) {
        if (!isNetWorkconnected()) {
            showToast("网络连接已断开");
            return;
        }
        showToast(msg);
    }

    protected boolean isNetWorkconnected() {
        return !TextUtils.isEmpty(AppUtil.getNetworkState(getActivity()));
    }

    @Override
    public void showLoadingDialog() {
        if (mDialog != null)
            mDialog.show();
    }

    @Override
    public void showToast(String msg) {
        mHandler.post(new ToastRunable(getActivity(), msg));
    }

    @Override
    public void showToast(int res) {
        if (res != 0)
            showToast(getResources().getString(res));
    }

    @Override
    public void dismissLoadingDiaog() {
        mDialog.dissmiss();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDialog = onCreatCustomDialog();
    }

    @Override
    public MvpDialog onCreatCustomDialog() {
        return new ImmersionBarDialog(getActivity(), R.string.loading_text);
    }

    @Override
    public String getToken() {
        return SPUtil.getInstance().getString(Param.Keys.TOKEN, "default_token");
    }
}

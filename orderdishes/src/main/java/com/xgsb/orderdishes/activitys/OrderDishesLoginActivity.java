package com.xgsb.orderdishes.activitys;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xgsb.orderdishes.R;
import com.xgsb.orderdishes.R2;
import com.xgsb.orderdishes.mvp.contacts.OrderDishLoginContact;
import com.xgsb.orderdishes.mvp.presenter.OrderDishLoginPresenter;
import com.zx.api.api.utils.SPUtil;
import com.zx.datafactory.enu.EventBusAction;
import com.zx.mvplibrary.MvpActivity;
import com.zx.network.Param;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Name: SplashActivity
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-21 12:37
 */
public class OrderDishesLoginActivity extends MvpActivity<OrderDishLoginPresenter> implements OrderDishLoginContact.view {
    @BindView(R2.id.ordedishes_cash_login_num_edit)
    AppCompatEditText mNumberEdite;
    @BindView(R2.id.ordedishes_cash_login_psw_edit)
    AppCompatEditText mPswEdite;
    @BindView(R2.id.ordedishes_cash_login_tv)
    TextView mLoginTv;

    @Override
    protected int initLayout() {
        return R.layout.ordedishes_login_activity;
    }

    @Override
    protected void onCreateView() {
        ButterKnife.bind(this);
        singleClickOnMinutes(mLoginTv, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.zx.datafactory.bean.User user = loginUser(mNumberEdite.getText().toString(), mPswEdite.getText().toString());
                if (isReady(user)) {
                    mPresenter.cashLogin(user);
                }
            }
        });
    }

    /**
     * 校验 工号 及 密码 格式
     *
     * @param user
     * @return
     */
    private boolean isReady(com.zx.datafactory.bean.User user) {
        if (user != null) {
            if (TextUtils.isEmpty(user.getUserName())) {
                showToast(R.string.ordedishes_empty_name);
                return false;
            }
            if (TextUtils.isEmpty(user.getPsw())) {
                showToast(R.string.ordedishes_empty_psw);
                return false;
            }
        }
        return true;
    }

    /**
     * 组装登录用户
     *
     * @param userNum
     * @param userPsw
     * @return
     */
    private com.zx.datafactory.bean.User loginUser(String userNum, String userPsw) {
        return new com.zx.datafactory.bean.User(userNum, userPsw);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }

    @Override
    protected OrderDishLoginPresenter onCtreatPresenter() {
        return new OrderDishLoginPresenter(this);
    }

    @Override
    public void loginCallbak(com.zx.datafactory.bean.User user) {
        showToast("登录成功");
        SPUtil.getInstance().put(Param.Keys.TOKEN, user.getSignature());
        EventBus.getDefault().post(EventBusAction.LOGIN_SUC);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(EventBusAction.MAIN);
    }
}

package com.xgsb.cashregister.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xgsb.cashregister.R;
import com.xgsb.cashregister.R2;
import com.zx.mvplibrary.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xgsb.cashregister.activitys.CashRigisterMainActivity.TEST;

/**
 * Name: TableFragment
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-01 14:27
 */
public class OrdersFragment extends BaseFragment {
    @BindView(R2.id.cashregister_test_text)
    TextView testTv;


    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void preOnCreatView() {

    }

    @Override
    protected int initLayout() {
        return R.layout.cashregister_text_layout;
    }

    @Override
    protected void onCreateView(View view, Bundle argment) {
        ButterKnife.bind(this, view);
        testTv.setText(argment.getString(TEST));
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }
}

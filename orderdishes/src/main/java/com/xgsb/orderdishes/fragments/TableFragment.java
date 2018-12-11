package com.xgsb.orderdishes.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.xgsb.orderdishes.R;
import com.xgsb.orderdishes.R2;
import com.xgsb.orderdishes.customViews.OrderDishTableLeftView;
import com.xgsb.orderdishes.mvp.contacts.TableContact;
import com.xgsb.orderdishes.mvp.presenter.TablePresenter;
import com.zx.datafactory.bean.Table;
import com.zx.mvplibrary.MvpFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Name: TableFragment
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-01 14:27
 */
public class TableFragment extends MvpFragment<TablePresenter> implements TableContact.view {
    @BindView(R2.id.ordedishes_table_left_container)
    FrameLayout mLeftLayout;
    @BindView(R2.id.ordedishes_table_right_container)
    FrameLayout mRightLayout;
    OrderDishTableLeftView mLeftView;

    public static TableFragment newInstance() {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected TablePresenter onCtreatPresenter() {
        return new TablePresenter(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.ordedishes_table_layout;
    }

    @Override
    protected void onCreateView(View view, Bundle argment) {
        ButterKnife.bind(this, view);
        mLeftView = new OrderDishTableLeftView(getContext(), mLeftLayout);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.getTableList();
    }

    @Override
    public void onTableResult(List<Table> tables) {

    }
}

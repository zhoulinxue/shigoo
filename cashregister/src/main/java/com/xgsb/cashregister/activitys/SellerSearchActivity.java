package com.xgsb.cashregister.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xgsb.cashregister.R;
import com.xgsb.cashregister.R2;
import com.xgsb.cashregister.adapter.SellerListAdapter;
import com.xgsb.cashregister.mvp.contacts.SellerSearchContact;
import com.xgsb.cashregister.mvp.presenter.SellerListPresenter;
import com.xgsb.cashregister.wedgit.ClearableEditTextWithIcon;
import com.zx.datafactory.bean.EventRouter;
import com.zx.datafactory.bean.Sellerbean;
import com.zx.datafactory.enu.EventBusAction;
import com.zx.mvplibrary.MvpActivity;
import com.zx.network.Param;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Name: SellerSearchActivity
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-06 10:47
 */
public class SellerSearchActivity extends MvpActivity<SellerListPresenter> implements SellerSearchContact.view {
    @BindView(R2.id.cashregister_seller_search_edite)
    ClearableEditTextWithIcon mKeySearchEdite;
    @BindView(R2.id.cashregister_seller_recycerView)
    RecyclerView mRecycerView;
    private SellerListAdapter mSellerAdapter;
    private List<Sellerbean> mList = new ArrayList<>();

    @Override
    public void onSellerListResult(List<Sellerbean> list) {
        if (mSellerAdapter != null) {
            mSellerAdapter.setNewData(list);
        }
    }

    @Override
    protected SellerListPresenter onCtreatPresenter() {
        return new SellerListPresenter(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.cashregister_search_seller_list_activity;
    }

    @Override
    protected void onCreateView() {
        ButterKnife.bind(this);
        mKeySearchEdite.setIconResource(R.mipmap.cashregister_icon_seach);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycerView.setLayoutManager(manager);
        mSellerAdapter = new SellerListAdapter(R.layout.cashregister_seller_list_item_layou, mList);
        mRecycerView.setAdapter(mSellerAdapter);
        mSellerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                org.greenrobot.eventbus.EventBus.getDefault().post(new EventRouter(EventBusAction.SELLER_INFO,mSellerAdapter.getItem(position)));
                finish();
            }
        });
        mKeySearchEdite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    mPresenter.getSellerList(Param.Keys.DATA, charSequence.toString());
                } else {
                    mList.clear();
                    mSellerAdapter.setNewData(mList);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }

    @OnClick({R2.id.cashregister_cancel_btn})
    public void onViewClilk(View view) {
        switch (view.getId()) {
            case R2.id.cashregister_cancel_btn:
                finish();
                break;
        }
    }
}

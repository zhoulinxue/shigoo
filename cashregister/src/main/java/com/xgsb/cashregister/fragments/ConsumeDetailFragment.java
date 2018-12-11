package com.xgsb.cashregister.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.xgsb.cashregister.R2;
import com.xgsb.cashregister.customViews.ConsumeHeaderView;
import com.xgsb.cashregister.customViews.WebChartView;
import com.xgsb.cashregister.R;
import com.xgsb.cashregister.mvp.contacts.ConsumeContact;
import com.xgsb.cashregister.mvp.presenter.ConsumePresenter;
import com.xgsb.cashregister.web.onOperateLisenter;
import com.zx.datafactory.bean.ConsumeListData;
import com.zx.datafactory.bean.WebData;
import com.zx.api.api.utils.AppLog;
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
public class ConsumeDetailFragment extends MvpFragment<ConsumePresenter> implements ConsumeContact.view, onOperateLisenter {
    @BindView(R2.id.cashregister_web_page_header_layout)
    FrameLayout mHeaderViewLayout;
    @BindView(R2.id.cashregister_web_page_footer_layout)
    FrameLayout mFooterView;
    @BindView(R2.id.cashregister_web_chart_layout)
    FrameLayout mWebChartContainer;
    private ConsumeHeaderView mHeader;
    private WebChartView mWebCahrtView;
    private Request mRequest;
    private ConsumeListData mList;

    public static ConsumeDetailFragment newInstance() {
        ConsumeDetailFragment fragment = new ConsumeDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
    public void onRefresh() {
        super.onRefresh();
        onInitData(null);
    }

    @Override
    protected ConsumePresenter onCtreatPresenter() {
        return new ConsumePresenter(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.cashregister_consume_layout;
    }

    @Override
    protected void onCreateView(View view, Bundle argment) {
        ButterKnife.bind(this, view);
        mHeader = new ConsumeHeaderView(getContext(), mHeaderViewLayout);
        mWebCahrtView = new WebChartView(view.getContext(), mWebChartContainer, this, mHandler);
        mWebCahrtView.loadDefaultUrl();
        mHeader.getSearchbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getConsumeList(Param.Keys.TOKEN, getToken(), Param.Keys.DATA, mHeader.getKey(), Param.Keys.START_DATE, mHeader.getStartTime(), Param.Keys.END_DATE, mHeader.getEndTime());
            }
        });
        mHeader.getCleanBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHeader.clean();
                onRefresh();
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.getConsumeList(Param.Keys.TOKEN, getToken(), Param.Keys.DATA, mHeader.getKey(), Param.Keys.START_DATE, mHeader.getStartTime(), Param.Keys.END_DATE, mHeader.getEndTime());
    }

    @Override
    public void onGettableInfo(Request request) {
        mRequest = request;
        if (mList != null) {
            onGetConsumeListCallback(mList);
        }
    }

    @Override
    public void onOperate(Request request) {

    }

    @Override
    public void onSearch(Request request) {

    }

    @Override
    public void onGetConsumeListCallback(ConsumeListData consumeListData) {
        this.mList = consumeListData;
        if (mRequest != null) {
            if (consumeListData != null && consumeListData.getData() != null && consumeListData.getData().size() != 0) {
                mHeader.setQueryNum(consumeListData.getData().size());
                mHeader.setNum(consumeListData);
                String json = WebData.newInstance().getConsumeListData(consumeListData.getData(), mWebCahrtView.getWidth(), mWebCahrtView.getHight());
                AppLog.print("onGetConsumeListCallback" + json);
                Response response = getResone(json);
                mWebCahrtView.callback(mRequest, response);
            }
        }
    }
}

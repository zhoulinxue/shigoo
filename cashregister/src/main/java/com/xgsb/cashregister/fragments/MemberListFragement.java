package com.xgsb.cashregister.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xgsb.cashregister.R2;
import com.xgsb.cashregister.customViews.MemberListHeaderView;
import com.xgsb.cashregister.customViews.RechargeView;
import com.xgsb.cashregister.customViews.WebChartView;
import com.xgsb.cashregister.R;
import com.xgsb.cashregister.activitys.AddAndUpdateMemberActivity;
import com.xgsb.cashregister.adapter.MemberLevelsAdapter;
import com.xgsb.cashregister.mvp.contacts.AddCardContact;
import com.xgsb.cashregister.mvp.contacts.MemberManageContact;
import com.xgsb.cashregister.mvp.presenter.AddCardPresenter;
import com.xgsb.cashregister.mvp.presenter.MemberManagePresenter;
import com.xgsb.cashregister.utils.DialogUtil;
import com.xgsb.cashregister.web.onOperateLisenter;
import com.zx.datafactory.JSONManager;
import com.zx.datafactory.bean.Cardbean;
import com.zx.datafactory.bean.Countbean;
import com.zx.datafactory.bean.EventRouter;
import com.zx.datafactory.bean.FragmentEvent;
import com.zx.datafactory.bean.Member;
import com.zx.datafactory.bean.MemberLevel;
import com.zx.datafactory.bean.Sellerbean;
import com.zx.datafactory.bean.WebData;
import com.zx.api.api.ChildViewClick;
import com.zx.mvplibrary.MvpFragment;
import com.zx.network.Param;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.starteos.dappsdk.Request;
import io.starteos.dappsdk.Response;

/**
 * Name: MemberListFragement
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //
 * Date: 2018-11-26 13:22
 */
public class MemberListFragement extends MvpFragment<MemberManagePresenter> implements MemberManageContact.view, TextView.OnEditorActionListener, RechargeView.RechargeLisenter, onOperateLisenter, AddCardContact.view {
    @BindView(R2.id.cashregister_web_page_header_layout)
    FrameLayout mHeaderView;
    @BindView(R2.id.cashregister_web_page_footer_layout)
    FrameLayout mFooterView;
    @BindView(R2.id.cashregister_web_chart_layout)
    FrameLayout mWebChartContainer;
    private MemberListHeaderView mMemberHeaderView;
    private List<String> mTitles;
    private int page = 1;
    private Request request;
    private WebChartView mWebCahrtView;
    private List<Member> mList;
    private Dialog mAddialog, mSearchDialog;
    private List<MemberLevel> mLevels;
    private MemberLevelsAdapter mMemberLevelsAdapter;

    private boolean isExcute = false;
    private AddCardPresenter mCardPresenter;
    private int mCardType = 1;
    private RechargeView mRechargeView;
    private TextView entityCardTv;
    private EditText mInputEdite, mobileTv;
    RelativeLayout mInputLayout, mCardInputLayout;


    public static MemberListFragement newInstance() {
        MemberListFragement fragment = new MemberListFragement();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MemberManagePresenter onCtreatPresenter() {
        return new MemberManagePresenter(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.cashregister_member_fragment;
    }

    @Override
    protected void onCreateView(View view, Bundle argment) {
        org.greenrobot.eventbus.EventBus.getDefault().register(this);
        ButterKnife.bind(this, view);
        mCardPresenter = new AddCardPresenter(this);
        mRechargeView = new RechargeView(getContext(), this);
        mTitles = Arrays.asList(getResources().getStringArray(R.array.menber_tab_title));
        mWebCahrtView = new WebChartView(view.getContext(), mWebChartContainer, this, mHandler);
        mMemberHeaderView = new MemberListHeaderView(view.getContext(), mHeaderView);
        mMemberHeaderView.getEditText().setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mMemberHeaderView.getEditText().setOnEditorActionListener(this);
        mMemberHeaderView.getAddCard().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                org.greenrobot.eventbus.EventBus.getDefault().post(new FragmentEvent(ChildViewClick.CARD_FRAGMENT));
            }
        });
        initSearchMemberDialog();
        initAddMemberDialog();
        mWebCahrtView.loadDefaultUrl();
    }

    private void initSearchMemberDialog() {
        final View inputView = LayoutInflater.from(getContext()).inflate(R.layout.cashregister_input_member_dialog, null);
        final EditText memberNumedite = inputView.findViewById(R.id.cashregister_member_input_edite);
        memberNumedite.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        memberNumedite.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    mPresenter.searchMembers(Param.Keys.TOKEN, getToken(), Param.Keys.DATA, memberNumedite.getText().toString());
                    return true;
                }
                return false;
            }
        });
        TextView searchBtn = inputView.findViewById(R.id.cashregister_search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.searchMembers(Param.Keys.TOKEN, getToken(), Param.Keys.DATA, memberNumedite.getText().toString());
            }
        });
        memberNumedite.setInputType(InputType.TYPE_CLASS_NUMBER);
        mMemberHeaderView.getRecahrge().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSearchDialog == null) {
                    mSearchDialog = DialogUtil.contentDialog(getActivity(), inputView);
                } else {
                    mSearchDialog.show();
                }
            }
        });
    }

    private void showReChargeDialog() {
        if (mRechargeView != null) {
            mRechargeView.show();
        }
    }

    private void initAddMemberDialog() {
        LayoutInflater factory = LayoutInflater.from(getContext());//提示框
        final View dialogview = factory.inflate(R.layout.cashregister_new_member_card_diaolog_layout, null);//这里必须是final的
        dialogview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideInputMethod();
            }
        });
        final TextView cacenlTv = dialogview.findViewById(R.id.dialog_left_tv);
        mobileTv = dialogview.findViewById(R.id.cashregister_member_mobile_tv);
        entityCardTv = dialogview.findViewById(R.id.cashregister_entity_card_num_edite);
        final TextView entityCardTrue = dialogview.findViewById(R.id.cashregister_entity_card_tv_true);
        final TextView entityCardFlase = dialogview.findViewById(R.id.cashregister_entity_card_tv_false);
        final LinearLayout cardNumberlayout = dialogview.findViewById(R.id.cashregister_card_num_layout);
        mInputEdite = dialogview.findViewById(R.id.cashregister_input_edite);
        mInputEdite.setInputType(InputType.TYPE_NULL);
        mInputLayout = dialogview.findViewById(R.id.cashregister_input_layout);
        mCardInputLayout = dialogview.findViewById(R.id.cashregister_input_card_layout);
        entityCardTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entityCardFlase.setTextColor(getResources().getColor(R.color.cashregister_menber_num_decript_color));
                entityCardFlase.setBackgroundResource(R.drawable.cashregister_card_level_bg);
                entityCardTrue.setTextColor(getResources().getColor(R.color.colorAccent));
                entityCardTrue.setBackgroundResource(R.drawable.cashregister_card_level_bg_selected);
                cardNumberlayout.setVisibility(View.VISIBLE);
                mCardType = 1;
            }
        });
        entityCardFlase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entityCardTrue.setTextColor(getResources().getColor(R.color.cashregister_menber_num_decript_color));
                entityCardTrue.setBackgroundResource(R.drawable.cashregister_card_level_bg);
                entityCardFlase.setTextColor(getResources().getColor(R.color.colorAccent));
                entityCardFlase.setBackgroundResource(R.drawable.cashregister_card_level_bg_selected);
                cardNumberlayout.setVisibility(View.GONE);
                mCardType = 2;
            }
        });
        entityCardTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardInputLayout.setVisibility(View.VISIBLE);
                mInputLayout.setVisibility(View.GONE);
            }
        });
        mInputEdite.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    mCardPresenter.getCardMsg(Param.Keys.INFO, mInputEdite.getText().toString());
                    return true;
                }
                return false;
            }
        });
        cacenlTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddialog.dismiss();
            }
        });
        TextView sureTv = dialogview.findViewById(R.id.dialog_right_tv);
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.addmember(Param.Keys.TOKEN, getToken(), Param.Keys.CARD_TYPE, mCardType + "", Param.Keys.MOBILE, mobileTv.getText().toString(), Param.Keys.GRADE_ID, mMemberLevelsAdapter.getCurrentLevel(), Param.Keys.ENTITY_CARD_NUM, entityCardTv.getText().toString().trim());
            }
        });
        RecyclerView mlevelsRecyclerView = dialogview.findViewById(R.id.cashregister_levels_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mlevelsRecyclerView.setLayoutManager(manager);
        mMemberLevelsAdapter = new MemberLevelsAdapter(R.layout.cashregister_member_level_item_layout, mLevels);
        mlevelsRecyclerView.setAdapter(mMemberLevelsAdapter);
        mMemberHeaderView.getAddMember().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mAddialog == null) {
//                    mAddialog = DialogUtil.contentDialog(getActivity(), dialogview);
//                } else {
//                    mAddialog.show();
//                }
                startActivity(new Intent(getActivity(), AddAndUpdateMemberActivity.class));
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.getCountData(Param.Keys.TOKEN, getToken());
        mPresenter.getMemberList(Param.Keys.TOKEN, getToken(), Param.Keys.DATA, mMemberHeaderView.getKey(), Param.Keys.PAGE, page + "");
        mPresenter.getMemberLevelList(Param.Keys.TOKEN, getToken());
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mWebCahrtView.refresh("refresh");
        mPresenter.getCountData(Param.Keys.TOKEN, getToken());
    }

    @Override
    public void onGetMenbersCallback(List<Member> list) {
        this.mList = list;
        fillChart(list);
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
    public void onSearchResult(List<Member> list) {
        onGetMenbersCallback(list);
    }

    @Override
    public void onResult(Countbean countbean) {
        mMemberHeaderView.setCountbean(countbean);
    }

    @Override
    public void onAddSuc(String msg) {
        showToast(msg);
        if (mAddialog != null && mAddialog.isShowing()) {
            mAddialog.dismiss();
        }
        onRefresh();
    }

    @Override
    public void onMemberList(List<MemberLevel> levels) {
        mLevels = levels;
        mMemberLevelsAdapter.setNewData(levels);
    }

    @Override
    public void onMemberResult(Member member) {
        // todo show recharge dialog
        if (mSearchDialog != null && mSearchDialog.isShowing()) {
            if (member != null) {
                mSearchDialog.dismiss();
                mRechargeView.setUserInfo(member);
                mRechargeView.show();
            } else {
                showToast("未查找到会员信息");
            }
        } else {
            List<Member> list = new ArrayList<>();
            list.add(member);
            fillChart(list);
        }
    }

    private void fillChart(List<Member> list) {
        if (request != null) {
            String json = WebData.newInstance().getMemberWebData(list, mWebCahrtView.getWidth(), mWebCahrtView.getHight());
            Response response = getResone(json);
            mWebCahrtView.callback(request, response);
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            mWebCahrtView.refresh("refresh");
            return true;
        }
        return false;
    }

    @Override
    public void onGettableInfo(final Request request) {
        this.request = request;
        if (mList != null && mList.size() != 0) {
            onGetMenbersCallback(mList);
        } else {
            mPresenter.getMemberList(Param.Keys.TOKEN, getToken(), Param.Keys.DATA, mMemberHeaderView.getKey(), Param.Keys.PAGE, page + "");
        }
    }

    @Override
    public void onOperate(Request request) {
        String operate = request.getParams().optString("method");
        switch (operate) {
            case "充值":
                Member mRechargeMember = (Member) JSONManager.getInstance().parseObject(request.getParams().opt("row_data") + "", Member.class);
                mRechargeView.setUserInfo(mRechargeMember);
                showReChargeDialog();
                break;
            case "查看":
                org.greenrobot.eventbus.EventBus.getDefault().post(new FragmentEvent(ChildViewClick.MEMBER_DETAIL, new String[]{request.getParams().optString("row_data")}));
                break;
        }
    }

    @Override
    public void onSearch(Request request) {
        this.request = request;
        if (!TextUtils.isEmpty(mMemberHeaderView.getKey())) {
            mPresenter.searchMembers(Param.Keys.TOKEN, getToken(), Param.Keys.DATA, mMemberHeaderView.getKey(), Param.Keys.PAGE, page + "");
        } else {
            mPresenter.getMemberList(Param.Keys.TOKEN, getToken(), Param.Keys.DATA, mMemberHeaderView.getKey(), Param.Keys.PAGE, page + "");
        }
    }

    @Override
    public void onResult(List<Cardbean> cardbean) {

    }

    @Override
    public void onAddresult(String msg) {

    }

    @Override
    public void onDeleteResult(String msg) {

    }

    @Override
    public void onCodeCardError(String msg) {

    }

    @Override
    public void onCardMsg(Cardbean cardbean) {
        mCardInputLayout.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mobileTv.clearFocus();
        entityCardTv.setText(cardbean.getCoding_card());
        mInputEdite.setText("");
        isExcute = false;
        hideInputMethod();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(EventRouter event) {
        switch (event.getAction()) {
            case SELLER_INFO:
                if (mRechargeView != null) {
                    //处理逻辑
                    mRechargeView.setSellerInfo((Sellerbean) event.getData());
                }
                break;
            case MEMBER_MSG:
                onRefresh();
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResultSuc() {
        onRefresh();
    }
}

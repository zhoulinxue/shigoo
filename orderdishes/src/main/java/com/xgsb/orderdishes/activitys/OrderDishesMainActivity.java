package com.xgsb.orderdishes.activitys;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xgsb.orderdishes.R;
import com.xgsb.orderdishes.R2;
import com.xgsb.orderdishes.adapters.OrderSheetMainMenuListAdapter;
import com.zx.datafactory.bean.OrderSheetMainMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Name: OrderDishesMainActivity
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-10 16:28
 */
public class OrderDishesMainActivity extends OrderDishBaseActivity {
    @BindView(R2.id.ordedishes_main_recyclerView)
    RecyclerView mRecyclerView;
    OrderSheetMainMenuListAdapter mAdapter;
    private List<OrderSheetMainMenu> mList = new ArrayList<>();
    private int[] LOGO = {
            R.mipmap.ordedishes_home_icon_piont,
            R.mipmap.ordedishes_home_icon_give,
            R.mipmap.ordedishes_home_icon_estimate,
            R.mipmap.ordedishes_home_icon_wine,
            R.mipmap.ordedishes_home_icon_vip,
            R.mipmap.ordedishes_home_icon_data};


    @Override
    protected int initLayout() {
        return R.layout.ordedishes_main_activity;
    }

    @Override
    protected void onCreateView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        String[] arrays = getResources().getStringArray(R.array.ordedishes_menber_tab_title);
        List<String> nameArrarys = Arrays.asList(arrays);
        for (int i = 0; i < nameArrarys.size(); i++) {
            mList.add(new OrderSheetMainMenu(LOGO[i], nameArrarys.get(i)));
        }
        mAdapter = new OrderSheetMainMenuListAdapter(R.layout.ordedishes_main_menu_item_layout, mList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (DEFAULT_TOKEN.equals(getToken())) {
                    //未登陆时 跳转登陆界面
                    startRouterActivity(com.zx.datafactory.enu.EventBusAction.LOGIN);
                    return;
                }
                showToast(mAdapter.getItem(position).getName());
                switch (position) {
                    case 0:
                        startRouterActivity(com.zx.datafactory.enu.EventBusAction.LOGIN_SUC);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }
        });
    }
}

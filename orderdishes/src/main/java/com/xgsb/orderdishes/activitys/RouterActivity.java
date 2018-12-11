package com.xgsb.orderdishes.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.aspsine.fragmentnavigator.FragmentNavigator;
import com.aspsine.fragmentnavigator.FragmentNavigatorAdapter;
import com.luojilab.router.facade.annotation.RouteNode;
import com.xgsb.orderdishes.R;
import com.xgsb.orderdishes.fragments.TableFragment;
import com.zx.datafactory.enu.EventBusAction;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: RouterActivity
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-11 10:37
 */
@RouteNode(path = "/orderdishesmain", desc = "点单首页")
public class RouterActivity extends OrderDishBaseActivity {
    private FragmentNavigator mFragmentNavigator;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.ordedishes_router_activity;
    }

    @Override
    protected void onCreateView() {
        fragments.add(TableFragment.newInstance());
        mFragmentNavigator = new FragmentNavigator(getSupportFragmentManager(), new FragmentNavigatorAdapter() {
            @Override
            public Fragment onCreateFragment(int i) {
                return fragments.get(i);
            }

            @Override
            public String getTag(int i) {
                return fragments.get(i).getClass().getSimpleName();
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        }, R.id.ordedishes_fragment_container);
        mFragmentNavigator.setDefaultPosition(0);
        mFragmentNavigator.showFragment(0);
        EventBus.getDefault().register(this);
        initIntent(getIntent());
    }

    private void initIntent(Intent intent) {
        onRouterEvent(com.zx.datafactory.enu.EventBusAction.getEnum(intent.getAction()));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initIntent(intent);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onRouterEvent(EventBusAction action) {
        switch (action) {
            case LOGIN:
                startActivity(new Intent(this, OrderDishesLoginActivity.class));
                break;
            case LOGIN_SUC:
                mFragmentNavigator.showFragment(0);
                break;
            case MAIN:
                startActivity(new Intent(this, OrderDishesMainActivity.class));
                break;
        }
    }
}

package com.xgsb.cashregister.activitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.aspsine.fragmentnavigator.FragmentNavigator;
import com.aspsine.fragmentnavigator.FragmentNavigatorAdapter;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.router.facade.annotation.RouteNode;
import com.xgsb.cashregister.R;
import com.xgsb.cashregister.R2;
import com.xgsb.cashregister.fragments.AddCardFragment;
import com.xgsb.cashregister.fragments.BooksOffFragment;
import com.xgsb.cashregister.fragments.MemberDetailFragment;
import com.xgsb.cashregister.fragments.MemberMainFragment;
import com.xgsb.cashregister.fragments.OrdersFragment;
import com.xgsb.cashregister.fragments.PrintFragment;
import com.zx.datafactory.JSONManager;
import com.zx.datafactory.bean.FragmentEvent;
import com.zx.datafactory.bean.Member;
import com.zx.api.componentservice.TablesService;
import com.zx.mvplibrary.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.ITabView.TabIcon;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Name: CashRigisterMainActivity
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-23 14:05
 */
@RouteNode(path = "/cashmain", desc = "收银首页")
public class CashRigisterMainActivity extends BaseActivity {
    @BindView(R2.id.left_tab_layout)
    VerticalTabLayout mLeftTabLayout;
    private List<TabLayout.Tab> mTabs = new ArrayList<>();
    private TabLayout.Tab mTables, mTabOrders, mTabMenber, mTabBookOff, mTabPrint;
    private final int[] mTabImgs = {R.mipmap.cashregister_books, R.mipmap.cashregister_orders, R.mipmap.cashregister_menbers, R.mipmap.cashregister_jiaoban, R.mipmap.cashregister_print};
    private final int[] mTabText = {R.string.cashregister_table, R.string.cashregister_orders, R.string.cashregister_menbers, R.string.cashregister_book_off, R.string.cashregister_print};

    private List<Fragment> fragments = new ArrayList<>();
    public static String TEST = "test";
    FragmentNavigator mFragmentNavigator;

    @Override
    protected int initLayout() {
        return R.layout.cashregister_cash_main_main_activity;
    }

    @Override
    protected void onCreateView() {
        ButterKnife.bind(this);
        initTableLayout();
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
        }, R.id.cashregister_fragment_container);
        mFragmentNavigator.setDefaultPosition(0);
        mLeftTabLayout.setTabSelected(mFragmentNavigator.getCurrentPosition());
        mLeftTabLayout.getTabAt(mFragmentNavigator.getCurrentPosition()).getTitleView().setBackgroundResource(R.drawable.cash_main_left_tab_press_bg);
        mFragmentNavigator.showFragment(mFragmentNavigator.getCurrentPosition());
        EventBus.getDefault().register(this);
//        try { //获得外接USB输入设备的信息
//            Process p = Runtime.getRuntime().exec("cat /proc/bus/input/devices");
//            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line = null;
//            while ((line = in.readLine()) != null) {
//
//            }
//            String deviceInfo = line.trim(); //对获取的每行的设备信息进行过滤，获得自己想要的。
//        } catch (Exception e) { // TODO: handle exception e.printStackTrace(); } *deviceinfo中包含每个设备的pid和vid*
//        }
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mFragmentNavigator.onCreate(savedInstanceState);
    }

    /**
     * 初始化代码
     */
    private void initTableLayout() {
        addTabs();
        mLeftTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                for (int i = 0; i < mTabImgs.length; i++) {
                    if (i == position) {
                        tab.getTitleView().setBackgroundResource(R.drawable.cash_main_left_tab_press_bg);
                        mFragmentNavigator.showFragment(position);
                    } else {
                        mLeftTabLayout.getTabAt(i).getTitleView().setBackground(null);
                    }
                }
            }

            @Override
            public void onTabReselected(TabView tab, int position) {
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mFragmentNavigator.onSaveInstanceState(outState);
    }

    /**
     * 添加 tab
     */
    private void addTabs() {
        for (int i = 0; i < mTabImgs.length; i++) {
            TabIcon.Builder iconBuilder = new TabIcon.Builder();
            iconBuilder.setIcon(mTabImgs[i], mTabImgs[i]);
            iconBuilder.setIconGravity(Gravity.TOP);
            QTabView tabView = new QTabView(this);
            tabView.setIcon(iconBuilder.build());
            ITabView.TabTitle.Builder titleBuilder = new ITabView.TabTitle.Builder();
            titleBuilder.setTextColor(getResources().getColor(R.color.white), getResources().getColor(R.color.white));
            titleBuilder.setTextSize((int) getResources().getDimension(R.dimen.cashregister_member_tabl_text_size));
            titleBuilder.setContent(getResources().getString(mTabText[i]));
            tabView.setTitle(titleBuilder.build());
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams
                    ((int) getResources().getDimension(R.dimen.cashregister_cash_main_tab_width),
                            (int) getResources().getDimension(R.dimen.cashregister_cash_main_tab_width));
            lp.gravity = Gravity.CENTER;
            tabView.getTitleView().setLayoutParams(lp);
            tabView.getTitleView().setPadding(0, 10, 0, 10);
            mLeftTabLayout.setTabMode(VerticalTabLayout.TAB_MODE_SCROLLABLE);
            mLeftTabLayout.addTab(tabView);
            addFragments(i);
        }
        MemberDetailFragment fragment = MemberDetailFragment.newInstance();
        fragments.add(fragment);
        AddCardFragment cardFragment = AddCardFragment.newInstance();
        fragments.add(cardFragment);
    }

    private void addFragments(int positon) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putString(TEST, getResources().getString(mTabText[positon]));
        switch (positon) {
            case 0:
                Router router = Router.getInstance();
                if (router.getService(TablesService.class.getSimpleName()) != null) {
                    TablesService service = (TablesService) router.getService(TablesService.class.getSimpleName());
                    fragment = service.getTablesFragment();
                    if (fragment != null) {
                        fragment.setArguments(bundle);
                    }
                }
                break;
            case 1:
                fragment = OrdersFragment.newInstance();
                break;
            case 2:
                fragment = MemberMainFragment.newInstance();
                break;
            case 3:
                fragment = BooksOffFragment.newInstance();
                break;
            case 4:
                fragment = PrintFragment.newInstance();
                break;

        }
        if (fragment != null) {
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public String getToken() {
        return null;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void OnClick(FragmentEvent fragmentEvent) {
        switch (fragmentEvent.getEvent()) {
            case MEMBER_DETAIL:
                Member member = (Member) JSONManager.getInstance().parseObject(fragmentEvent.getParams()[0], Member.class);
                ((MemberDetailFragment) fragments.get(5)).updateId(member.getId() + "");
                mFragmentNavigator.showFragment(5);
                break;
            case MEMBER_DETAIL_BACK:
                mFragmentNavigator.showFragment(2);
                break;
            case CARD_FRAGMENT:
                mFragmentNavigator.showFragment(6);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

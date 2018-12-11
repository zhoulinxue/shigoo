package com.xgsb.orderdishes.serviceImpl;

import android.support.v4.app.Fragment;

import com.xgsb.orderdishes.fragments.TableFragment;
import com.zx.api.componentservice.TablesService;

/**
 * Name: TablesFragmentServiceImpl
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-10 14:16
 */
public class TablesFragmentServiceImpl implements TablesService {
    @Override
    public Fragment getTablesFragment() {
        return TableFragment.newInstance();
    }
}

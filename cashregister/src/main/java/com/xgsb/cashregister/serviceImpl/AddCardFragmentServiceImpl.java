package com.xgsb.cashregister.serviceImpl;

import android.support.v4.app.Fragment;

import com.xgsb.cashregister.fragments.AddCardFragment;
import com.zx.api.componentservice.AddCardFragmentService;

/**
 * Name: AddCardFragmentServiceImpl
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-12 10:37
 */
public class AddCardFragmentServiceImpl implements AddCardFragmentService {
    @Override
    public Fragment getAddCardFragment() {
        return AddCardFragment.newInstance();
    }
}

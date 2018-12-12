package com.xgsb.cashregister.serviceImpl;

import android.support.v4.app.Fragment;

import com.xgsb.cashregister.fragments.MemberDetailFragment;
import com.zx.api.componentservice.MemberDetailFragmentService;

/**
 * Name: MemberDetailFragmentServiceImpl
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-12 10:32
 */
public class MemberDetailFragmentServiceImpl implements MemberDetailFragmentService {
    @Override
    public Fragment getMemberDitailFragment() {
        return MemberDetailFragment.newInstance();
    }
}

package com.xgsb.cashregister.mvp.contacts;

import com.zx.datafactory.bean.Countbean;
import com.zx.datafactory.bean.ListData;
import com.zx.datafactory.bean.Member;
import com.zx.datafactory.bean.ReChargeListData;
import com.zx.datafactory.bean.ReChargebean;
import com.zx.api.api.mvp.BaseView;

import java.util.List;

/**
 * Name: MemberManageContact
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-23 14:05
 */
public class MemberRechargeContact {
    public interface presenter {
        void getReChargeList(String... params);

        void cancelation(String... params);
    }

    public interface view extends BaseView {
        void onGetReChargeListCallback(ReChargeListData list);

        void onCancelLationResult(String msg);
    }
}

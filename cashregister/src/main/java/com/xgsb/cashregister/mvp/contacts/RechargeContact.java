package com.xgsb.cashregister.mvp.contacts;

import com.zx.datafactory.bean.Countbean;
import com.zx.datafactory.bean.Member;
import com.zx.datafactory.bean.MemberLevel;
import com.zx.datafactory.bean.MemberReChargeGive;
import com.zx.datafactory.bean.Sellerbean;
import com.zx.api.api.mvp.BaseView;

import java.util.List;

/**
 * Name: RechargeContact
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-12-04 16:34
 */
public class RechargeContact {

    public interface presenter {
        void getReChareList(String... params);

        void recharge(String... param);

        void getGives(String... param);

    }

    public interface view extends BaseView {

        void onReChareList(List<MemberReChargeGive> reChargeGives);

        void onRechargeResult(String msg);

        void onGiveResult(double give);
    }
}

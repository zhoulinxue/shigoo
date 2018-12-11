package com.xgsb.cashregister.mvp.contacts;

import com.zx.datafactory.bean.Member;
import com.zx.api.api.mvp.BaseView;

import java.util.List;

/**
 * Name: MemberDetailContact
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-27 13:15
 */
public class MemberDetailContact {
    public interface presenter {
        void getMemberDetail(String... params);

        void exChanageCard(String... params);

        void refund(String... params);

        void deleteMember(String... params);

        void memberFrozen(String... params);

    }

    public interface view extends BaseView {
        void onResult(Member member);

        void onExChanageResult(String msg);

        void onRefundResult(String msg);

        void onDeleteResult(String msg);

        void onFrozenResult(String msg);
    }
}
